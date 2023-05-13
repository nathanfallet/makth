package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class SumTest {

    private val context = Context()

    @Test
    fun toRawString() {
        assertEquals(
            "1 + 2",
            Sum(Integer.instantiate(1), Integer.instantiate(2)).toRawString()
        )
    }

    @Test
    fun toRawStringWithBraces() {
        // This will technically never happen, but it's the only way to test it
        assertEquals(
            "(1 = 1) + (2 = 2)",
            Sum(Equality(Integer.instantiate(1), Integer.instantiate(1)), Equality(Integer.instantiate(2), Integer.instantiate(2))).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "1 + 2",
            Sum(Integer.instantiate(1), Integer.instantiate(2)).toLaTeXString()
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        // This will technically never happen, but it's the only way to test it
        assertEquals(
            "(1 \\eq 1) + (2 \\eq 2)",
            Sum(Equality(Integer.instantiate(1), Integer.instantiate(1)), Equality(Integer.instantiate(2), Integer.instantiate(2))).toLaTeXString()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Sum(Variable.instantiate("x"), Variable.instantiate("y")).extractVariables()
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

}