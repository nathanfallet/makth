package me.nathanfallet.makth.sets

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/*
 * Represents a matrix
 */
interface Matrix : Value {

    // Instantiate

    companion object {

        /**
         * Instantiate a matrix from a list of rows
         * @param rows List of rows
         * @return Matrix
         */
        @JvmStatic
        fun instantiate(rows: List<List<Value>>): Matrix {
            // Check that matrix is not empty and all rows have the same size
            if (rows.isEmpty() || rows.any { it.count() != rows.first().count() }) {
                throw IllegalArgumentException("Invalid matrix")
            }

            // If matrix is a vector
            if (rows.first().count() == 1) {
                return Vector.instantiate(rows.flatMap { it })
            }

            // Normal matrix
            return MatrixImpl(rows)
        }

    }

    // Matrix interface

    /**
     * Get rows
     * @return List of rows
     */
    fun getRows(): List<List<Value>>

    /**
     * Get columns
     * @return List of columns
     */
    fun getColumns(): List<List<Value>>

    // Value

    override fun compute(context: Context): Value {
        return Matrix.instantiate(getRows().map { row ->
            row.map { it.compute(context) }
        })
    }

    override fun toRawString(): String {
        return getRows().joinToString("; ", "(", ")") {
            it.joinToString(", ") { it.toRawString() }
        }
    }

    override fun toLaTeXString(): String {
        return getRows().joinToString(" \\\\ ", "\\begin{bmatrix} ", " \\end{bmatrix}") {
            it.joinToString(" & ") { it.toLaTeXString() }
        }
    }

    override fun extractVariables(): Set<Variable> {
        return getRows().flatMap { row ->
            row.flatMap { it.extractVariables() }
        }.toSet()
    }

    // Operations

    override fun sum(right: Value): Value {
        if (right is Matrix) {
            if (getRows().count() != right.getRows().count() || getColumns().count() != right.getColumns().count()) {
                throw UnsupportedOperationException("Cannot sum matrices with different sizes")
            }
            return Matrix.instantiate(getRows().zip(right.getRows()).map { rows ->
                rows.first.zip(rows.second).map { it.first.sum(it.second) }
            })
        }
        return super.sum(right)
    }

}