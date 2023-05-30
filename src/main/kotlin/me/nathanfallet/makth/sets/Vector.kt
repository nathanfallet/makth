package me.nathanfallet.makth.sets

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.numbers.Real

/**
 * Vector representation
 * It's a set of elements of the same type
 * @param elements List of elements
 */
interface Vector : Value {

    // Instantiate

    companion object {

        /**
         * Instantiate a vector from a list of elements
         * @param elements List of elements
         * @return Vector
         */
        @JvmStatic
        fun instantiate(elements: List<Value>): Vector {
            if (elements.count() == 1 && elements.first() is Vector) {
                return elements.first() as Vector
            }
            return VectorImpl(elements)
        }

    }

    // Vector interface

    /**
     * Get elements
     * @return List of elements
     */
    fun getElements(): List<Value>

    // Value

    override fun compute(context: Context): Value {
        return Vector.instantiate(getElements().map { it.compute(context) })
    }

    override fun toRawString(): String {
        return getElements().joinToString(", ", "(", ")") { it.toRawString() }
    }

    override fun toLaTeXString(): String {
        return getElements().joinToString(" \\\\ ", "\\begin{pmatrix} ", " \\end{pmatrix}") { it.toLaTeXString() }
    }

    override fun extractVariables(): Set<Variable> {
        return getElements().flatMap { it.extractVariables() }.toSet()
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Vector) {
            if (getElements().size != right.getElements().size) {
                throw UnsupportedOperationException("Cannot sum vectors of different sizes")
            }
            return Vector.instantiate(getElements().zip(right.getElements()).map { pair ->
                Sum(pair.first, pair.second).compute(Context())
            })
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Real) {
            return Vector.instantiate(getElements().map { it.multiply(right) })
        }
        return super.multiply(right)
    }

}
