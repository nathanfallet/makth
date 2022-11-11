package me.nathanfallet.makth.extensions

import org.junit.Assert.assertEquals
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

}