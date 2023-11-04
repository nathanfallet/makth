package me.nathanfallet.makth.actions

import me.nathanfallet.makth.exceptions.NotABooleanException
import me.nathanfallet.makth.exceptions.UnknownVariablesException
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.numbers.integers.IntegerFactory
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.variables.VariableFactory
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WhileActionTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        )
    )

    private val contextWithXIncremented = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(10)
        )
    )

    @Test
    fun rawString() {
        assertEquals(
            "while (x < 10) {\n}",
            WhileAction(
                Equality(
                    VariableFactory.instantiate("x"),
                    IntegerFactory.instantiate(10),
                    Equality.Operator.LessThan
                ), listOf()
            ).algorithmString
        )
    }

    @Test
    fun toRawStringWithChild() {
        assertEquals(
            "while (x < 10) {\n    set(x, x + 1)\n}",
            WhileAction(
                Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(10), Equality.Operator.LessThan),
                listOf(SetAction("x", Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(1))))
            ).algorithmString
        )
    }

    @Test
    fun handler() {
        assertEquals(
            WhileAction(
                Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2)),
                listOf()
            ),
            WhileAction.handler(listOf(Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2))))
        )
    }

    @Test
    fun handlerWithWrongArgsCount() {
        assertFailsWith(IncorrectArgumentCountException::class) {
            WhileAction.handler(listOf())
        }
    }

    @Test
    fun whileWithVariable() {
        assertEquals(
            contextWithXIncremented,
            contextWithX.execute(
                WhileAction(
                    Equality(
                        VariableFactory.instantiate("x"),
                        IntegerFactory.instantiate(10),
                        Equality.Operator.LessThan
                    ),
                    listOf(SetAction("x", Sum(VariableFactory.instantiate("x"), IntegerFactory.instantiate(1))))
                )
            )
        )
    }

    @Test
    fun whileWithVariableWithoutContext() {
        assertFailsWith(UnknownVariablesException::class) {
            context.execute(
                WhileAction(
                    Equality(VariableFactory.instantiate("x"), IntegerFactory.instantiate(2)),
                    listOf()
                )
            )
        }
    }

    @Test
    fun whileWhenNotABoolean() {
        assertFailsWith(NotABooleanException::class) {
            contextWithX.execute(
                WhileAction(
                    VariableFactory.instantiate("x"),
                    listOf()
                )
            )
        }
    }

}
