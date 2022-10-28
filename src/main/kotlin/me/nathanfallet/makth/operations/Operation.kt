package me.nathanfallet.makth.operations

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.MathLexer
import me.nathanfallet.makth.numbers.Integer

interface Operation : Value {

    object Utils {

        @Throws(MathLexer.SyntaxException::class)
        fun initialize(operation: String, left: Value, right: Value): Value {
            return when (operation) {
                "+" -> Sum(left, right)
                "-" -> Sum(left, Product(Integer.instantiate(-1), right))
                "*" -> Product(left, right)
                "/" -> Quotient(left, right)
                "=" -> Equality(left, right)
                "!=" -> Equality(left, right, Equality.Operator.NotEquals)
                "<" -> Equality(left, right, Equality.Operator.LessThan)
                ">" -> Equality(left, right, Equality.Operator.GreaterThan)
                "<=" -> Equality(left, right, Equality.Operator.LessThanOrEquals)
                ">=" -> Equality(left, right, Equality.Operator.GreaterThanOrEquals)
                else -> throw MathLexer.SyntaxException()
            }
        }

        fun getPrecedence(operation: String): Int {
            return when (operation) {
                "+", "-" -> 1
                "*", "/" -> 2
                else -> 0
            }
        }

    }

}