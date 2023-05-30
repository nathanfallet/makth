package me.nathanfallet.makth.numbers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.math.PI
import kotlin.math.pow
import me.nathanfallet.makth.sets.Vector

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
    fun mutliplyCorrectVector() {
        // 1/2 * [1, 2] = [1/2, 1]
        assertEquals(
            Vector.instantiate(listOf(Rational.instantiate(1, 2), Integer.instantiate(1))),
            Rational.instantiate(1, 2).multiply(Vector.instantiate(listOf(Integer.instantiate(1), Integer.instantiate(2)))))
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

    @Test
    fun remainderCorrectNatural() {
        // 5/2 % 2 = 1/2
        assertEquals(
            Rational.instantiate(1, 2),
            Rational.instantiate(5, 2).remainder(Integer.instantiate(2))
        )
    }

    @Test
    fun remainderCorrectInteger() {
        // 5/2 % -2 = 1/2
        assertEquals(
            Rational.instantiate(1, 2),
            Rational.instantiate(5, 2).remainder(Integer.instantiate(-2))
        )
    }

    @Test
    fun remainderCorrectRational() {
        // 5/2 % 1/3 = 1/6
        assertEquals(
            Rational.instantiate(1, 6),
            Rational.instantiate(5, 2).remainder(Rational.instantiate(1, 3))
        )
    }

    @Test
    fun remainderCorrectReal() {
        // 7/2 % pi = 7/2%pi
        assertEquals(
            Real.instantiate(3.5 % PI),
            Rational.instantiate(7, 2).remainder(Real.pi)
        )
    }

    @Test
    fun raiseCorrectNatural() {
        // 2/3 ^ 2 = 4/9
        assertEquals(
            Rational.instantiate(4, 9),
            Rational.instantiate(2, 3).raise(Integer.instantiate(2))
        )
    }

    @Test
    fun raiseCorrectInteger() {
        // 2/3 ^ -2 = 9/4
        assertEquals(
            Rational.instantiate(9, 4),
            Rational.instantiate(2, 3).raise(Integer.instantiate(-2))
        )
    }

    @Test
    fun raiseCorrectRational() {
        // 4/9 ^ 1/2 = 2/3
        assertEquals(
            Rational.instantiate(2, 3),
            Rational.instantiate(4, 9).raise(Rational.instantiate(1, 2))
        )
    }

    @Test
    fun raiseCorrectReal() {
        // 1/2 ^ pi = 1/(2^pi)
        assertEquals(
            Real.instantiate(1.0 / 2.0.pow(PI)),
            Rational.instantiate(1, 2).raise(Real.pi)
        )
    }

}