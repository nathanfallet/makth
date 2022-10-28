package me.nathanfallet.makth.interfaces

import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

interface Action {

    // Errors

    open class ExecutionException(
        val action: Action,
        val context: Context,
        message: String
    ) : Exception(message)

    open class UnknownVariablesException(
        action: Action,
        context: Context,
        val variables: List<Variable>
    ) : ExecutionException(
        action, context,
        "Unknown variables: ${variables.joinToString(", ") { it.toString() }}"
    )

    open class NotABooleanException(
        action: Action,
        context: Context,
        val value: Value
    ) : ExecutionException(
        action, context,
        "Value is not a boolean: ${value.toRawString()}"
    )

    // Interface

    @Throws(ExecutionException::class)
    fun execute(context: Context): Context

    fun toAlgorithmString(): String

}