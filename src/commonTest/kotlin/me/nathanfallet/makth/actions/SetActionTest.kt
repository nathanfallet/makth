package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentTypeException
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SetActionTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to Integer.instantiate(2)
        )
    )

    private val contextWithXAndY = Context(
        mapOf(
            "x" to Integer.instantiate(2),
            "y" to Integer.instantiate(4)
        )
    )

    @Test
    fun algorithmString() {
        assertEquals(
            "set(x, 2)",
            SetAction("x", Integer.instantiate(2)).algorithmString
        )
    }

    @Test
    fun handlerWithVariable() {
        assertEquals(
            SetAction("x", Integer.instantiate(2)),
            SetAction.handler(listOf(Variable.instantiate("x"), Integer.instantiate(2)))
        )
    }

    @Test
    fun handlerWithString() {
        assertEquals(
            SetAction("x", Integer.instantiate(2)),
            SetAction.handler(listOf(StringValue("x"), Integer.instantiate(2)))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertFailsWith(IncorrectArgumentCountException::class) {
            SetAction.handler(listOf())
        }
    }

    @Test
    fun handlerWithWrongArgType() {
        assertFailsWith(IncorrectArgumentTypeException::class) {
            SetAction.handler(listOf(Integer.instantiate(1), Integer.instantiate(2)))
        }
    }

    @Test
    fun setInteger() {
        assertEquals(
            contextWithX,
            context.execute(SetAction("x", Integer.instantiate(2)))
        )
    }

    @Test
    fun setIntegerWithVariable() {
        assertEquals(
            contextWithXAndY,
            contextWithX.execute(SetAction("y", Sum(Variable.instantiate("x"), Integer.instantiate(2))))
        )
    }

    @Test
    fun setIntegerWithVariableWithoutContext() {
        assertFailsWith(Action.UnknownVariablesException::class) {
            context.execute(SetAction("y", Sum(Variable.instantiate("x"), Integer.instantiate(2))))
        }
    }

}