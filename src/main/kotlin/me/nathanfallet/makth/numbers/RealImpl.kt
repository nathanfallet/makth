package me.nathanfallet.makth.numbers

internal data class RealImpl(private val value: Double) : Real {

    override fun getDoubleValue(): Double {
        return value
    }

}
