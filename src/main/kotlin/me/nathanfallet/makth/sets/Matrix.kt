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
     * @return List of rows
     */
    fun getRows(): List<List<Value>>

    /**
     * Get columns
     * @return List of columns
     */
    fun getColumns(): List<List<Value>>

    // Iterable

    override fun getIterator(): Iterator<Value> {
        return getRows().map {
            Matrix.instantiate(listOf(it))
        }.iterator()
    }

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

    fun transpose(): Matrix {
        return Matrix.instantiate(getColumns())
    }

    override fun equals(right: Value): Boolean {
        if (right is Matrix) {
            return getRows().count() == right.getRows().count() && getColumns().count() == right.getColumns().count() && getRows().zip(right.getRows()).all { rows ->
                rows.first.zip(rows.second).all { it.first.equals(it.second) }
            }
        }
        return super.equals(right)
    }

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

    override fun multiply(right: Value): Value {
        if (right is Matrix) {
            if (getColumns().count() != right.getRows().count()) {
                throw UnsupportedOperationException("Cannot multiply matrices with incompatible sizes")
            }
            return Matrix.instantiate(getRows().map { row ->
                right.getColumns().map { column ->
                    row.zip(column).map {
                        it.first.multiply(it.second)
                    }.reduce { a, b -> a.sum(b) }
                }
            })
        }
        return super.multiply(right)
    }

}