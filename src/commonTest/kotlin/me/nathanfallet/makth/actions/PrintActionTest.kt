package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class PrintActionTest {

    private val context = Context()

    private val contextWithString = Context(
        mapOf(),
        listOf(
            StringValue("Hello world!"), StringValue("\n")
        )
    )

    private val contextWithX = Context(
        mapOf(
            "x" to Integer.instantiate(2)
        )
    )

    private val contextWithXAndString = Context(
        mapOf(
            "x" to Integer.instantiate(2)
        ),
        listOf(
            StringValue("x = "), Integer.instantiate(2), StringValue("\n")
        )
    )

    @Test
    fun algorithmString() {
        assertEquals(
            "print(\"x = \", x)",
            PrintAction(listOf(StringValue("x = "), Variable.instantiate("x"))).algorithmString
        )
    }

    @Test
    fun printString() {
        assertEquals(
            contextWithString,
            context.execute(PrintAction(listOf(StringValue("Hello world!"))))
        )
    }

    @Test
    fun printStringWithVariable() {
        assertEquals(
            contextWithXAndString,
            contextWithX.execute(PrintAction(listOf(StringValue("x = "), Variable.instantiate("x"))))
        )
    }

    @Test
    fun printStringWithVariableWithoutContext() {
        assertFailsWith(Action.UnknownVariablesException::class) {
            context.execute(PrintAction(listOf(StringValue("x = "), Variable.instantiate("x"))))
        }
    }

}