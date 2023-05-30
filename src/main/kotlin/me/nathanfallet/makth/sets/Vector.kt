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
data class Vector(
    val elements: List<Value>
) : Value {

    override fun compute(context: Context): Value {
        return Vector(elements.map { it.compute(context) })
    }

    override fun toRawString(): String {
        return elements.joinToString(", ", "(", ")") { it.toRawString() }
    }

    override fun toLaTeXString(): String {
        return elements.joinToString(" \\\\ ", "\\begin{pmatrix} ", " \\end{pmatrix}") { it.toLaTeXString() }
    }

    override fun extractVariables(): Set<Variable> {
        return elements.flatMap { it.extractVariables() }.toSet()
    }

    override fun sum(right: Value): Value {
        if (right is Vector) {
            if (elements.size != right.elements.size) {
                throw UnsupportedOperationException("Cannot sum vectors of different sizes")
            }
            return Vector(elements.zip(right.elements).map { pair ->
                Sum(pair.first, pair.second).compute(Context())
            })
        }
        return super.sum(right)
    }

    override fun multiply(right: Value): Value {
        if (right is Real) {
            return Vector(elements.map { it.multiply(right) })
        }
        return super.multiply(right)
    }

}
