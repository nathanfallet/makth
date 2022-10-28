package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert
import org.junit.Test

class PrintActionTest {

    private val context = Context()

    private val contextWithString = Context(
        hashMapOf(),
        listOf(
            "Hello world!"
        )
    )

    private val contextWithX = Context(
        hashMapOf(
            Pair("x", Integer.instantiate(2))
        )
    )

    private val contextWithXAndString = Context(
        hashMapOf(
            Pair("x", Integer.instantiate(2))
        ),
        listOf(
            "x = 2"
        )
    )

    @Test
    fun toRawString() {
        Assert.assertEquals(
            "print(\"x = \", x)",
            PrintAction(listOf(StringValue("x = "), Variable("x"))).toAlgorithmString()
        )
    }

    @Test
    fun printString() {
        Assert.assertEquals(
            contextWithString,
            context.execute(PrintAction(listOf(StringValue("Hello world!"))))
        )
    }

    @Test
    fun printStringWithVariable() {
        Assert.assertEquals(
            contextWithXAndString,
            contextWithX.execute(PrintAction(listOf(StringValue("x = "), Variable("x"))))
        )
    }

    @Test
    fun printStringWithVariableWithoutContext() {
        Assert.assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(PrintAction(listOf(StringValue("x = "), Variable("x"))))
        }
    }

}