package me.nathanfallet.makth.sets

import me.nathanfallet.makth.interfaces.Value

internal data class MatrixImpl(private val rows: List<List<Value>>) : Matrix {

    override fun getRows(): List<List<Value>> {
        return rows
    }

    override fun getColumns(): List<List<Value>> {
        return (0 until rows.first().count()).map { i ->
            rows.map { it[i] }
        }
    }

}
