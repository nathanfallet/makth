package me.nathanfallet.makth.numbers

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
