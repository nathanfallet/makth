package me.nathanfallet.makth.actions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class WhileActionTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to Integer.instantiate(2)
        )
    )

    private val contextWithXIncremented = Context(
        mapOf(
            "x" to Integer.instantiate(10)
        )
    )

    @Test
    fun rawString() {
        assertEquals(
            "while (x < 10) {\n}",
            WhileAction(
                Equality(
                    Variable.instantiate("x"),
                    Integer.instantiate(10),
                    Equality.Operator.LessThan
                ), listOf()
            ).algorithmString
        )
    }

    @Test
    fun toRawStringWithChild() {
        assertEquals(
            "while (x < 10) {\n    set(x, x + 1)\n}",
            WhileAction(
                Equality(Variable.instantiate("x"), Integer.instantiate(10), Equality.Operator.LessThan),
                listOf(SetAction("x", Sum(Variable.instantiate("x"), Integer.instantiate(1))))
            ).algorithmString
        )
    }

    @Test
    fun handler() {
        assertEquals(
            WhileAction(
                Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                listOf()
            ),
            WhileAction.handler(listOf(Equality(Variable.instantiate("x"), Integer.instantiate(2))))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertThrows(IncorrectArgumentCountException::class.java) {
            WhileAction.handler(listOf())
        }
    }

    @Test
    fun whileWithVariable() {
        assertEquals(
            contextWithXIncremented,
            contextWithX.execute(
                WhileAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(10), Equality.Operator.LessThan),
                    listOf(SetAction("x", Sum(Variable.instantiate("x"), Integer.instantiate(1))))
                )
            )
        )
    }

    @Test
    fun whileWithVariableWithoutContext() {
        assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(
                WhileAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                    listOf()
                )
            )
        }
    }

    @Test
    fun whileWhenNotABoolean() {
        assertThrows(Action.NotABooleanException::class.java) {
            contextWithX.execute(
                WhileAction(
                    Variable.instantiate("x"),
                    listOf()
                )
            )
        }
    }

}
