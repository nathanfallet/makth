package me.nathanfallet.makth.interfaces

import kotlin.js.JsExport

/**
 * Interface for iterable values
 */
@JsExport
interface Iterable: Value {

    /**
     * Iterator
     */
    val iterator: Iterator<Value>

}
