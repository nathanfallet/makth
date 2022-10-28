package me.nathanfallet.makth.numbers

import kotlin.math.abs

interface Integer : Rational {

    // Instantiate

    companion object {

        fun instantiate(value: Long): Integer {
            return if (value < 0) IntegerImpl(value) else Natural.instantiate(value)
        }

    }

    // Integer interface

    fun getLongValue(): Long

    // AbstractRational

    override fun getNumerator(): Integer {
        return this
    }

    override fun getDenominator(): Natural {
        return Natural.instantiate(1)
    }

    // AbstractReal

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

    override fun sum(right: Real): Real {
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
        return RealImpl(getDoubleValue()).sum(right)
    }

    override fun multiply(right: Real): Real {
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
        return RealImpl(getDoubleValue()).multiply(right)
    }

    override fun divide(right: Real): Real {
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
        return RealImpl(getDoubleValue()).divide(right)
    }

}