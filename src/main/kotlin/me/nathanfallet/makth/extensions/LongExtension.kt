package me.nathanfallet.makth.extensions

import kotlin.math.abs

fun Long.gcd(b: Long): Long {
    // Binary GCD algorithm (faster than classic GCD)

    if (this == 0L) return b
    if (b == 0L) return this

    var u = abs(this)
    var v = abs(b)
    u = u shr u.countTrailingZeroBits()

    do {
        v = v shr v.countTrailingZeroBits()
        if (u > v) {
            val t = v
            v = u
            u = t
        }
        v -= u
    } while (v != 0L)

    return u shl (this or b).countTrailingZeroBits()
}