package me.nathanfallet.makth.operations

import me.nathanfallet.makth.extensions.orOrThrowUnsupportedOperationException
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * Quotient operation.
 * @param left Left value
 * @param right Right value
 */
data class Quotient(
    val left: Value,
    val right: Value
) : Operation {

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)
        return try {
            left.divide(right)
        } catch (e: UnsupportedOperationException) {
            Quotient(left, right)
        }
    }

    override val rawString: String get() {
        val leftRawString = left.rawString.let {
            if (left.mainPrecedence < mainPrecedence) "($it)" else it
        }
        val rightRawString = right.rawString.let {
            if (right.mainPrecedence < mainPrecedence) "($it)" else it
        }
        return "$leftRawString / $rightRawString"
    }

    override val laTeXString: String get() {
        // We don't need to add parentheses here because it's useless with '\frac{}{}'
        return "\\frac{${left.laTeXString}}{${right.laTeXString}}"
    }

    override val variables: Set<Variable> get() {
        return left.variables + right.variables
    }

    override val mainPrecedence: Int get() {
        return Operation.Utils.getPrecedence("/")
    }

    override fun equals(right: Value): Boolean {
        if (right is Quotient) {
            return orOrThrowUnsupportedOperationException(
                { left.equals(right.left) && this.right.equals(right.right) },
                { left.equals(this.right) && right.left.equals(right.right) }
            )
        }
        return super.equals(right)
    }

}
