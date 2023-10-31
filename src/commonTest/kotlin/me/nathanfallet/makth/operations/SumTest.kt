package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.test.Test
import kotlin.test.assertEquals

class SumTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 + 2",
            Sum(Integer.instantiate(1), Integer.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithMinus() {
        assertEquals(
            "1 - 2",
            Sum(Integer.instantiate(1), Integer.instantiate(-2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        // This will technically never happen, but it's the only way to test it
        assertEquals(
            "(1 = 1) + (2 = 2)",
            Sum(Equality(Integer.instantiate(1), Integer.instantiate(1)), Equality(Integer.instantiate(2), Integer.instantiate(2))).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "1 + 2",
            Sum(Integer.instantiate(1), Integer.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithMinus() {
        assertEquals(
            "1 - 2",
            Sum(Integer.instantiate(1), Integer.instantiate(-2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        // This will technically never happen, but it's the only way to test it
        assertEquals(
            "(1 \\eq 1) + (2 \\eq 2)",
            Sum(Equality(Integer.instantiate(1), Integer.instantiate(1)), Equality(Integer.instantiate(2), Integer.instantiate(2))).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Sum(Variable.instantiate("x"), Variable.instantiate("y")).variables
        )
    }

    @Test
    fun sumNaturals() {
        // Check that a sum is computed correctly
        assertEquals(
            Integer.instantiate(3),
            Sum(Integer.instantiate(1), Integer.instantiate(2)).compute(context)
        )
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(
            true,
            Sum(Variable.instantiate("x"), Integer.instantiate(2)).equals(
                Sum(Variable.instantiate("x"), Integer.instantiate(2))
            )
        )
    }

    @Test
    fun testEqualsTrue2() {
        assertEquals(
            true,
            Sum(Integer.instantiate(2), Variable.instantiate("x")).equals(
                Sum(Variable.instantiate("x"), Integer.instantiate(2))
            )
        )
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(
            false,
            Sum(Variable.instantiate("x"), Integer.instantiate(1)).equals(
                Sum(Variable.instantiate("x"), Integer.instantiate(2))
            )
        )
    }

    @Test
    fun testLessThanTrue() {
        assertEquals(
            true,
            Sum(Variable.instantiate("x"), Integer.instantiate(1)).lessThan(
                Sum(Variable.instantiate("x"), Integer.instantiate(2))
            )
        )
    }

    @Test
    fun testLessThanTrue2() {
        assertEquals(
            true,
            Sum(Variable.instantiate("x"), Integer.instantiate(1)).lessThan(
                Sum(Integer.instantiate(2), Variable.instantiate("x"))
            )
        )
    }

}