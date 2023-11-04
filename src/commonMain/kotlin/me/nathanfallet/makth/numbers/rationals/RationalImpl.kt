package me.nathanfallet.makth.numbers.rationals

import me.nathanfallet.makth.numbers.integers.Integer
import me.nathanfallet.makth.numbers.naturals.Natural

internal data class RationalImpl(
    override val numerator: Integer,
    override val denominator: Natural
) : Rational {

    init {
        if (denominator.longValue == 0L) {
            throw IllegalArgumentException("Denominator cannot be null!")
        }
    }

}
