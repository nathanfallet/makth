package me.nathanfallet.makth.numbers

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI
import kotlin.math.pow

class IntegerTest {

    @Test
    fun toRawString() {
        assertEquals("-1", Integer.instantiate(-1).toRawString())
    }

    @Test
    fun toLaTeXString() {
        assertEquals("-1", Integer.instantiate(-1).toLaTeXString())
    }

    @Test
    fun correctNumerator() {
        assertEquals(
            Integer.instantiate(-2),
            Integer.instantiate(-2).getNumerator()
        )
    }

    @Test
    fun correctDenominator() {
        assertEquals(
            Integer.instantiate(1),
            Integer.instantiate(-2).getDenominator()
        )
    }

    @Test
    fun sumCorrectNatural() {
        // -2 + 1 = -1
        assertEquals(Integer.instantiate(-1), Integer.instantiate(-2).sum(Integer.instantiate(1)))
    }

    @Test
    fun sumCorrectInteger() {
        // -2 + -1 = -3
        assertEquals(Integer.instantiate(-3), Integer.instantiate(-2).sum(Integer.instantiate(-1)))
    }

    @Test
    fun sumCorrectRational() {
        // -1 + 1/2 = -1/2
        assertEquals(
            Rational.instantiate(-1, 2),
            Integer.instantiate(-1).sum(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun sumCorrectReal() {
        // -2 + pi = -2 + pi
        assertEquals(
            Real.instantiate(-2 + PI),
            Integer.instantiate(-2).sum(Real.pi)
        )
    }

    @Test
    fun multiplyCorrectNatural() {
        // -2 * 3 = -6
        assertEquals(
            Integer.instantiate(-6),
            Integer.instantiate(-2).multiply(Integer.instantiate(3))
        )
    }

    @Test
    fun multiplyCorrectInteger() {
        // -2 * -3 = 6
        assertEquals(
            Integer.instantiate(6),
            Integer.instantiate(-2).multiply(Integer.instantiate(-3))
        )
    }

    @Test
    fun multiplyCorrectRational() {
        // -3 * 1/2 = -3/2
        assertEquals(
            Rational.instantiate(-3, 2),
            Integer.instantiate(-3).multiply(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun multiplyCorrectReal() {
        // -2 * pi = -2 * pi
        assertEquals(
            Real.instantiate(-2 * PI),
            Integer.instantiate(-2).multiply(Real.pi)
        )
    }

    @Test
    fun divideCorrectNatural() {
        // -2 / 3 = -2/3
        assertEquals(
            Rational.instantiate(-2, 3),
            Integer.instantiate(-2).divide(Integer.instantiate(3))
        )
    }

    @Test
    fun divideCorrectInteger() {
        // -2 / -3 = 2/3
        assertEquals(
            Rational.instantiate(2, 3),
            Integer.instantiate(-2).divide(Integer.instantiate(-3))
        )
    }

    @Test
    fun divideCorrectRational() {
        // -3 / (5/2) = -6/5
        assertEquals(
            Rational.instantiate(-6, 5),
            Integer.instantiate(-3).divide(Rational.instantiate(5, 2))
        )
    }

    @Test
    fun divideCorrectReal() {
        // -2 / pi = -2 / pi
        assertEquals(
            Real.instantiate(-2 / PI),
            Integer.instantiate(-2).divide(Real.pi)
        )
    }

    @Test
    fun raiseCorrectNatural() {
        // -2 ^ 2 = 4
        assertEquals(
            Integer.instantiate(4),
            Integer.instantiate(-2).raise(Integer.instantiate(2))
        )
    }

    @Test
    fun raiseCorrectInteger() {
        // -2 ^ -2 = 1/4
        assertEquals(
            Rational.instantiate(1, 4),
            Integer.instantiate(-2).raise(Integer.instantiate(-2))
        )
    }

    @Test
    fun raiseCorrectRational() {
        // -8 ^ (1/3) = -2
        assertEquals(
            Integer.instantiate(-2),
            Integer.instantiate(-8).raise(Rational.instantiate(1, 3))
        )
    }

}