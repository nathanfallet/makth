package me.nathanfallet.makth.actions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentTypeException
import me.nathanfallet.makth.extensions.StringValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

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
    fun toRawString() {
        assertEquals(
            "set(x, 2)",
            SetAction("x", Integer.instantiate(2)).toAlgorithmString()
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
        assertThrows(IncorrectArgumentCountException::class.java) {
            SetAction.handler(listOf())
        }
    }

    @Test
    fun handlerWithWrongArgType() {
        assertThrows(IncorrectArgumentTypeException::class.java) {
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
        assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(SetAction("y", Sum(Variable.instantiate("x"), Integer.instantiate(2))))
        }
    }

}