package me.nathanfallet.makth.numbers

import me.nathanfallet.makth.extensions.gcd
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.sets.Matrix

/**
 * Rational representation
 */
interface Rational : Real {

    // Instantiate

    companion object {

        /**
         * Instantiate a rational from a numerator and a denominator
         * @param numerator Numerator
         * @param denominator Denominator
         * @return Rational
         */
        @JvmStatic
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
                    Integer.instantiate(newNumeratorValue)
                } else {
                    RationalImpl(
                        Integer.instantiate(newNumeratorValue),
                        Natural.instantiate(newDenominatorValue)
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
        @JvmStatic
        fun instantiate(
            numerator: Integer,
            denominator: Integer
        ): Rational {
            return if (denominator.longValue < 0) {
                instantiate(
                    Integer.instantiate(-1 * numerator.longValue),
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
        @JvmStatic
        fun instantiate(
            numerator: Long,
            denominator: Long
        ): Rational {
            return instantiate(Integer.instantiate(numerator), Integer.instantiate(denominator))
        }

    }

    // Rational interface

    /**
     * Get numerator
     */
    val numerator: Integer

    /**
     * Get denominator
     */
    val denominator: Natural

    // Real

    override val doubleValue: Double get() {
        return numerator.doubleValue / denominator.doubleValue
    }

    override val absoluteValue: Rational get() {
        return instantiate(numerator.absoluteValue, denominator)
    }

    // Value

    override val rawString: String get() {
        return "${numerator.rawString}/${denominator.rawString}"
    }

    override val laTeXString: String get() {
        return "\\frac{${numerator.laTeXString}}{${denominator.laTeXString}}"
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Integer) {
            val newNumerator = numerator.sum(denominator.multiply(right))
            if (newNumerator is Integer) {
                return instantiate(newNumerator, denominator)
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator)
                .sum(right.numerator.multiply(denominator))
            val newDenominator = denominator.multiply(right.denominator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return instantiate(newNumerator, newDenominator)
            }
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Integer) {
            val newNumerator = numerator.multiply(right)
            if (newNumerator is Integer) {
                return instantiate(newNumerator, denominator)
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.numerator)
            val newDenominator = denominator.multiply(right.denominator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return instantiate(newNumerator, newDenominator)
            }
        }
        if (right is Real) {
            return RealImpl(doubleValue).multiply(right)
        }
        if (right is Matrix) {
            return Matrix.instantiate(right.rows.map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Integer) {
            val newDenominator = denominator.multiply(right)
            if (newDenominator is Integer) {
                return instantiate(
                    numerator,
                    newDenominator
                )
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator)
            val newDenominator = denominator.multiply(right.numerator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return instantiate(
                    newNumerator,
                    newDenominator
                )
            }
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Integer) {
            val newRight = denominator.multiply(right)
            val newNumerator = numerator.remainder(newRight)
            if (newRight is Integer && newNumerator is Integer) {
                return instantiate(
                    newNumerator,
                    denominator
                )
            }
        }
        if (right is Rational) {
            val newNumerator = numerator.multiply(right.denominator).remainder(right.numerator.multiply(denominator))
            val newDenominator = denominator.multiply(right.denominator)
            if (newNumerator is Integer && newDenominator is Integer) {
                return instantiate(
                    newNumerator,
                    newDenominator
                )
            }
        }
        return super.remainder(right)
    }

    override fun raise(right: Value): Value {
        val denominator = denominator
        if (denominator != Integer.instantiate(1)) {
            val newNumerator = numerator.raise(right)
            val newDenominator = denominator.raise(right)
            return newNumerator.divide(newDenominator)
        }
        return super.raise(right)
    }

}