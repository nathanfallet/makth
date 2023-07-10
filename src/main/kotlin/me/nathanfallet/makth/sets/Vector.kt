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
interface Vector : Matrix {

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
     */
    val elements: List<Value>

    // Iterable

    override val iterator: Iterator<Value> get() {
        return elements.iterator()
    }

    // Value

    override fun compute(context: Context): Value {
        return Vector.instantiate(elements.map { it.compute(context) })
    }

    override val rawString: String get() {
        return elements.joinToString("; ", "(", ")") { it.rawString }
    }

    override val laTeXString: String get() {
        return elements.joinToString(" \\\\ ", "\\begin{pmatrix} ", " \\end{pmatrix}") { it.laTeXString }
    }

    override val variables: Set<Variable> get() {
        return elements.flatMap { it.variables }.toSet()
    }

    // Matrix

    override val rows: List<List<Value>> get() {
        return elements.map { listOf(it) }
    }

    override val columns: List<List<Value>> get() {
        return listOf(this.elements)
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Vector) {
            if (elements.count() != right.elements.count()) {
                throw UnsupportedOperationException("Cannot sum vectors of different sizes")
            }
            return Vector.instantiate(elements.zip(right.elements).map { pair ->
                Sum(pair.first, pair.second).compute(Context())
            })
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Real) {
            return Vector.instantiate(elements.map { it.multiply(right) })
        }
        return super.multiply(right)
    }

}
