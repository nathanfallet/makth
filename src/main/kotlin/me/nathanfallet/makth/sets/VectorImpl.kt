package me.nathanfallet.makth.sets

import me.nathanfallet.makth.interfaces.Value

internal data class VectorImpl(private val elements: List<Value>) : Vector {

    override fun getElements(): List<Value> {
        return elements
    }

}
