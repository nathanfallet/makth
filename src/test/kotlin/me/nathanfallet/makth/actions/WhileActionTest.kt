package me.nathanfallet.makth.actions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert
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
    fun toRawString() {
        Assert.assertEquals(
            "while (x < 10) {\n}",
            WhileAction(
                Equality(
                    Variable("x"),
                    Integer.instantiate(10),
                    Equality.Operator.LessThan
                ), listOf()
            ).toAlgorithmString()
        )
    }

    @Test
    fun toRawStringWithChild() {
        Assert.assertEquals(
            "while (x < 10) {\n    set(x, x + 1)\n}",
            WhileAction(
                Equality(Variable("x"), Integer.instantiate(10), Equality.Operator.LessThan),
                listOf(SetAction("x", Sum(Variable("x"), Integer.instantiate(1))))
            ).toAlgorithmString()
        )
    }

    @Test
    fun whileWithVariable() {
        Assert.assertEquals(
            contextWithXIncremented,
            contextWithX.execute(
                WhileAction(
                    Equality(Variable("x"), Integer.instantiate(10), Equality.Operator.LessThan),
                    listOf(SetAction("x", Sum(Variable("x"), Integer.instantiate(1))))
                )
            )
        )
    }

    @Test
    fun whileWithVariableWithoutContext() {
        Assert.assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(
                WhileAction(
                    Equality(Variable("x"), Integer.instantiate(2)),
                    listOf()
                )
            )
        }
    }

    @Test
    fun whileWhenNotABoolean() {
        Assert.assertThrows(Action.NotABooleanException::class.java) {
            contextWithX.execute(
                WhileAction(
                    Variable("x"),
                    listOf()
                )
            )
        }
    }

}
