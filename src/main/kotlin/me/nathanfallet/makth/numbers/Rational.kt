package me.nathanfallet.makth.numbers

import me.nathanfallet.makth.extensions.gcd

interface Rational : Real {

    // Instantiate

    companion object {

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

        fun instantiate(
            numerator: Long,
            denominator: Long
        ): Rational {
            return instantiate(Integer.instantiate(numerator), Integer.instantiate(denominator))
        }

    }

    // Rational interface

    fun getNumerator(): Integer
    fun getDenominator(): Natural

    // AbstractReal

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

    override fun sum(right: Real): Real {
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
        return RealImpl(getDoubleValue()).sum(right)
    }

    override fun multiply(right: Real): Real {
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
        return RealImpl(getDoubleValue()).multiply(right)
    }

    override fun divide(right: Real): Real {
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
        return RealImpl(getDoubleValue()).divide(right)
    }

}