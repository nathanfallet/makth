package me.nathanfallet.makth.numbers

import kotlin.math.PI

internal class RealImplPi : Real {

    override fun getDoubleValue(): Double {
        return PI
    }

    override fun toRawString(): String {
        return "\u03C0"
    }

    override fun toLaTeXString(): String {
        return "\\pi"
    }

}
