package me.nathanfallet.makth.actions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.sets.Vector
import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.StringValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

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
        assertThrows(IncorrectArgumentCountException::class.java) {
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
        assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(
                ForAction("x", Variable.instantiate("y"), listOf())
            )
        }
    }

    @Test
    fun forWhenNotIterable() {
        assertThrows(Action.NotIterableException::class.java) {
            context.execute(
                ForAction("x", BooleanValue(true), listOf())
            )
        }
    }

}
