package me.nathanfallet.makth.extensions

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

data class StringValue(val value: String) : Value {

    override fun compute(context: Context): Value {
        return this
    }

    override fun toAlgorithmString(): String {
        return "\"${value.replace("\"", "\\\"")}\""
    }

    override fun toRawString(): String {
        return value
    }

    override fun toLaTeXString(): String {
        return "\\text{$value}"
    }

    override fun extractVariables(): List<Variable> {
        return listOf()
    }

}

fun String.getValue(): StringValue {
    return StringValue(this)
}

fun String.indentLines(): String {
    val builder = StringBuilder()
    for (s in split("\n").toTypedArray()) {
        if (builder.isNotEmpty()) {
            builder.append("\n")
        }
        builder.append("    ")
        builder.append(s)
    }
    return builder.toString()
}