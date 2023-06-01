package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.MathLexer
import me.nathanfallet.makth.lexers.AlgorithmLexer.SyntaxException
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.sets.Vector
import me.nathanfallet.makth.sets.Matrix

/**
 * Interface for all operations between values
 */
interface Operation : Value {

    object Utils {

        /**
         * Initialize an operation from an operator and two values
         * @param operator Operator
         * @param left Left value
         * @param right Right value
         * @return Operation
         */
        @Throws(SyntaxException::class)
        fun initialize(operator: String, left: Value, right: Value): Value {
            return when (operator) {
                "+" -> Sum(left, right)
                "-" -> Sum(left, Product(Integer.instantiate(-1), right))
                "*" -> Product(left, right)
                "/" -> Quotient(left, right)
                "%" -> Remainder(left, right)
                "^" -> Exponentiation(left, right)
                "=" -> Equality(left, right)
                "!=" -> Equality(left, right, Equality.Operator.NotEquals)
                "<" -> Equality(left, right, Equality.Operator.LessThan)
                ">" -> Equality(left, right, Equality.Operator.GreaterThan)
                "<=" -> Equality(left, right, Equality.Operator.LessThanOrEquals)
                ">=" -> Equality(left, right, Equality.Operator.GreaterThanOrEquals)
                "," -> createHorizontalMatrix(left, right)
                ";" -> createVerticalMatrix(left, right)
                else -> throw MathLexer.UnknownOperatorException(operator)
            }
        }

        /**
         * Get the precedence of an operation
         * @param operation Operation
         * @return Precedence
         */
        fun getPrecedence(operation: String): Int {
            return when (operation) {
                "^" -> 5
                "*", "/", "%" -> 4
                "+", "-" -> 3
                "," -> 2
                ";" -> 1
                else -> 0
            }
        }

        internal fun createHorizontalMatrix(left: Value, right: Value): Matrix {
            return if (left is Matrix && left.getRows().count() == 1) {
                // We add one element to the first and only row
                Matrix.instantiate(listOf(
                    left.getRows().first() + right
                ))
            } else {
                // Create a new matrix with two elements on the only row
                Matrix.instantiate(listOf(
                    listOf(left), listOf(right)
                ))
            }
        }

        internal fun createVerticalMatrix(left: Value, right: Value): Matrix {
            return if (left is Matrix && right is Matrix && right.getRows().count() == 1) {
                // We add one row to the first matrix
                Matrix.instantiate(left.getRows() + right.getRows())
            } else {
                // Create a new matrix with two rows
                Matrix.instantiate(listOf(
                    listOf(left), listOf(right)
                ))
            }
        }

    }

}