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
            val gcd = numerator.getLongValue().gcd(denominator.getLongValue())
            return if (gcd != 1L) {
                val newNumeratorValue = numerator.getLongValue() / gcd
                val newDenominatorValue = denominator.getLongValue() / gcd
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
            return if (denominator.getLongValue() < 0) {
                instantiate(
                    Integer.instantiate(-1 * numerator.getLongValue()),
                    denominator.absoluteValue()
                )
            } else {
                instantiate(numerator, denominator.absoluteValue())
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
     * @return Numerator
     */
    fun getNumerator(): Integer

    /**
     * Get denominator
     * @return Denominator
     */
    fun getDenominator(): Natural

    // Real

    override fun getDoubleValue(): Double {
        return getNumerator().getDoubleValue() / getDenominator().getDoubleValue()
    }

    override fun absoluteValue(): Rational {
        return instantiate(getNumerator().absoluteValue(), getDenominator())
    }

    // Value

    override fun toRawString(): String {
        return "${getNumerator().toRawString()}/${getDenominator().toRawString()}"
    }

    override fun toLaTeXString(): String {
        return "\\frac{${getNumerator().toLaTeXString()}}{${getDenominator().toLaTeXString()}}"
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Integer) {
            val newNumerator = getNumerator().sum(getDenominator().multiply(right))
            if (newNumerator is Integer) {
                return instantiate(newNumerator, getDenominator())
            }
        }
        if (right is Rational) {
            val newNumerator = getNumerator().multiply(right.getDenominator())
                .sum(right.getNumerator().multiply(getDenominator()))
            val newDenominator = getDenominator().multiply(right.getDenominator())
            if (newNumerator is Integer && newDenominator is Integer) {
                return instantiate(newNumerator, newDenominator)
            }
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Integer) {
            val newNumerator = getNumerator().multiply(right)
            if (newNumerator is Integer) {
                return instantiate(newNumerator, getDenominator())
            }
        }
        if (right is Rational) {
            val newNumerator = getNumerator().multiply(right.getNumerator())
            val newDenominator = getDenominator().multiply(right.getDenominator())
            if (newNumerator is Integer && newDenominator is Integer) {
                return instantiate(newNumerator, newDenominator)
            }
        }
        if (right is Real) {
            return RealImpl(getDoubleValue()).multiply(right)
        }
        if (right is Matrix) {
            return Matrix.instantiate(right.getRows().map { rows ->
                rows.map { multiply(it) }
            })
        }
        return super.multiply(right)
    }

    override fun divide(right: Value): Value {
        if (right is Integer) {
            val newDenominator = getDenominator().multiply(right)
            if (newDenominator is Integer) {
                return instantiate(
                    getNumerator(),
                    newDenominator
                )
            }
        }
        if (right is Rational) {
            val newNumerator = getNumerator().multiply(right.getDenominator())
            val newDenominator = getDenominator().multiply(right.getNumerator())
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
            val newRight = getDenominator().multiply(right)
            val newNumerator = getNumerator().remainder(newRight)
            if (newRight is Integer && newNumerator is Integer) {
                return instantiate(
                    newNumerator,
                    getDenominator()
                )
            }
        }
        if (right is Rational) {
            val newNumerator = getNumerator().multiply(right.getDenominator()).remainder(right.getNumerator().multiply(getDenominator()))
            val newDenominator = getDenominator().multiply(right.getDenominator())
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
        val denominator = getDenominator()
        if (denominator != Integer.instantiate(1)) {
            val newNumerator = getNumerator().raise(right)
            val newDenominator = denominator.raise(right)
            return newNumerator.divide(newDenominator)
        }
        return super.raise(right)
    }

}