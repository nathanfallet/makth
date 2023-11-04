package me.nathanfallet.makth.numbers.rationals

import me.nathanfallet.makth.extensions.gcd
import me.nathanfallet.makth.numbers.integers.Integer
import me.nathanfallet.makth.numbers.integers.IntegerFactory
import me.nathanfallet.makth.numbers.naturals.Natural
import me.nathanfallet.makth.numbers.naturals.NaturalFactory
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
object RationalFactory {

    /**
     * Instantiate a rational from a numerator and a denominator
     * @param numerator Numerator
     * @param denominator Denominator
     * @return Rational
     */
    @JsName("instantiateWithIntegerAndNatural")
    fun instantiate(
        numerator: Integer,
        denominator: Natural
    ): Rational {
        // Get GCD to simplify
        val gcd = numerator.longValue.gcd(denominator.longValue)
        return if (gcd != 1L) {
            val newNumeratorValue = numerator.longValue / gcd
            val newDenominatorValue = denominator.longValue / gcd
            if (newDenominatorValue == 1L) {
                IntegerFactory.instantiate(newNumeratorValue)
            } else {
                RationalImpl(
                    IntegerFactory.instantiate(newNumeratorValue),
                    NaturalFactory.instantiate(newDenominatorValue)
                )
            }
        } else {
            RationalImpl(numerator, denominator)
        }
    }

    /**
     * Instantiate a rational from a numerator and a denominator
     * @param numerator Numerator
     * @param denominator Denominator
     * @return Rational
     */
    @JsName("instantiateWithIntegers")
    fun instantiate(
        numerator: Integer,
        denominator: Integer
    ): Rational {
        return if (denominator.longValue < 0) {
            instantiate(
                IntegerFactory.instantiate(-1 * numerator.longValue),
                denominator.absoluteValue
            )
        } else {
            instantiate(numerator, denominator.absoluteValue)
        }
    }

    /**
     * Instantiate a rational from a numerator and a denominator
     * @param numerator Numerator
     * @param denominator Denominator
     * @return Rational
     */
    fun instantiate(
        numerator: Long,
        denominator: Long
    ): Rational {
        return instantiate(IntegerFactory.instantiate(numerator), IntegerFactory.instantiate(denominator))
    }

}