package me.nathanfallet.makth.numbers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.math.PI
import kotlin.math.pow
import kotlin.math.sqrt
import me.nathanfallet.makth.sets.Matrix
import me.nathanfallet.makth.extensions.StringValue

class RealTest {

    @Test
    fun toRawString() {
        assertEquals(1.23456789.toString(), Real.instantiate(1.23456789).toRawString())
    }

    @Test
    fun toLaTeXString() {
        assertEquals(1.23456789.toString(), Real.instantiate(1.23456789).toLaTeXString())
    }

    @Test
    fun absoluteValue() {
        assertEquals(
            Real.instantiate(1.23456789),
            Real.instantiate(-1.23456789).absoluteValue()
        )
    }

    @Test
    fun sumCorrectNatural() {
        // pi + 1 = 1 + pi
        assertEquals(
            Real.instantiate(1 + PI),
            Real.pi.sum(Integer.instantiate(1))
        )
    }

    @Test
    fun sumCorrectInteger() {
        // pi + -1 = pi - 1
        assertEquals(
            Real.instantiate(PI - 1),
            Real.pi.sum(Integer.instantiate(-1))
        )
    }

    @Test
    fun sumCorrectRational() {
        // pi + 1/3 = 1/3 + pi
        assertEquals(
            Real.instantiate(1.0 / 3.0 + PI),
            Real.pi.sum(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun sumCorrectReal() {
        // pi + pi = 2pi
        assertEquals(
            Real.instantiate(2 * PI),
            Real.pi.sum(Real.pi)
        )
    }

    @Test
    fun sumUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Real.pi.sum(StringValue("test"))
        }
    }

    @Test
    fun multiplyCorrectNatural() {
        // pi * 3 = 3pi
        assertEquals(
            Real.instantiate(3 * PI),
            Real.pi.multiply(Integer.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // pi * -3 = -3pi
        assertEquals(
            Real.instantiate(-3 * PI),
            Real.pi.multiply(Integer.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // pi * 1/3 = pi/3
        assertEquals(
            Real.instantiate(PI / 3),
            Real.pi.multiply(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // pi * pi = pi^2
        assertEquals(
            Real.instantiate(PI * PI),
            Real.pi.multiply(Real.pi)
        )
    }

    @Test
    fun multiplyCorrectVector() {
        // pi * [[1, 2], [3, 4]] = [[pi, 2pi], [3pi, 4pi]]
        assertEquals(
            Matrix.instantiate(listOf(
                listOf(Real.pi, Real.pi.multiply(Integer.instantiate(2))),
                listOf(Real.pi.multiply(Integer.instantiate(3)), Real.pi.multiply(Integer.instantiate(4)))
            )),
            Real.pi.multiply(Matrix.instantiate(listOf(
                listOf(Integer.instantiate(1), Integer.instantiate(2)),
                listOf(Integer.instantiate(3), Integer.instantiate(4))
            )))
        )
    }

    @Test
    fun multiplyUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Real.pi.multiply(StringValue("test"))
        }
    }

    @Test
    fun divideCorrectNatural() {
        // pi / 3 = pi/3
        assertEquals(
            Real.instantiate(PI / 3),
            Real.pi.divide(Integer.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // pi / -3 = -pi/3
        assertEquals(
            Real.instantiate(-PI / 3),
            Real.pi.divide(Integer.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // pi / 1/3 = 3pi
        assertEquals(
            Real.instantiate(3 * PI),
            Real.pi.divide(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun divideCorrectReal() {
        // pi / pi = 1
        assertEquals(
            Integer.instantiate(1),
            Real.pi.divide(Real.pi)
        )
    }

    @Test
    fun divideUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Real.pi.divide(StringValue("test"))
        }
    }

    @Test
    fun remainderCorrectNatural() {
        // pi % 3 = pi%3
        assertEquals(
            Real.instantiate(PI % 3),
            Real.pi.remainder(Integer.instantiate(3))
        )
    }

    @Test
    fun remainderCorrectInteger() {
        // pi % -3 = pi%(-3)
        assertEquals(
            Real.instantiate(PI % -3),
            Real.pi.remainder(Integer.instantiate(-3))
        )
    }

    @Test
    fun remainderCorrectRational() {
        // pi % 1/2 = pi%1/2
        assertEquals(
            Real.instantiate(PI % 0.5),
            Real.pi.remainder(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun remainderCorrectReal() {
        // pi % pi = 0
        assertEquals(
            Integer.instantiate(0),
            Real.pi.remainder(Real.pi)
        )
    }

    @Test
    fun remainderUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Real.pi.remainder(StringValue("test"))
        }
    }

    @Test
    fun raiseCorrectNatural() {
        // pi ^ 2 = pi^2
        assertEquals(
            Real.instantiate(PI * PI),
            Real.pi.raise(Integer.instantiate(2))
        )
    }

    @Test
    fun raiseCorrectInteger() {
        // pi ^ -2 = 1/(pi^2)
        assertEquals(
            Real.instantiate(1/(PI * PI)),
            Real.pi.raise(Integer.instantiate(-2))
        )
    }

    @Test
    fun raiseCorrectRational() {
        // pi ^ 1/2 = sqrt(pi)
        assertEquals(
            Real.instantiate(sqrt(PI)),
            Real.pi.raise(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun raiseCorrectReal() {
        // pi ^ pi = pi^pi
        assertEquals(
            Real.instantiate(PI.pow(PI)),
            Real.pi.raise(Real.pi)
        )
    }

    @Test
    fun raiseUnsupported() {
        assertThrows(UnsupportedOperationException::class.java) {
            Real.pi.raise(StringValue("test"))
        }
    }

    @Test
    fun instantiatePi() {
        assertEquals(
            Real.pi,
            Real.instantiate(PI)
        )
    }

    @Test
    fun piToRawString() {
        assertEquals("\u03C0", Real.pi.toRawString())
    }

    @Test
    fun piToLaTeXString() {
        assertEquals("\\pi", Real.pi.toLaTeXString())
    }

}