package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class ExponentiationTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 ^ 2",
            Exponentiation(Integer.instantiate(1), Integer.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) ^ (3 + 4)",
            Exponentiation(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "1^{2}",
            Exponentiation(Integer.instantiate(1), Integer.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "(1 + 2)^{3 + 4}",
            Exponentiation(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Exponentiation(Variable.instantiate("x"), Variable.instantiate("y")).variables
        )
    }

    @Test
    fun exponentiateNaturals() {
        // Check that an exponentiation is computed correctly
        assertEquals(
            Integer.instantiate(8),
            Exponentiation(Integer.instantiate(2), Integer.instantiate(3)).compute(context)
        )
    }

}