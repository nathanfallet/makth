package me.nathanfallet.makth.lexers

import me.nathanfallet.makth.actions.IfAction
import me.nathanfallet.makth.actions.PrintAction
import me.nathanfallet.makth.actions.SetAction
import me.nathanfallet.makth.actions.WhileAction
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AlgorithmLexerTest {

    @Test
    fun parseSingleLineComment() {
        assertEquals(
            listOf(
                PrintAction(listOf(Integer.instantiate(2))),
                PrintAction(listOf(Integer.instantiate(3)))
            ),
            AlgorithmLexer("print(2) // Prints 2\nprint(3) // Prints 3").execute()
        )
    }

    @Test
    fun parseMultiLineComment() {
        assertEquals(
            listOf(
                PrintAction(listOf(Integer.instantiate(2))),
                PrintAction(listOf(Integer.instantiate(3)))
            ),
            AlgorithmLexer("print(2) /* Prints 2 */ print(3) /* Prints 3 */").execute()
        )
    }

    @Test
    fun parseIfAction() {
        assertEquals(
            listOf(
                IfAction(Equality(Variable.instantiate("x"), Integer.instantiate(2)), listOf())
            ),
            AlgorithmLexer("if (x = 2)").execute()
        )
    }

    @Test
    fun parseIfActionWithChild() {
        assertEquals(
            listOf(
                IfAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                    listOf(
                        PrintAction(listOf(StringValue("Test")))
                    )
                )
            ),
            AlgorithmLexer("if (x = 2) { print(\"Test\") }").execute()
        )
    }

    @Test
    fun parseIfActionWithIfChild() {
        assertEquals(
            listOf(
                IfAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(2)),
                    listOf(
                        IfAction(
                            Equality(Variable.instantiate("y"), Integer.instantiate(3)),
                            listOf(
                                PrintAction(listOf(StringValue("Test")))
                            )
                        )
                    )
                )
            ),
            AlgorithmLexer("if (x = 2) { if (y = 3) { print(\"Test\") }}").execute()
        )
    }

    @Test
    fun parseIfActionWithElse() {
        assertEquals(
            listOf(
                IfAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(2)),
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
        assertEquals(
            listOf(
                PrintAction(listOf(Integer.instantiate(2)))
            ),
            AlgorithmLexer("print(2)").execute()
        )
    }

    @Test
    fun parsePrintActionWithTwoArguments() {
        assertEquals(
            listOf(
                PrintAction(listOf(StringValue("x = "), Variable.instantiate("x")))
            ),
            AlgorithmLexer("print(\"x = \", x)").execute()
        )
    }

    @Test
    fun parseSetAction() {
        assertEquals(
            listOf(
                SetAction("x", Integer.instantiate(2))
            ),
            AlgorithmLexer("set(x, 2)").execute()
        )
    }

    @Test
    fun parseWhileAction() {
        assertEquals(
            listOf(
                WhileAction(
                    Equality(
                        Variable.instantiate("x"),
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
        assertEquals(
            listOf(
                WhileAction(
                    Equality(Variable.instantiate("x"), Integer.instantiate(10), Equality.Operator.LessThan),
                    listOf(SetAction("x", Sum(Variable.instantiate("x"), Integer.instantiate(1))))
                )
            ),
            AlgorithmLexer("while (x < 10) { set(x, x + 1) }").execute()
        )
    }

    @Test
    fun parseAndExecuteExampleAlgorithm() {
        val raw = "set(x, 2)\nwhile (x < 10) {\n    set(x, x + 1)\n}\nprint(\"x = \", x)"
        val actions = AlgorithmLexer(raw).execute()
        assertEquals(
            Context(
                mapOf(
                    "x" to Integer.instantiate(10)
                ),
                listOf(
                    StringValue("x = "), Integer.instantiate(10), StringValue("\n")
                )
            ),
            Context().execute(actions)
        )
        assertEquals(raw, actions.joinToString("\n") { it.algorithmString })
    }

    @Test
    fun registerAndParseCustomAction() {
        assertEquals(
            listOf(
                CustomAction(Integer.instantiate(2))
            ),
            AlgorithmLexer("custom(2)").registerKeyword("custom", CustomAction::handler).execute()
        )
    }

    @Test
    fun unknownKeyword() {
        assertFailsWith(AlgorithmLexer.UnknownKeywordException::class) {
            AlgorithmLexer("unknown()").execute()
        }
    }

    @Test
    fun unexpectedKeyword() {
        assertFailsWith(AlgorithmLexer.UnexpectedKeywordException::class) {
            AlgorithmLexer("print unexpected()").execute()
        }
    }

    @Test
    fun unexpectedKeywordInsteadOfElse() {
        assertFailsWith(AlgorithmLexer.UnexpectedKeywordException::class) {
            AlgorithmLexer("if (x = 1) {} unexpected {}").execute()
        }
    }

    @Test
    fun unexpectedBrace() {
        assertFailsWith(AlgorithmLexer.UnexpectedBraceException::class) {
            AlgorithmLexer("()").execute()
        }
    }

    @Test
    fun unexpectedBraceBlock() {
        assertFailsWith(AlgorithmLexer.UnexpectedBraceException::class) {
            AlgorithmLexer("print() {}").execute()
        }
    }

    @Test
    fun unexpectedSlash() {
        assertFailsWith(AlgorithmLexer.UnexpectedSlashException::class) {
            AlgorithmLexer("print() /").execute()
        }
    }

    data class CustomAction(val value: Value): Action {

        companion object {

            fun handler(args: List<Value>): Action {
                if (args.count() != 1) {
                    throw IncorrectArgumentCountException("custom", args.count(), 1)
                }
                return CustomAction(args[0])
            }
        }

        override fun execute(context: Context): Context {
            return context
        }

        override val algorithmString: String get() {
            return "custom(" + value.algorithmString + ")"
        }

    }

}