package me.nathanfallet.makth.resolvables;

import me.nathanfallet.makth.numbers.Integer;
import org.junit.Assert.assertEquals
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

}