package me.nathanfallet.makth.extensions

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * String value
 * @param value String value
 */
data class StringValue(val value: String, val latex: Boolean = false) : Value {

    override fun compute(context: Context): Value {
        return this
    }

    override fun toAlgorithmString(): String {
        val char = if (latex) "$" else "\""
        return "$char${value.replace("$char", "\\$char")}$char"
    }

    override fun toRawString(): String {
        return value
    }

    override fun toLaTeXString(): String {
        return if (latex) value.replace("\$", "\\\$") else "\\text{${value.replace("{", "\\{").replace("}", "\\}")}}"
    }

    override fun extractVariables(): Set<Variable> {
        return setOf()
    }

}

fun String.indentLines(): String {
    return split("\n").joinToString("\n") { "    $it" }
}