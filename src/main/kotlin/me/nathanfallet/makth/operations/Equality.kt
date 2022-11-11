package me.nathanfallet.makth.operations

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

data class Equality(
    val left: Value,
    val right: Value,
    val operator: Operator = Operator.Equals
) : Operation {

    enum class Operator {

        Equals, NotEquals, LessThan, GreaterThan, LessThanOrEquals, GreaterThanOrEquals;

        fun toRawString(): String {
            return when (this) {
                Equals -> "="
                NotEquals -> "!="
                LessThan -> "<"
                GreaterThan -> ">"
                LessThanOrEquals -> "<="
                GreaterThanOrEquals -> ">="
            }
        }

        fun toLaTeXString(): String {
            return when (this) {
                Equals -> "\\eq"
                NotEquals -> "\\ne"
                LessThan -> "\\lt"
                GreaterThan -> "\\gt"
                LessThanOrEquals -> "\\le"
                GreaterThanOrEquals -> "\\ge"
            }
        }

    }

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)

        if (left is Real && right is Real) {
            val leftDouble = left.getDoubleValue()
            val rightDouble = right.getDoubleValue()
            return BooleanValue(when (operator) {
                Operator.Equals -> leftDouble == rightDouble
                Operator.NotEquals -> leftDouble != rightDouble
                Operator.LessThan -> leftDouble < rightDouble
                Operator.GreaterThan -> leftDouble > rightDouble
                Operator.LessThanOrEquals -> leftDouble <= rightDouble
                Operator.GreaterThanOrEquals -> leftDouble >= rightDouble
            })
        }

        return Equality(left, right, operator)
    }

    override fun toRawString(): String {
        return "${left.toRawString()} ${operator.toRawString()} ${right.toRawString()}"
    }

    override fun toLaTeXString(): String {
        return "${left.toLaTeXString()} ${operator.toLaTeXString()} ${right.toLaTeXString()}"
    }

    override fun extractVariables(): Set<Variable> {
        return left.extractVariables() + right.extractVariables()
    }

}
