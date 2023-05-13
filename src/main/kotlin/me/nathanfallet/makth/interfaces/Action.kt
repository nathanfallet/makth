package me.nathanfallet.makth.interfaces

import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * Interface for all actions that can be executed
 */
interface Action {

    // Errors

    /**
     * Base class for all execution errors
     * @param action Action that failed
     * @param context Context of the action
     * @param message Error message
     */
    open class ExecutionException(
        val action: Action,
        val context: Context,
        message: String
    ) : Exception(message)

    /**
     * Exception thrown when one (or more) variable is not found
     * @param action Action that failed
     * @param context Context of the action
     * @param variable Variables that were not found
     */
    open class UnknownVariablesException(
        action: Action,
        context: Context,
        val variables: Set<Variable>
    ) : ExecutionException(
        action, context,
        "Unknown variable(s): ${variables.joinToString(", ") { it.name }}"
    )

    /**
     * Exception thrown when a variable is not a boolean
     * @param action Action that failed
     * @param context Context of the action
     * @param value Value that is not a boolean
     */
    open class NotABooleanException(
        action: Action,
        context: Context,
        val value: Value
    ) : ExecutionException(
        action, context,
        "Value is not a boolean: ${value.toRawString()}"
    )

    // Interface

    /**
     * Execute the action in the given context
     * @param context Context of the action
     * @return New context after the action was executed
     */
    @Throws(ExecutionException::class)
    fun execute(context: Context): Context

    /**
     * Convert the action to a string that can be parsed by the algorithm lexer
     * @return String representation of the action
     */
    fun toAlgorithmString(): String

}