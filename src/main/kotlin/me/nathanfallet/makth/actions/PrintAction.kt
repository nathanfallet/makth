package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context

/**
 * Action that prints values
 * @param values Values to print
 */
data class PrintAction(val values: List<Value>) : Action {

    companion object {

        /**
         * Handler for print action
         * @param args Arguments of the action
         * @return Action created from the arguments
         */
        @JvmStatic
        fun handler(args: List<Value>): Action {
            return PrintAction(args.toList())
        }
    }

    @Throws(Action.ExecutionException::class)
    override fun execute(context: Context): Context {
        // Generate output
        val output =
                values.map {
                    // Compute value
                    val computed = it.compute(context)

                    // Check for missing variables
                    val missingVariables = computed.extractVariables()
                    if (missingVariables.isNotEmpty()) {
                        throw Action.UnknownVariablesException(this, context, missingVariables)
                    }

                    // Return
                    computed
                } + listOf(StringValue("\n"))

        // Return the new context
        return Context(context.data, context.outputs + output)
    }

    override fun toAlgorithmString(): String {
        val builder = StringBuilder()
        builder.append("print(")
        builder.append(values.joinToString(", ") { it.toAlgorithmString() })
        builder.append(")")
        return builder.toString()
    }
}
