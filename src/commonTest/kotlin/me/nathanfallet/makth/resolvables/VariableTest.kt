package me.nathanfallet.makth.resolvables;

import me.nathanfallet.makth.numbers.Integer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class VariableTest {

    @Test
    fun variableResolvedCorrectly() {
        assertEquals(
            Integer.instantiate(2),
            Variable.instantiate("x").compute(Context(
                mapOf("x" to Integer.instantiate(2))
            ))
        )
    }

    @Test
    fun rawString() {
        assertEquals("x", Variable.instantiate("x").rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals("x", Variable.instantiate("x").laTeXString)
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, Variable.instantiate("x").equals(Variable.instantiate("x")))
    }

    @Test
    fun testEqualsUnsupported() {
        assertFailsWith(UnsupportedOperationException::class) {
            Variable.instantiate("x").equals(Variable.instantiate("y"))
        }
    }

}