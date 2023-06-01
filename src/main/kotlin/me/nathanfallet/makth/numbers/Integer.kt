package me.nathanfallet.makth.numbers

import kotlin.math.abs
import kotlin.math.pow
import me.nathanfallet.makth.extensions.pow
import me.nathanfallet.makth.extensions.nthRoot
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.sets.Matrix

/**
 * Integer representation
 */
interface Integer : Rational {

    // Instantiate

    companion object {

        /**
         * Instantiate an integer from a long value
         * @param value Long value
         * @return Integer
         */
        @JvmStatic
        fun instantiate(value: Long): Integer {
            return if (value < 0) IntegerImpl(value) else Natural.instantiate(value)
        }

    }

    // Integer interface

    /**
     * Get long value
     * @return Long value
     */
    fun getLongValue(): Long

    // Rational

    override fun getNumerator(): Integer {
        return this
    }

    override fun getDenominator(): Natural {
        return Natural.instantiate(1)
    }

    // Real

    override fun getDoubleValue(): Double {
        return getLongValue().toDouble()
    }

    override fun absoluteValue(): Natural {
        return Natural.instantiate(abs(getLongValue()))
    }

    // Value

    override fun toRawString(): String {
        return getLongValue().toString()
    }

    override fun toLaTeXString(): String {
        return getLongValue().toString()
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Integer) {
            return instantiate(getLongValue() + right.getLongValue())
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.getDenominator())
                .sum(right.getNumerator())
            if (newNumerator is Integer) {
                return Rational.instantiate(newNumerator, right.getDenominator())
            }
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Integer) {
            return instantiate(getLongValue() * right.getLongValue())
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.getNumerator())
            if (newNumerator is Integer) {
                return Rational.instantiate(
                    newNumerator,
                    right.getDenominator()
                )
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
            return Rational.instantiate(
                this,
                right
            )
        }
        if (right is Rational) {
            val newNumerator = this.multiply(right.getDenominator())
            if (newNumerator is Integer) {
                return Rational.instantiate(
                    newNumerator,
                    right.getNumerator()
                )
            }
        }
        return super.divide(right)
    }

    override fun remainder(right: Value): Value {
        if (right is Integer) {
            return instantiate(getLongValue() % right.getLongValue())
        }
        if (right is Rational) {
            val newNumerator = getNumerator().multiply(right.getDenominator()).remainder(right.getNumerator())
            if (newNumerator is Integer) {
                return Rational.instantiate(
                    newNumerator,
                    right.getDenominator()
                )
            }
        }
        return super.remainder(right)
    }

    override fun raise(right: Value): Value {
        if (right is Integer) {
            return if (right.getLongValue() < 0) {
                Rational.instantiate(
                    instantiate(1),
                    instantiate(getLongValue().pow(-right.getLongValue()).toLong())
                )
            } else {
                instantiate(getLongValue().pow(right.getLongValue()).toLong())
            }
        }
        if (right is Rational) {
            val firstRaised = raise(right.getNumerator())
            if (firstRaised is Integer) {
                return if (firstRaised.getDoubleValue() < 0) {
                    // In case of negative number, we need to take the oppposite
                    // and check if the denominator is even (to avoid complex case).
                    if (right.getDenominator().getLongValue() and 1 == 0L) {
                        Real.instantiate(Double.NaN)
                    } else {
                        Real.instantiate(
                            -(-firstRaised.getLongValue())
                            .nthRoot(right.getDenominator().getLongValue())
                        )
                    }
                } else {
                    Real.instantiate(firstRaised.getLongValue().nthRoot(right.getDenominator().getLongValue()))
                }
            }
        }
        return super.raise(right)
    }

}