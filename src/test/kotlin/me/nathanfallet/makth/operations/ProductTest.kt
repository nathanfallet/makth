package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductTest {

    private val context = Context()

    @Test
    fun sumNaturals() {
        // Check that a product is computed correctly
        assertEquals(
            Integer.instantiate(6),
            Product(Integer.instantiate(2), Integer.instantiate(3)).compute(context)
        )
    }

}