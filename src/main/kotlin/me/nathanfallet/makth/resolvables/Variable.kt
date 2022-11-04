package me.nathanfallet.makth.resolvables

import me.nathanfallet.makth.interfaces.Value

data class Variable(val name: String) : Value {

    override fun compute(context: Context): Value {
        return context.data[name] ?: this
    }

    override fun toRawString(): String {
        return name
    }

    override fun toLaTeXString(): String {
        return name
    }

    override fun extractVariables(): Set<Variable> {
        return setOf(this)
    }

}
