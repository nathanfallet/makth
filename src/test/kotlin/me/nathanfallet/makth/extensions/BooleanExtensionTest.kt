package me.nathanfallet.makth.extensions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
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

    @Test
    fun testEqualsTrue() {
        assertEquals(true, BooleanValue(true).equals(BooleanValue(true)))
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(false, BooleanValue(true).equals(BooleanValue(false)))
    }

    @Test
    fun testEqualsUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            BooleanValue(true).equals(StringValue("Hello world!"))
        }
    }

}