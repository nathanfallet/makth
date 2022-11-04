package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTest {

    private val context = Context()

    @Test
    fun toRawString() {
        assertEquals(
            "1 * 2",
            Product(Integer.instantiate(1), Integer.instantiate(2)).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "1 \\times 2",
            Product(Integer.instantiate(1), Integer.instantiate(2)).toLaTeXString()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable("x"), Variable("y")),
            Product(Variable("x"), Variable("y")).extractVariables()
        )
    }

    @Test
    fun sumNaturals() {
        // Check that a product is computed correctly
        assertEquals(
            Integer.instantiate(6),
            Product(Integer.instantiate(2), Integer.instantiate(3)).compute(context)
        )
    }

}