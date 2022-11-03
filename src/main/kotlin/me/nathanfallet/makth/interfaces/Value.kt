package me.nathanfallet.makth.interfaces

import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

interface Value: Output {

    fun toAlgorithmString(): String {
        return toRawString()
    }

    fun compute(context: Context): Value

    fun toRawString(): String
    fun toLaTeXString(): String

    fun extractVariables(): List<Variable>

}