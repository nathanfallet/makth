package me.nathanfallet.makth.resolvables.variables;

import me.nathanfallet.makth.numbers.integers.IntegerFactory
import me.nathanfallet.makth.resolvables.Context
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VariableTest {

    @Test
    fun variableResolvedCorrectly() {
        assertEquals(
            IntegerFactory.instantiate(2),
            VariableFactory.instantiate("x").compute(
                Context(
                    mapOf("x" to IntegerFactory.instantiate(2))
                )
            )
        )
    }

    @Test
    fun rawString() {
        assertEquals("x", VariableFactory.instantiate("x").rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals("x", VariableFactory.instantiate("x").laTeXString)
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, VariableFactory.instantiate("x").equals(VariableFactory.instantiate("x")))
    }

    @Test
    fun testEqualsUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            VariableFactory.instantiate("x").equals(VariableFactory.instantiate("y"))
        }
    }

}