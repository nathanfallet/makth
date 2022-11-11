package me.nathanfallet.makth.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class BooleanExtensionTest {

    @Test
    fun toAlgorithmStringTrue() {
        assertEquals("true", BooleanValue(true).toAlgorithmString())
    }

    @Test
    fun toRawStringTrue() {
        assertEquals("true", BooleanValue(true).toRawString())
    }

    @Test
    fun toLaTeXStringTrue() {
        assertEquals("\\text{true}", BooleanValue(true).toLaTeXString())
    }

    @Test
    fun toAlgorithmStringFalse() {
        assertEquals("false", BooleanValue(false).toAlgorithmString())
    }

    @Test
    fun toRawStringFalse() {
        assertEquals("false", BooleanValue(false).toRawString())
    }

    @Test
    fun toLaTeXStringFalse() {
        assertEquals("\\text{false}", BooleanValue(false).toLaTeXString())
    }

}