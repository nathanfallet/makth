package me.nathanfallet.makth.numbers

internal data class NaturalImpl(private val value: Long) : Natural {

    init {
        if (value < 0) {
            throw IllegalArgumentException("Natural cannot be negative!")
        }
    }

    override fun getLongValue(): Long {
        return value
    }

}
