package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class QuotientTest {

    private val context = Context()

    @Test
    fun toRawString() {
        assertEquals(
            "1 / 2",
            Quotient(Integer.instantiate(1), Integer.instantiate(2)).toRawString()
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) / (3 + 4)",
            Quotient(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "\\frac{1}{2}",
            Quotient(Integer.instantiate(1), Integer.instantiate(2)).toLaTeXString()
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "\\frac{1 + 2}{3 + 4}",
            Quotient(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).toLaTeXString()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable("x"), Variable("y")),
            Quotient(Variable("x"), Variable("y")).extractVariables()
        )
    }

    @Test
    fun divideNaturals() {
        // Check that a quotient is computed correctly
        assertEquals(
            Integer.instantiate(2),
            Quotient(Integer.instantiate(6), Integer.instantiate(3)).compute(context)
        )
    }

}