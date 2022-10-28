package me.nathanfallet.makth.operations

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert
import org.junit.Test

class EqualityTest {

    private val context = Context()

    private val contextWithX = Context(
        hashMapOf(
            Pair("x", Integer.instantiate(2))
        )
    )

    @Test
    fun testEqualsTrue() {
        Assert.assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(1),
                Equality.Operator.Equals
            ).compute(context)
        )
    }

    @Test
    fun testEqualsFalse() {
        Assert.assertEquals(
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
        Assert.assertEquals(
            BooleanValue(true),
            Equality(Variable("x"), Integer.instantiate(2), Equality.Operator.Equals).compute(
                contextWithX
            )
        )
    }

    @Test
    fun testNotEqualsTrue() {
        Assert.assertEquals(
            BooleanValue(true),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testNotEqualsFalse() {
        Assert.assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(1),
                Equality.Operator.NotEquals
            ).compute(context)
        )
    }

    @Test
    fun testLessThanTrue() {
        Assert.assertEquals(
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
        Assert.assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(1),
                Equality.Operator.LessThan
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanTrue() {
        Assert.assertEquals(
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
        Assert.assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.GreaterThan
            ).compute(context)
        )
    }

    @Test
    fun testLessThanOrEqualsTrue() {
        Assert.assertEquals(
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
        Assert.assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(2),
                Integer.instantiate(1),
                Equality.Operator.LessThanOrEquals
            ).compute(context)
        )
    }

    @Test
    fun testGreaterThanOrEqualsTrue() {
        Assert.assertEquals(
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
        Assert.assertEquals(
            BooleanValue(false),
            Equality(
                Integer.instantiate(1),
                Integer.instantiate(2),
                Equality.Operator.GreaterThanOrEquals
            ).compute(context)
        )
    }

}