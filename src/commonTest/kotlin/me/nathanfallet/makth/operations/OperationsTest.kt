package me.nathanfallet.makth.operations;

import me.nathanfallet.makth.lexers.AlgorithmLexer.SyntaxException
import me.nathanfallet.makth.numbers.Integer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class OperationsTest {

    @Test
    fun syntaxException() {
        assertFailsWith(SyntaxException::class) {
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
        assertEquals(Operation.Utils.getPrecedence("+"), Operation.Utils.getPrecedence("-"))
    }

    @Test
    fun precedenceMultiplySameAsDivide() {
        assertEquals(Operation.Utils.getPrecedence("*"), Operation.Utils.getPrecedence("/"))
    }

    @Test
    fun precedenceMultiplySameAsRemainder() {
        assertEquals(Operation.Utils.getPrecedence("*"), Operation.Utils.getPrecedence("%"))
    }

    @Test
    fun precedenceNotEqualsSameAsEquals() {
        assertEquals(Operation.Utils.getPrecedence("!="), Operation.Utils.getPrecedence("="))
    }

    @Test
    fun precedenceLessThanSameAsEquals() {
        assertEquals(Operation.Utils.getPrecedence("<"), Operation.Utils.getPrecedence("="))
    }

    @Test
    fun precedenceGreaterThanSameAsEquals() {
        assertEquals(Operation.Utils.getPrecedence(">"), Operation.Utils.getPrecedence("="))
    }

    @Test
    fun precedenceLessThanOrEqualsSameAsEquals() {
        assertEquals(Operation.Utils.getPrecedence("<="), Operation.Utils.getPrecedence("="))
    }

    @Test
    fun precedenceGreaterThanOrEqualsSameAsEquals() {
        assertEquals(Operation.Utils.getPrecedence(">="), Operation.Utils.getPrecedence("="))
    }

}