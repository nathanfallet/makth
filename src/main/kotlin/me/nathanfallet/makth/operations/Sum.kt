package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.extensions.orOrThrowUnsupportedOperationException

/**
 * Sum operation.
 * @param left Left value
 * @param right Right value
 */
data class Sum(
    val left: Value,
    val right: Value
) : Operation {

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)
        return try {
            left.sum(right)
        } catch (e: UnsupportedOperationException) {
            Sum(left, right)
        }
    }

    override fun toRawString(): String {
        val leftRawString = left.toRawString().let {
            if (left.getMainPrecedence() < getMainPrecedence()) "($it)" else it
        }
        val rightRawString = right.toRawString().let {
            if (right.getMainPrecedence() < getMainPrecedence()) "($it)" else it
        }
        if (rightRawString.startsWith("-")) {
            return "$leftRawString - ${rightRawString.substring(1)}"
        }
        return "$leftRawString + $rightRawString"
    }

    override fun toLaTeXString(): String {
        val leftLaTeXString = left.toLaTeXString().let {
            if (left.getMainPrecedence() < getMainPrecedence()) "($it)" else it
        }
        val rightLaTeXString = right.toLaTeXString().let {
            if (right.getMainPrecedence() < getMainPrecedence()) "($it)" else it
        }
        if (rightLaTeXString.startsWith("-")) {
            return "$leftLaTeXString - ${rightLaTeXString.substring(1)}"
        }
        return "$leftLaTeXString + $rightLaTeXString"
    }

    override fun extractVariables(): Set<Variable> {
        return left.extractVariables() + right.extractVariables()
    }

    override fun getMainPrecedence(): Int {
        return Operation.Utils.getPrecedence("+")
    }

    override fun equals(right: Value): Boolean {
        if (right is Sum) {
            return orOrThrowUnsupportedOperationException(
                { left.equals(right.left) && this.right.equals(right.right) },
                { left.equals(right.right) && this.right.equals(right.left) }
            )
        }
        return super.equals(right)
    }

    override fun lessThan(right: Value): Boolean {
        if (right is Sum) {
            return orOrThrowUnsupportedOperationException(
                { left.lessThan(right.left) && this.right.lessThan(right.right) },
                { left.lessThan(right.left) && this.right.equals(right.right) },
                { left.lessThan(right.right) && this.right.lessThan(right.left) },
                { left.lessThan(right.right) && this.right.equals(right.left) },
                { left.equals(right.left) && this.right.lessThan(right.right) },
                { left.equals(right.right) && this.right.lessThan(right.left) }
            )
        }
        return super.lessThan(right)
    }

}
