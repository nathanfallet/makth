package me.nathanfallet.makth.extensions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class StringExtensionTest {

    @Test
    fun toAlgorithmString() {
        assertEquals("\"Hello world!\"", StringValue("Hello world!").toAlgorithmString())
    }

    @Test
    fun toRawString() {
        assertEquals("Hello world!", StringValue("Hello world!").toRawString())
    }

    @Test
    fun toLaTeXString() {
        assertEquals("\\text{Hello world!}", StringValue("Hello world!").toLaTeXString())
    }

    @Test
    fun toAlgorithmStringWithLaTeX() {
        assertEquals("\$x ^ 2\$", StringValue("x ^ 2", true).toAlgorithmString())
    }

    @Test
    fun toRawStringWithLaTeX() {
        assertEquals("x ^ 2", StringValue("x ^ 2", true).toRawString())
    }

    @Test
    fun toLaTeXStringWithLaTeX() {
        assertEquals("x ^ 2", StringValue("x ^ 2", true).toLaTeXString())
    }

    @Test
    fun toAlgorithmStringWithEscape() {
        assertEquals("\"Hello \\\"world\\\"!\"", StringValue("Hello \"world\"!").toAlgorithmString())
    }

    @Test
    fun toRawStringWithEscape() {
        assertEquals("Hello \"world\"!", StringValue("Hello \"world\"!").toRawString())
    }

    @Test
    fun toLaTeXStringWithEscape() {
        assertEquals("\\text{Hello \"world\"!}", StringValue("Hello \"world\"!").toLaTeXString())
    }

    @Test
    fun toAlgorithmStringWithLaTeXAndEscape() {
        assertEquals("\$x \\\$ 2\$", StringValue("x \$ 2", true).toAlgorithmString())
    }

    @Test
    fun toRawStringWithLaTeXAndEscape() {
        assertEquals("x \$ 2", StringValue("x \$ 2", true).toRawString())
    }

    @Test
    fun toLaTeXStringWithLaTeXAndEscape() {
        assertEquals("x \\\$ 2", StringValue("x \$ 2", true).toLaTeXString())
    }

    @Test
    fun testEqualsTrue() {
        assertEquals(true, StringValue("Hello world!").equals(StringValue("Hello world!")))
    }

    @Test
    fun testEqualsFalse() {
        assertEquals(false, StringValue("Hello world!").equals(StringValue("Hello world")))
    }

    @Test
    fun testEqualsUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            StringValue("Hello world!").equals(BooleanValue(true))
        }
    }

}