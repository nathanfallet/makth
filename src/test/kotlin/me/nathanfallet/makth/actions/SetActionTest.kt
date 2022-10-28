package me.nathanfallet.makth.actions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Sum
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import org.junit.Assert
import org.junit.Test

class SetActionTest {

    private val context = Context()

    private val contextWithX = Context(
        hashMapOf(
            Pair("x", Integer.instantiate(2))
        )
    )

    private val contextWithXAndY = Context(
        hashMapOf(
            Pair("x", Integer.instantiate(2)),
            Pair("y", Integer.instantiate(4))
        )
    )

    @Test
    fun toRawString() {
        Assert.assertEquals(
            "set(x, 2)",
            SetAction("x", Integer.instantiate(2)).toAlgorithmString()
        )
    }

    @Test
    fun setInteger() {
        Assert.assertEquals(
            contextWithX,
            context.execute(SetAction("x", Integer.instantiate(2)))
        )
    }

    @Test
    fun setIntegerWithVariable() {
        Assert.assertEquals(
            contextWithXAndY,
            contextWithX.execute(SetAction("y", Sum(Variable("x"), Integer.instantiate(2))))
        )
    }

    @Test
    fun setIntegerWithVariableWithoutContext() {
        Assert.assertThrows(Action.UnknownVariablesException::class.java) {
            context.execute(SetAction("y", Sum(Variable("x"), Integer.instantiate(2))))
        }
    }

}