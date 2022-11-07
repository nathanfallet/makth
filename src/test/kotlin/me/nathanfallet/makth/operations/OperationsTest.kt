package me.nathanfallet.makth.operations;

import me.nathanfallet.makth.lexers.MathLexer.SyntaxException
import me.nathanfallet.makth.numbers.Integer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class OperationsTest {

    @Test
    fun syntaxException() {
        assertThrows(SyntaxException::class.java) {
            Operation.Utils.initialize("a", Integer.instantiate(1), Integer.instantiate(2))
        }
    }

    @Test
    fun precedenceExponentiateOverMultiply() {
        assertTrue(
            Operation.Utils.getPrecedence("^") > Operation.Utils.getPrecedence("*")
        )
    }

    @Test
    fun precedenceMultiplyOverSum() {
        assertTrue(
            Operation.Utils.getPrecedence("*") > Operation.Utils.getPrecedence("+")
        )
    }

    @Test
    fun precedenceSumOverEquals() {
        assertTrue(
            Operation.Utils.getPrecedence("+") > Operation.Utils.getPrecedence("=")
        )
    }

    @Test
    fun precedenceSumSameAsDifference() {
        assertTrue(
            Operation.Utils.getPrecedence("+") == Operation.Utils.getPrecedence("-")
        )
    }

    @Test
    fun precedenceMultiplySameAsDivide() {
        assertTrue(
            Operation.Utils.getPrecedence("*") == Operation.Utils.getPrecedence("/")
        )
    }

    @Test
    fun precedenceMultiplySameAsRemainder() {
        assertTrue(
            Operation.Utils.getPrecedence("*") == Operation.Utils.getPrecedence("%")
        )
    }

    @Test
    fun precedenceNotEqualsSameAsEquals() {
        assertTrue(
            Operation.Utils.getPrecedence("!=") == Operation.Utils.getPrecedence("=")
        )
    }

    @Test
    fun precedenceLessThanSameAsEquals() {
        assertTrue(
            Operation.Utils.getPrecedence("<") == Operation.Utils.getPrecedence("=")
        )
    }

    @Test
    fun precedenceGreaterThanSameAsEquals() {
        assertTrue(
            Operation.Utils.getPrecedence(">") == Operation.Utils.getPrecedence("=")
        )
    }

    @Test
    fun precedenceLessThanOrEqualsSameAsEquals() {
        assertTrue(
            Operation.Utils.getPrecedence("<=") == Operation.Utils.getPrecedence("=")
        )
    }

    @Test
    fun precedenceGreaterThanOrEqualsSameAsEquals() {
        assertTrue(
            Operation.Utils.getPrecedence(">=") == Operation.Utils.getPrecedence("=")
        )
    }

}