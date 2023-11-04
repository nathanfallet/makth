package me.nathanfallet.makth.numbers.naturals

import me.nathanfallet.makth.numbers.integers.Integer
import kotlin.js.JsExport

/**
 * Natural representation
 */
@JsExport
interface Natural : Integer {

    // Real

    override val absoluteValue: Natural
        get() {
        return this
    }

}