package me.nathanfallet.makth.numbers

import me.nathanfallet.makth.sets.Matrix
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class IntegerTest {

    @Test
    fun rawString() {
        assertEquals("-1", Integer.instantiate(-1).rawString)
    }

    @Test
    fun laTeXString() {
        assertEquals("-1", Integer.instantiate(-1).laTeXString)
    }

    @Test
    fun correctNumerator() {
        assertEquals(
            Integer.instantiate(-2),
            Integer.instantiate(-2).numerator
        )
    }

    @Test
    fun correctDenominator() {
        assertEquals(
            Integer.instantiate(1),
            Integer.instantiate(-2).denominator
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
    fun multiplyCorrectMatrix() {
        // -2 * [[1, 2], [3, 4]] = [[-2, -4], [-6, -8]]
        assertEquals(
            Matrix.instantiate(listOf(
                listOf(Integer.instantiate(-2), Integer.instantiate(-4)),
                listOf(Integer.instantiate(-6), Integer.instantiate(-8))
            )),
            Integer.instantiate(-2).multiply(
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                ))
            )
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
    fun remainderCorrectNatural() {
        // -5 % 3 = -2
        assertEquals(
            Integer.instantiate(-2),
            Integer.instantiate(-5).remainder(Integer.instantiate(3))
        )
    }

    @Test
    fun remainderCorrectInteger() {
        // -5 % -3 = -2
        assertEquals(
            Integer.instantiate(-2),
            Integer.instantiate(-5).remainder(Integer.instantiate(-3))
        )
    }

    @Test
    fun remainderCorrectRational() {
        // -2 % (3/2) = -1/2
        assertEquals(
            Rational.instantiate(-1, 2),
            Integer.instantiate(-2).remainder(Rational.instantiate(3, 2))
        )
    }

    @Test
    fun remainderCorrectReal() {
        // -4 % pi = -4%pi
        assertEquals(
            Real.instantiate(-4 % PI),
            Integer.instantiate(-4).remainder(Real.pi)
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