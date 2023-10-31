package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.sets.Vector
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ForActionTest {

    private val context = Context()

    private val contextWithOutput = Context(
        mapOf(
            "x" to Integer.instantiate(3)
        ),
        listOf(
            Integer.instantiate(1), StringValue("\n"),
            Integer.instantiate(2), StringValue("\n"),
            Integer.instantiate(3), StringValue("\n")
        )
    )

    @Test
    fun algorithmString() {
        assertEquals(
            "for (x, (1; 2; 3)) {\n}",
            ForAction(
                "x",
                Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))),
                listOf()
            ).algorithmString
        )
    }

    @Test
    fun toRawStringWithChild() {
        assertEquals(
            "for (x, (1; 2; 3)) {\n    print(x)\n}",
            ForAction(
                "x",
                Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))),
                listOf(PrintAction(listOf(Variable.instantiate("x"))))
            ).algorithmString
        )
    }

    @Test
    fun handler() {
        assertEquals(
            ForAction(
                "x",
                Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))),
                listOf()
            ),
            ForAction.handler(listOf(
                Variable.instantiate("x"),
                Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3)))
            ))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertFailsWith(IncorrectArgumentCountException::class) {
            ForAction.handler(listOf())
        }
    }

    @Test
    fun forWithPrint() {
        assertEquals(
            contextWithOutput,
            context.execute(
                ForAction(
                    "x",
                    Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))),
                    listOf(PrintAction(listOf(Variable.instantiate("x"))))
                )
            )
        )
    }

    @Test
    fun forWithPrintWithoutContext() {
        assertFailsWith(Action.UnknownVariablesException::class) {
            context.execute(
                ForAction("x", Variable.instantiate("y"), listOf())
            )
        }
    }

    @Test
    fun forWhenNotIterable() {
        assertFailsWith(Action.NotIterableException::class) {
            context.execute(
                ForAction("x", BooleanValue(true), listOf())
            )
        }
    }

}
