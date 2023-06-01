package me.nathanfallet.makth.resolvables;

import me.nathanfallet.makth.numbers.Integer;
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

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
    fun toRawString() {
        assertEquals("x", Variable.instantiate("x").toRawString())
    }

    @Test
    fun toLaTeXString() {
        assertEquals("x", Variable.instantiate("x").toLaTeXString())
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, Variable.instantiate("x").equals(Variable.instantiate("x")))
    }

    @Test
    fun testEqualsUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Variable.instantiate("x").equals(Variable.instantiate("y"))
        }
    }

}