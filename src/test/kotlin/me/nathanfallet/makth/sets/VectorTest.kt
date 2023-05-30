package me.nathanfallet.makth.sets

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.extensions.StringValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class VectorTest {

    @Test
    fun toRawString() {
        assertEquals(
            "(1, 2, 3)",
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "\\begin{pmatrix} 1 \\\\ 2 \\\\ 3 \\end{pmatrix}",
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).toLaTeXString()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Vector(listOf(Variable.instantiate("x"), Variable.instantiate("y"))).extractVariables()
        )
    }

    @Test
    fun sumCorrectVector() {
        // (1, 2, 3) + (4, 5, 6) = (5, 7, 9)
        assertEquals(
            Vector(listOf(Integer.instantiate(5), Integer.instantiate(7), Integer.instantiate(9))),
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).sum(
                Vector(listOf(Integer.instantiate(4), Integer.instantiate(5), Integer.instantiate(6)))
            )
        )
    }

    @Test
    fun sumIncorrectVector() {
        // (1, 2, 3) + (4, 5) = UnsupportedOperationException
        assertThrows(UnsupportedOperationException::class.java) {
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).sum(
                Vector(listOf(Integer.instantiate(4), Integer.instantiate(5)))
            )
        }
    }

    @Test
    fun sumUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).sum(
                StringValue("test")
            )
        }
    }

    @Test
    fun multiplyCorrectReal() {
        // (1, 2, 3) * 2 = (2, 4, 6)
        assertEquals(
            Vector(listOf(Integer.instantiate(2), Integer.instantiate(4), Integer.instantiate(6))),
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).multiply(
                Integer.instantiate(2)
            )
        )
    }

    @Test
    fun multiplyUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Vector(listOf(Integer.instantiate(1), Integer.instantiate(2), Integer.instantiate(3))).multiply(
                StringValue("test")
            )
        }
    }

}