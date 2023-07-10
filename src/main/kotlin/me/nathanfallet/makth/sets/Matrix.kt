package me.nathanfallet.makth.sets

import me.nathanfallet.makth.interfaces.Iterable
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/*
 * Represents a matrix
 */
interface Matrix : Iterable {

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
     */
    val rows: List<List<Value>>

    /**
     * Get columns
     */
    val columns: List<List<Value>>

    // Iterable

    override val iterator: Iterator<Value> get() {
        return rows.map {
            Matrix.instantiate(listOf(it))
        }.iterator()
    }

    // Value

    override fun compute(context: Context): Value {
        return Matrix.instantiate(rows.map { row ->
            row.map { it.compute(context) }
        })
    }

    override val rawString: String get() {
        return rows.joinToString("; ", "(", ")") {
            it.joinToString(", ") { it.rawString }
        }
    }

    override val laTeXString: String get() {
        return rows.joinToString(" \\\\ ", "\\begin{bmatrix} ", " \\end{bmatrix}") {
            it.joinToString(" & ") { it.laTeXString }
        }
    }

    override val variables: Set<Variable> get() {
        return rows.flatMap { row ->
            row.flatMap { it.variables }
        }.toSet()
    }

    // Operations

    fun transpose(): Matrix {
        return Matrix.instantiate(columns)
    }

    override fun equals(right: Value): Boolean {
        if (right is Matrix) {
            return rows.count() == right.rows.count() && columns.count() == right.columns.count() && rows.zip(right.rows).all { rows ->
                rows.first.zip(rows.second).all { it.first.equals(it.second) }
            }
        }
        return super.equals(right)
    }

    override fun sum(right: Value): Value {
        if (right is Matrix) {
            if (rows.count() != right.rows.count() || columns.count() != right.columns.count()) {
                throw UnsupportedOperationException("Cannot sum matrices with different sizes")
            }
            return Matrix.instantiate(rows.zip(right.rows).map { rows ->
                rows.first.zip(rows.second).map { it.first.sum(it.second) }
            })
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Matrix) {
            if (columns.count() != right.rows.count()) {
                throw UnsupportedOperationException("Cannot multiply matrices with incompatible sizes")
            }
            return Matrix.instantiate(rows.map { row ->
                right.columns.map { column ->
                    row.zip(column).map {
                        it.first.multiply(it.second)
                    }.reduce { a, b -> a.sum(b) }
                }
            })
        }
        return super.multiply(right)
    }

}