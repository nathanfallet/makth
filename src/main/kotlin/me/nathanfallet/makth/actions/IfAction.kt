package me.nathanfallet.makth.actions

import me.nathanfallet.makth.extensions.BooleanValue
import me.nathanfallet.makth.extensions.indentLines
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.AlgorithmLexer.IncorrectArgumentCountException
import me.nathanfallet.makth.resolvables.Context

data class IfAction(
        val condition: Value,
        val actions: List<Action>,
        val elseActions: List<Action> = listOf()
) : Action {

    companion object {

        fun handler(args: List<Value>): Action {
            if (args.count() != 1) {
                throw IncorrectArgumentCountException("if", args.count(), 1)
            }
            return IfAction(args[0], listOf())
        }
    }

    @Throws(Action.ExecutionException::class)
    override fun execute(context: Context): Context {
        // Eval condition
        val evaluatedCondition = condition.compute(context)

        // Check if there are missing variables
        val missingVariables = evaluatedCondition.extractVariables()
        if (missingVariables.isNotEmpty()) {
            throw Action.UnknownVariablesException(this, context, missingVariables)
        }

        // Check if condition is a boolean
        if (evaluatedCondition !is BooleanValue) {
            throw Action.NotABooleanException(this, context, evaluatedCondition)
        }

        // Execute if it is true
        return if (evaluatedCondition.value) {
            context.execute(actions)
        } else {
            context.execute(elseActions)
        }
    }

    override fun toAlgorithmString(): String {
        val builder = StringBuilder()
        builder.append("if (")
        builder.append(condition.toAlgorithmString())
        builder.append(") {")
        for (action in actions) {
            builder.append("\n")
            builder.append(action.toAlgorithmString().indentLines())
        }
        if (elseActions.isNotEmpty()) {
            builder.append("\n} else {")
            for (action in elseActions) {
                builder.append("\n")
                builder.append(action.toAlgorithmString().indentLines())
            }
        }
        builder.append("\n}")
        return builder.toString()
    }
}
