package me.nathanfallet.makth.operations

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import org.junit.Assert.assertEquals
import org.junit.Test

class SumTest {

    private val context = Context()

    @Test
    fun sumNaturals() {
        // Check that a sum is computed correctly
        assertEquals(
            Integer.instantiate(3),
            Sum(Integer.instantiate(1), Integer.instantiate(2)).compute(context)
        )
    }

}