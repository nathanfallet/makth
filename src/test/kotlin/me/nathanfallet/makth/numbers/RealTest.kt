package me.nathanfallet.makth.numbers

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.math.PI

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

}