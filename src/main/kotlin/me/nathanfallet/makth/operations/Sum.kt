package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

data class Sum(
    val left: Value,
    val right: Value
) : Operation {

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)

        if (left is Real && right is Real) {
            return left.sum(right)
        }

        return Sum(left, right)
    }

    override fun toRawString(): String {
        return "${left.toRawString()} + ${right.toRawString()}"
    }

    override fun toLaTeXString(): String {
        return "${left.toLaTeXString()} + ${right.toLaTeXString()}"
    }

    override fun extractVariables(): Set<Variable> {
        return left.extractVariables() + right.extractVariables()
    }

}
