package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.extensions.orOrThrowUnsupportedOperationException

/**
 * Product operation.
 * @param left Left value
 * @param right Right value
 */
data class Product(
    val left: Value,
    val right: Value
) : Operation {

    override fun compute(context: Context): Value {
        val left = left.compute(context)
        val right = right.compute(context)
        return try {
            left.multiply(right)
        } catch (e: UnsupportedOperationException) {
            Product(left, right)
        }
    }

    override val rawString: String get() {
        val leftRawString = left.rawString.let {
            if (left.mainPrecedence < mainPrecedence) "($it)" else it
        }
        val rightRawString = right.rawString.let {
            if (right.mainPrecedence < mainPrecedence) "($it)" else it
        }
        if (leftRawString == "-1") {
            return "-$rightRawString"
        }
        return "$leftRawString * $rightRawString"
    }

    override val laTeXString: String get() {
        val leftLaTeXString = left.laTeXString.let {
            if (left.mainPrecedence < mainPrecedence) "($it)" else it
        }
        val rightLaTeXString = right.laTeXString.let {
            if (right.mainPrecedence < mainPrecedence) "($it)" else it
        }
        if (leftLaTeXString == "-1") {
            return "-$rightLaTeXString"
        }
        return "$leftLaTeXString \\times $rightLaTeXString"
    }

    override val variables: Set<Variable> get() {
        return left.variables + right.variables
    }

    override val mainPrecedence: Int get() {
        return Operation.Utils.getPrecedence("*")
    }

    override fun equals(right: Value): Boolean {
        if (right is Product) {
            return orOrThrowUnsupportedOperationException(
                { left.equals(right.left) && this.right.equals(right.right) },
                { left.equals(right.right) && this.right.equals(right.left) }
            )
        }
        return super.equals(right)
    }

}
