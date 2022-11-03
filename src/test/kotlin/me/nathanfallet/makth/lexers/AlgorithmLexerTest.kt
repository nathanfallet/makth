package me.nathanfallet.makth.lexers

import me.nathanfallet.makth.actions.IfAction
import me.nathanfallet.makth.actions.PrintAction
import me.nathanfallet.makth.actions.SetAction
import me.nathanfallet.makth.actions.WhileAction
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert
import org.junit.Test

class AlgorithmLexerTest {

    @Test
    fun parseIfAction() {
        Assert.assertEquals(
            listOf(
                IfAction(Equality(Variable("x"), Integer.instantiate(2)), listOf())
            ),
            AlgorithmLexer("if (x = 2)").execute()
        )
    }

    @Test
    fun parseIfActionWithChild() {
        Assert.assertEquals(
            listOf(
                IfAction(
                    Equality(Variable("x"), Integer.instantiate(2)),
                    listOf(
                        PrintAction(listOf(StringValue("Test")))
                    )
                )
            ),
            AlgorithmLexer("if (x = 2) { print(\"Test\") }").execute()
        )
    }

    @Test
    fun parseIfActionWithElse() {
        Assert.assertEquals(
            listOf(
                IfAction(
                    Equality(Variable("x"), Integer.instantiate(2)),
                    listOf(
                        PrintAction(listOf(StringValue("Test")))
                    ),
                    listOf(
                        PrintAction(listOf(StringValue("Test2")))
                    )
                )
            ),
            AlgorithmLexer("if (x = 2) { print(\"Test\") } else { print(\"Test2\") }").execute()
        )
    }

    @Test
    fun parsePrintAction() {
        Assert.assertEquals(
            listOf(
                PrintAction(listOf(Integer.instantiate(2)))
            ),
            AlgorithmLexer("print(2)").execute()
        )
    }

    @Test
    fun parsePrintActionWithTwoArguments() {
        Assert.assertEquals(
            listOf(
                PrintAction(listOf(StringValue("x = "), Variable("x")))
            ),
            AlgorithmLexer("print(\"x = \", x)").execute()
        )
    }

    @Test
    fun parseSetAction() {
        Assert.assertEquals(
            listOf(
                SetAction("x", Integer.instantiate(2))
            ),
            AlgorithmLexer("set(x, 2)").execute()
        )
    }

    @Test
    fun parseWhileAction() {
        Assert.assertEquals(
            listOf(
                WhileAction(
                    Equality(
                        Variable("x"),
                        Integer.instantiate(10),
                        Equality.Operator.LessThan
                    ), listOf()
                )
            ),
            AlgorithmLexer("while (x < 10)").execute()
        )
    }

    @Test
    fun parseWhileActionWithChild() {
        Assert.assertEquals(
            listOf(
                WhileAction(
                    Equality(Variable("x"), Integer.instantiate(10), Equality.Operator.LessThan),
                    listOf(SetAction("x", Sum(Variable("x"), Integer.instantiate(1))))
                )
            ),
            AlgorithmLexer("while (x < 10) { set(x, x + 1) }").execute()
        )
    }

    @Test
    fun parseAndExecuteExampleAlgorithm() {
        val raw = """
        set(x, 2)
        while (x < 10) {
            set(x, x + 1)
        }
        print("x = ", x)
        """
        val actions = AlgorithmLexer(raw).execute()
        Assert.assertEquals(
            Context(
                hashMapOf(
                    Pair("x", Integer.instantiate(10))
                ),
                listOf(
                    StringValue("x = "), Integer.instantiate(10), StringValue("\n")
                )
            ),
            Context().execute(actions)
        )
    }

}