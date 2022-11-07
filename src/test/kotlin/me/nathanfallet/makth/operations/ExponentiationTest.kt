package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class ExponentiationTest {

    private val context = Context()

    @Test
    fun toRawString() {
        assertEquals(
            "1 ^ 2",
            Exponentiation(Integer.instantiate(1), Integer.instantiate(2)).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "1^{2}",
            Exponentiation(Integer.instantiate(1), Integer.instantiate(2)).toLaTeXString()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable("x"), Variable("y")),
            Exponentiation(Variable("x"), Variable("y")).extractVariables()
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