package me.nathanfallet.makth.numbers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.math.PI

class RationalTest {

    @Test
    fun toRawString() {
        assertEquals("-1/2", Rational.instantiate(-1, 2).toRawString())
    }

    @Test
    fun toLaTeXString() {
        assertEquals("\\frac{-1}{2}", Rational.instantiate(-1, 2).toLaTeXString())
    }

    @Test
    fun nullDenominatorThrows() {
        assertThrows(IllegalArgumentException::class.java) {
            Rational.instantiate(1, 0)
        }
    }

    @Test
    fun simplifyToRational() {
        // 2/4 = 1/2
        assertEquals(
            Rational.instantiate(1, 2),
            Rational.instantiate(2, 4)
        )
    }

    @Test
    fun simplifyToInteger() {
        // 4/2 = 2
        assertEquals(
            Integer.instantiate(2),
            Rational.instantiate(4, 2)
        )
    }

    @Test
    fun absoluteValue() {
        assertEquals(
            Rational.instantiate(1, 2),
            Rational.instantiate(-1, 2).absoluteValue()
        )
    }

    @Test
    fun sumCorrectNatural() {
        // 1/2 + 1 = 3/2
        assertEquals(
            Rational.instantiate(3, 2),
            Rational.instantiate(1, 2).sum(Integer.instantiate(1))
        )
    }

    @Test
    fun sumCorrectInteger() {
        // 1/2 + -1 = -1/2
        assertEquals(
            Rational.instantiate(-1, 2),
            Rational.instantiate(1, 2).sum(Integer.instantiate(-1))
        )
    }

    @Test
    fun sumCorrectRational() {
        // 1/2 + 1/3 = 5/6
        assertEquals(
            Rational.instantiate(5, 6),
            Rational.instantiate(1, 2).sum(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun sumCorrectReal() {
        // 1/2 + pi = 1/2 + pi
        assertEquals(
            Real.instantiate(0.5 + PI),
            Rational.instantiate(1, 2).sum(Real.pi)
        )
    }

    @Test
    fun multiplyCorrectNatural() {
        // 1/2 * 3 = 3/2
        assertEquals(
            Rational.instantiate(3, 2),
            Rational.instantiate(1, 2).multiply(Integer.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // 1/2 * -3 = -3/2
        assertEquals(
            Rational.instantiate(-3, 2),
            Rational.instantiate(1, 2).multiply(Integer.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // 1/2 * 1/3 = 1/6
        assertEquals(
            Rational.instantiate(1, 6),
            Rational.instantiate(1, 2).multiply(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // 1/2 * pi = 1/2 * pi
        assertEquals(
            Real.instantiate(0.5 * PI),
            Rational.instantiate(1, 2).multiply(Real.pi)
        )
    }

    @Test
    fun divideCorrectNatural() {
        // 1/2 / 3 = 1/6
        assertEquals(
            Rational.instantiate(1, 6),
            Rational.instantiate(1, 2).divide(Integer.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // 1/2 / -3 = -1/6
        assertEquals(
            Rational.instantiate(-1, 6),
            Rational.instantiate(1, 2).divide(Integer.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // 1/2 / 1/3 = 3/2
        assertEquals(
            Rational.instantiate(3, 2),
            Rational.instantiate(1, 2).divide(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun divideCorrectReal() {
        // 1/2 / pi = 1/2 * pi
        assertEquals(
            Real.instantiate(0.5 / PI),
            Rational.instantiate(1, 2).divide(Real.pi)
        )
    }

}