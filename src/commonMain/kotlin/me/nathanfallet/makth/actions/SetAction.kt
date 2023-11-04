package me.nathanfallet.makth.actions

import me.nathanfallet.makth.exceptions.ExecutionException
import me.nathanfallet.makth.exceptions.UnknownVariablesException
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentTypeException
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.variables.Variable
import kotlin.js.JsExport
import kotlin.jvm.JvmStatic

/**
 * Action that sets a variable
 * @param identifier Identifier of the variable to set
 * @param value Value to set
 */
@JsExport
data class SetAction(val identifier: String, val value: Value) : Action {

    companion object {

        /**
         * Handler for set action
         * @param args Arguments of the action
         * @return Action created from the arguments
         */
        @JvmStatic
        fun handler(args: List<Value>): Action {
            if (args.count() != 2) {
                throw IncorrectArgumentCountException("set", args.count(), 2)
            }
            val identifier = when (val identifierValue = args[0]) {
                is Variable -> identifierValue.name
                is StringValue -> identifierValue.value
                else -> throw IncorrectArgumentTypeException("set", identifierValue, Variable::class)
            }
            return SetAction(identifier, args[1])
        }
    }

    @Throws(ExecutionException::class)
    override fun execute(context: Context): Context {
        // First, compute the value with the given context
        val valueToSet = value.compute(context)

        // Check if there are missing variables
        valueToSet.variables.takeIf { it.isNotEmpty() }?.let {
            throw UnknownVariablesException(this, context, it)
        }

        // Return the new context
        return Context(context.data + mapOf(identifier to valueToSet), context.outputs)
    }

    override val algorithmString: String get() {
        return "set($identifier, ${value.algorithmString})"
    }
}
