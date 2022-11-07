package me.nathanfallet.makth.lexers

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.numbers.Rational
import me.nathanfallet.makth.operations.Equality
import me.nathanfallet.makth.operations.Product
import me.nathanfallet.makth.operations.Quotient
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.operations.Exponentiation
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class MathLexerTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to Integer.instantiate(2)
        )
    )

    @Test
    fun parseString() {
        assertEquals(StringValue("Hello world!"), MathLexer("\"Hello world!\"").execute(context))
    }

    @Test
    fun parseVariable() {
        assertEquals(Variable("x"), MathLexer("x").execute(context))
    }

    @Test
    fun parseInteger() {
        assertEquals(Integer.instantiate(2), MathLexer("2").execute(context))
    }

    @Test
    fun parseInteger2() {
        assertEquals(Integer.instantiate(-2), MathLexer("-2").execute(context))
    }

    @Test
    fun parseInteger3() {
        assertEquals(Integer.instantiate(-2), MathLexer("(-2)").execute(context))
    }

    @Test
    fun parseSum() {
        assertEquals(Integer.instantiate(3), MathLexer("1 + 2").execute(context))
    }

    @Test
    fun parseSum2() {
        assertEquals(Integer.instantiate(-1), MathLexer("1 + (-2)").execute(context))
    }

    @Test
    fun parseSumWithVariable() {
        assertEquals(
            Sum(Integer.instantiate(1), Variable("x")),
            MathLexer("1 + x").execute(context)
        )
    }

    @Test
    fun parseSumWithVariableAndContext() {
        assertEquals(
            Integer.instantiate(3),
            MathLexer("1 + x").execute(contextWithX)
        )
    }

    @Test
    fun parseDifference() {
        assertEquals(Integer.instantiate(-1), MathLexer("1 - 2").execute(context))
    }

    @Test
    fun parseDifferenceWithVariable() {
        assertEquals(
            Sum(Integer.instantiate(1), Product(Integer.instantiate(-1), Variable("x"))),
            MathLexer("1 - x").execute(context)
        )
    }

    @Test
    fun parseDifferenceWithVariableAndContext() {
        assertEquals(
            Integer.instantiate(-1),
            MathLexer("1 - x").execute(contextWithX)
        )
    }

    @Test
    fun parseProduct() {
        assertEquals(Integer.instantiate(6), MathLexer("2 * 3").execute(context))
    }

    @Test
    fun parseProduct2() {
        assertEquals(Integer.instantiate(-6), MathLexer("2 * (-3)").execute(context))
    }

    @Test
    fun parseProductWithVariable() {
        assertEquals(
            Product(Integer.instantiate(2), Variable("x")),
            MathLexer("2 * x").execute(context)
        )
    }

    @Test
    fun parseProductWithVariableAndContext() {
        assertEquals(
            Integer.instantiate(4),
            MathLexer("2 * x").execute(contextWithX)
        )
    }

    @Test
    fun parseQuotient() {
        assertEquals(Rational.instantiate(2, 3), MathLexer("2 / 3").execute(context))
    }

    @Test
    fun parseQuotient2() {
        assertEquals(Rational.instantiate(-2, 3), MathLexer("2 / (-3)").execute(context))
    }

    @Test
    fun parseQuotientWithVariable() {
        assertEquals(
            Quotient(Integer.instantiate(2), Variable("x")),
            MathLexer("2 / x").execute(context)
        )
    }
    
    @Test
    fun parseExponentiate() {
        assertEquals(Integer.instantiate(8), MathLexer("2 ^ 3").execute(context))
    }

    @Test
    fun parseExponentiate2() {
        assertEquals(Rational.instantiate(1, 8), MathLexer("2 ^ (-3)").execute(context))
    }

    @Test
    fun parseExponentiateWithVariable() {
        assertEquals(
            Exponentiation(Integer.instantiate(2), Variable("x")),
            MathLexer("2 ^ x").execute(context)
        )
    }

    @Test
    fun parseExponentiateWithVariableAndContext() {
        assertEquals(
            Integer.instantiate(4),
            MathLexer("2 ^ x").execute(contextWithX)
        )
    }

    @Test
    fun parseQuotientWithVariableAndContext() {
        assertEquals(
            Integer.instantiate(1),
            MathLexer("2 / x").execute(contextWithX)
        )
    }

    @Test
    fun parseEquality() {
        assertEquals(
            Equality(Variable("x"), Integer.instantiate(2)),
            MathLexer("x = 2").execute(context)
        )
    }

    @Test
    fun parseEqualityWithVariable() {
        assertEquals(
            BooleanValue(true),
            MathLexer("x = 2").execute(contextWithX)
        )
    }

    @Test
    fun parseEqualityNotEquals() {
        assertEquals(
            Equality(Variable("x"), Integer.instantiate(2), Equality.Operator.NotEquals),
            MathLexer("x != 2").execute(context)
        )
    }

    @Test
    fun parseEqualityLessThan() {
        assertEquals(
            Equality(Variable("x"), Integer.instantiate(2), Equality.Operator.LessThan),
            MathLexer("x < 2").execute(context)
        )
    }

    @Test
    fun parseEqualityGreaterThan() {
        assertEquals(
            Equality(Variable("x"), Integer.instantiate(2), Equality.Operator.GreaterThan),
            MathLexer("x > 2").execute(context)
        )
    }

    @Test
    fun parseEqualityLessThanOrEquals() {
        assertEquals(
            Equality(Variable("x"), Integer.instantiate(2), Equality.Operator.LessThanOrEquals),
            MathLexer("x <= 2").execute(context)
        )
    }

    @Test
    fun parseEqualityGreaterThanOrEquals() {
        assertEquals(
            Equality(Variable("x"), Integer.instantiate(2), Equality.Operator.GreaterThanOrEquals),
            MathLexer("x >= 2").execute(context)
        )
    }

}