package me.nathanfallet.makth.sets

import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.extensions.StringValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class MatrixTest {

    @Test
    fun toRawString() {
        assertEquals(
            "(1, 2; 3, 4)",
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).toRawString()
        )
    }

    @Test
    fun toLaTeXString() {
        assertEquals(
            "\\begin{bmatrix} 1 & 2 \\\\ 3 & 4 \\end{bmatrix}",
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).toLaTeXString()
        )
    }

    @Test
    fun getRows() {
        assertEquals(
            listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            ),
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).getRows()
        )
    }

    @Test
    fun getColumns() {
        assertEquals(
            listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(3)),
                listOf(Integer.instantiate(2), Integer.instantiate(4))
            ),
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).getColumns()
        )
    }

    @Test
    fun extractVariables() {
        assertEquals(
            setOf(Variable.instantiate("x"), Variable.instantiate("y")),
            Matrix.instantiate(listOf(
                listOf(Variable.instantiate("x"), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Variable.instantiate("y"))
            )).extractVariables()
        )
    }

    @Test
    fun instantiateInvalidMatrix() {
        assertThrows(IllegalArgumentException::class.java) {
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3))
            ))
        }
    }

    @Test
    fun instantiateReal() {
        assertEquals(
            Integer.instantiate(1),
            Matrix.instantiate(listOf(listOf(Integer.instantiate(1))))
        )
    }

    @Test
    fun transposition() {
        assertEquals(
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(3)),
                listOf(Integer.instantiate(2), Integer.instantiate(4))
            )),
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).transpose()
        )
    }

    @Test
    fun sumCorrectMatrix() {
        assertEquals(
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(6), Integer.instantiate(8)),
                listOf(Integer.instantiate(10), Integer.instantiate(12))
            )),
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).sum(Matrix.instantiate(listOf(
                listOf(Integer.instantiate(5), Integer.instantiate(6)),
                listOf(Integer.instantiate(7), Integer.instantiate(8))
            )))
        )
    }

    @Test
    fun sumIncorrectMatrix() {
        assertThrows(UnsupportedOperationException::class.java) {
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).sum(Matrix.instantiate(listOf(
                listOf(Integer.instantiate(5), Integer.instantiate(6)),
                listOf(Integer.instantiate(7), Integer.instantiate(8)),
                listOf(Integer.instantiate(9), Integer.instantiate(10))
            )))
        }
    }

    @Test
    fun sumUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).sum(StringValue("test"))
        }
    }

    @Test
    fun multiplyCorrectMatrix() {
        assertEquals(
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(3), Integer.instantiate(2340)),
                listOf(Integer.instantiate(0), Integer.instantiate(1000))
            )),
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(2), Integer.instantiate(3), Integer.instantiate(4)),
                listOf(Integer.instantiate(1), Integer.instantiate(0), Integer.instantiate(0))
            )).multiply(Matrix.instantiate(listOf(
                listOf(Integer.instantiate(0), Integer.instantiate(1000)),
                listOf(Integer.instantiate(1), Integer.instantiate(100)),
                listOf(Integer.instantiate(0), Integer.instantiate(10))
            )))
        )
    }

    @Test
    fun multiplyIncorrectMatrix() {
        assertThrows(UnsupportedOperationException::class.java) {
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(2), Integer.instantiate(3), Integer.instantiate(4)),
                listOf(Integer.instantiate(1), Integer.instantiate(0), Integer.instantiate(0))
            )).multiply(Matrix.instantiate(listOf(
                listOf(Integer.instantiate(2), Integer.instantiate(3), Integer.instantiate(4)),
                listOf(Integer.instantiate(1), Integer.instantiate(0), Integer.instantiate(0))
            )))
        }
    }

    @Test
    fun multiplyUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )).multiply(StringValue("test"))
        }
    }

}