package me.nathanfallet.makth.numbers

internal data class IntegerImpl(private val value: Long) :
    Integer {

    override fun getLongValue(): Long {
        return value
    }

}
