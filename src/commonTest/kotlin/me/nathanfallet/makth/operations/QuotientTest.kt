package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.test.Test
import kotlin.test.assertEquals

class QuotientTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 / 2",
            Quotient(Integer.instantiate(1), Integer.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) / (3 + 4)",
            Quotient(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "\\frac{1}{2}",
            Quotient(Integer.instantiate(1), Integer.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "\\frac{1 + 2}{3 + 4}",
            Quotient(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Quotient(Variable.instantiate("x"), Variable.instantiate("y")).variables
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