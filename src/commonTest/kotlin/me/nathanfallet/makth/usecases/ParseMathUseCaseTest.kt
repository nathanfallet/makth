package me.nathanfallet.makth.usecases

import me.nathanfallet.makth.numbers.integers.IntegerFactory
import me.nathanfallet.makth.resolvables.Context
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseMathUseCaseTest {

    private val contextWithX = Context(
        mapOf(
            "x" to IntegerFactory.instantiate(2)
        )
    )

    @Test
    fun testInvoke() {
        val useCase = ParseMathUseCase()
        assertEquals(
            IntegerFactory.instantiate(4),
            useCase("2 * x", contextWithX)
        )
    }

}
