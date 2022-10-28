package me.nathanfallet.makth.numbers

internal data class RationalImpl(
    private val numerator: Integer,
    private val denominator: Natural
) : Rational {

    init {
        if (denominator.getLongValue() == 0L) {
            throw IllegalArgumentException("Denominator cannot be null!")
        }
    }

    override fun getNumerator(): Integer {
        return numerator
    }

    override fun getDenominator(): Natural {
        return denominator
    }

}
