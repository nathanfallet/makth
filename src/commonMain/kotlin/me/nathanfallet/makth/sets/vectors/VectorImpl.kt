package me.nathanfallet.makth.sets.vectors

import me.nathanfallet.makth.interfaces.Value

internal data class VectorImpl(
    override val elements: List<Value>
) : Vector
