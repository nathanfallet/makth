package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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
    fun algorithmString() {
        assertEquals(
            "if (x = 2) {\n}",
            IfAction(Equality(Variable.instantiate("x"), Integer.instantiate(2)), listOf()).algorithmString
        )
    }

    @Test
    fun toRawStringWithChild() {
        assertEquals(
            "if (x = 2) {\n    print(\"Test\")\n}",
            IfAction(
                Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                listOf(PrintAction(listOf(StringValue("Test"))))
            ).algorithmString
        )
    }

    @Test
    fun toRawStringWithChildren() {
        assertEquals(
            "if (x = 2) {\n    print(\"Test\")\n} else {\n    print(\"Test2\")\n}",
            IfAction(
                Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                listOf(PrintAction(listOf(StringValue("Test")))),
                listOf(PrintAction(listOf(StringValue("Test2"))))
            ).algorithmString
        )
    }

    @Test
    fun handler() {
        assertEquals(
            IfAction(
                Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                listOf(),
                listOf()
            ),
            IfAction.handler(listOf(Equality(Variable.instantiate("x"), Integer.instantiate(2))))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertFailsWith(IncorrectArgumentCountException::class) {
            IfAction.handler(listOf())
        }
    }

    @Test
    fun ifWithBoolean() {
        assertEquals(
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
        assertEquals(
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
        assertEquals(
            contextWithXAndY,
            contextWithX.execute(
                IfAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                    listOf(SetAction("y", Integer.instantiate(4))),
                    listOf()
                )
            )
        )
    }

    @Test
    fun ifWithVariableWithoutContext() {
        assertFailsWith(Action.UnknownVariablesException::class) {
            context.execute(
                IfAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                    listOf(),
                    listOf()
                )
            )
        }
    }

    @Test
    fun ifWhenNotABoolean() {
        assertFailsWith(Action.NotABooleanException::class) {
            contextWithX.execute(
                IfAction(
                    Variable.instantiate("x"),
                    listOf(),
                    listOf()
                )
            )
        }
    }

}