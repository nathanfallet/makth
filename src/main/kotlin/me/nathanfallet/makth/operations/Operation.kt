package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.MathLexer
import me.nathanfallet.makth.lexers.AlgorithmLexer.SyntaxException
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.sets.Vector

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
                "," -> if (left is Vector) Vector.instantiate(left.getElements() + right)
                       else Vector.instantiate(listOf(left, right))
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
                "^" -> 3
                "*", "/", "%" -> 2
                "+", "-" -> 1
                else -> 0
            }
        }

    }

}