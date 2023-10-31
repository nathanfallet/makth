package me.nathanfallet.makth.sets

import me.nathanfallet.makth.interfaces.Value

internal data class VectorImpl(
    override val elements: List<Value>
) : Vector
