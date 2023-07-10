package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTest {

    private val context = Context()

    @Test
    fun rawString() {
        assertEquals(
            "1 * 2",
            Product(Integer.instantiate(1), Integer.instantiate(2)).rawString
        )
    }

    @Test
    fun toRawStringWithBraces() {
        assertEquals(
            "(1 + 2) * (3 + 4)",
            Product(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).rawString
        )
    }

    @Test
    fun toRawStringWithMinus() {
        assertEquals(
            "-x",
            Product(Integer.instantiate(-1), Variable.instantiate("x")).rawString
        )
    }

    @Test
    fun laTeXString() {
        assertEquals(
            "1 \\times 2",
            Product(Integer.instantiate(1), Integer.instantiate(2)).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithBraces() {
        assertEquals(
            "(1 + 2) \\times (3 + 4)",
            Product(Sum(Integer.instantiate(1), Integer.instantiate(2)), Sum(Integer.instantiate(3), Integer.instantiate(4))).laTeXString
        )
    }

    @Test
    fun toLaTeXStringWithMinus() {
        assertEquals(
            "-x",
            Product(Integer.instantiate(-1), Variable.instantiate("x")).laTeXString
        )
    }

    @Test
    fun variables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Product(Variable.instantiate("x"), Variable.instantiate("y")).variables
        )
    }

    @Test
    fun multiplyNaturals() {
        // Check that a product is computed correctly
        assertEquals(
            Integer.instantiate(6),
            Product(Integer.instantiate(2), Integer.instantiate(3)).compute(context)
        )
    }

}