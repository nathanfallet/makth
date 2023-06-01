package me.nathanfallet.makth.operations

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.sets.Vector

/**
 * Equality operation.
 * @param left Left value
 * @param right Right value
 * @param operator Operator, default to Equals
 */
data class Equality(
    val left: Value,
    val right: Value,
    val operator: Operator = Operator.Equals
) : Operation {

    /**
     * Available operators for equalities
     */
    enum class Operator {

        Equals, NotEquals, LessThan, GreaterThan, LessThanOrEquals, GreaterThanOrEquals;

        /**
         * Get the raw string for this operator
         * @return Raw string
         */
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

        /**
         * Get the LaTeX string for this operator
         * @return LaTeX string
         */
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

        return try {
            return BooleanValue(when (operator) {
                Operator.Equals -> left.equals(right)
                Operator.NotEquals -> !left.equals(right)
                Operator.LessThan -> left.lessThan(right)
                Operator.GreaterThan -> !(left.lessThan(right) || left.equals(right))
                Operator.LessThanOrEquals -> left.lessThan(right) || left.equals(right)
                Operator.GreaterThanOrEquals -> !left.lessThan(right)
            })
        } catch (e: UnsupportedOperationException) {
            Equality(left, right, operator)
        }
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

    override fun getMainPrecedence(): Int {
        return Operation.Utils.getPrecedence("=")
    }

    override fun equals(right: Value): Boolean {
        if (right is Equality) {
            return left.equals(right.left) && this.right.equals(right.right) && operator == right.operator
        }
        return super.equals(right)
    }

}
