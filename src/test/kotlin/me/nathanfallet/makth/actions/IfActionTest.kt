package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert
import org.junit.Test

class IfActionTest {

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
        Assert.assertEquals(
            "if (x = 2) {\n}",
            IfAction(Equality(Variable("x"), Integer.instantiate(2)), listOf()).toAlgorithmString()
        )
    }

    @Test
    fun toRawStringWithChild() {
        Assert.assertEquals(
            "if (x = 2) {\n    print(\"Test\")\n}",
            IfAction(
                Equality(Variable("x"), Integer.instantiate(2)),
                listOf(PrintAction(listOf(StringValue("Test"))))
            ).toAlgorithmString()
        )
    }

    @Test
    fun toRawStringWithChildren() {
        Assert.assertEquals(
            "if (x = 2) {\n    print(\"Test\")\n} else {\n    print(\"Test2\")\n}",
            IfAction(
                Equality(Variable("x"), Integer.instantiate(2)),
                listOf(PrintAction(listOf(StringValue("Test")))),
                listOf(PrintAction(listOf(StringValue("Test2"))))
            ).toAlgorithmString()
        )
    }

    @Test
    fun ifWithBoolean() {
        Assert.assertEquals(
            contextWithX,
            context.execute(
                IfAction(
                    BooleanValue(true),
                    listOf(SetAction("x", Integer.instantiate(2))),
                    listOf()
                )
            )
        )
    }

    @Test
    fun elseWithBoolean() {
        Assert.assertEquals(
            contextWithX,
            context.execute(
                IfAction(
                    BooleanValue(false),
                    listOf(),
                    listOf(SetAction("x", Integer.instantiate(2)))
                )
            )
        )
    }

    @Test
    fun ifWithVariable() {
        Assert.assertEquals(
            contextWithXAndY,
            contextWithX.execute(
                IfAction(
                    Equality(Variable("x"), Integer.instantiate(2)),
                    listOf(SetAction("y", Integer.instantiate(4))),
                    listOf()
                )
            )
        )
    }

    @Test
    fun ifWithVariableWithoutContext() {
        Assert.assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(
                IfAction(
                    Equality(Variable("x"), Integer.instantiate(2)),
                    listOf(),
                    listOf()
                )
            )
        }
    }

    @Test
    fun ifWhenNotABoolean() {
        Assert.assertThrows(Action.NotABooleanException::class.java) {
            contextWithX.execute(
                IfAction(
                    Variable("x"),
                    listOf(),
                    listOf()
                )
            )
        }
    }

}