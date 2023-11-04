package me.nathanfallet.makth.actions

import me.nathanfallet.makth.exceptions.UnknownVariablesException
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.numbers.integers.IntegerFactory
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.variables.VariableFactory
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
            "x" to IntegerFactory.instantiate(2)
        )
    )

    private val contextWithXAndString = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        ),
        listOf(
            StringValue("x = "), IntegerFactory.instantiate(2), StringValue("\n")
        )
    )

    @Test
    fun algorithmString() {
        assertEquals(
            "print(\"x = \", x)",
            PrintAction(listOf(StringValue("x = "), VariableFactory.instantiate("x"))).algorithmString
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
            contextWithX.execute(PrintAction(listOf(StringValue("x = "), VariableFactory.instantiate("x"))))
        )
    }

    @Test
    fun printStringWithVariableWithoutContext() {
        assertFailsWith(UnknownVariablesException::class) {
            context.execute(PrintAction(listOf(StringValue("x = "), VariableFactory.instantiate("x"))))
        }
    }

}