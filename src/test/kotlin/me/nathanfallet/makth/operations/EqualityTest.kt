package me.nathanfallet.makth.operations

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import me.nathanfallet.makth.sets.Matrix
import org.junit.Assert.assertEquals
import org.junit.Test

class EqualityTest {

    private val context = Context()

    private val contextWithX = Context(
        mapOf(
            "x" to Integer.instantiate(2)
        )
    )

    @Test
    fun testEqualsRealTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(1),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsRealFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsTrueWithVariable() {
        assertEquals(
            BooleanValue(true),
            Equality(Variable.instantiate("x"), Integer.instantiate(2), Equality.Operator.Equals).compute(
                contextWithX
            )
        )
    }

    @Test
    fun testEqualsMatrixTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                )),
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                )),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsMatrixFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                )),
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(4), Integer.instantiate(3))
                )),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun equalsToRawString() {
        assertEquals(
            "1 = 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.Equals).toRawString()
        )
    }

    @Test
    fun equalsToLaTeXString() {
        assertEquals(
            "1 \\eq 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.Equals).toLaTeXString()
        )
    }

    @Test
    fun testNotEqualsRealTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsRealFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(1),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsMatrixTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                )),
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(4), Integer.instantiate(3))
                )),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsMatrixFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                )),
                Matrix.instantiate(listOf(
                    listOf(Integer.instantiate(1), Integer.instantiate(2)),
                    listOf(Integer.instantiate(3), Integer.instantiate(4))
                )),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun notEqualsToRawString() {
        assertEquals(
            "1 != 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.NotEquals).toRawString()
        )
    }

    @Test
    fun notEqualsToLaTeXString() {
        assertEquals(
            "1 \\ne 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.NotEquals).toLaTeXString()
        )
    }

    @Test
    fun testLessThanTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun testLessThanFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(1),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun testLessThanFalse2() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(2),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun lessThanToRawString() {
        assertEquals(
            "1 < 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.LessThan).toRawString()
        )
    }

    @Test
    fun lessThanToLaTeXString() {
        assertEquals(
            "1 \\lt 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.LessThan).toLaTeXString()
        )
    }

    @Test
    fun testGreaterThanTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(1),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanFalse2() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(2),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun greaterThanToRawString() {
        assertEquals(
            "1 > 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.GreaterThan).toRawString()
        )
    }

    @Test
    fun greaterThanToLaTeXString() {
        assertEquals(
            "1 \\gt 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.GreaterThan).toLaTeXString()
        )
    }

    @Test
    fun testLessThanOrEqualsTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testLessThanOrEqualsTrue2() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(1),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testLessThanOrEqualsFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(1),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun lessThanOrEqualsToRawString() {
        assertEquals(
            "1 <= 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.LessThanOrEquals).toRawString()
        )
    }

    @Test
    fun lessThanOrEqualsToLaTeXString() {
        assertEquals(
            "1 \\le 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.LessThanOrEquals).toLaTeXString()
        )
    }

    @Test
    fun testGreaterThanOrEqualsTrue() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(1),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanOrEqualsTrue2() {
        assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(1),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanOrEqualsFalse() {
        assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun greaterThanOrEqualsToRawString() {
        assertEquals(
            "1 >= 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.GreaterThanOrEquals).toRawString()
        )
    }

    @Test
    fun greaterThanOrEqualsToLaTeXString() {
        assertEquals(
            "1 \\ge 2",
            Equality(Integer.instantiate(1), Integer.instantiate(2), Equality.Operator.GreaterThanOrEquals).toLaTeXString()
        )
    }

}