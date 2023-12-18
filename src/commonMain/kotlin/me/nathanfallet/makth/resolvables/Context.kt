package me.nathanfallet.makth.resolvables

import me.nathanfallet.makth.exceptions.ExecutionException
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Output
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.usecases.context.IContext
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Context class
 * @param data Variables in current memory
 * @param outputs Outputs list
 */
@JsExport
data class Context(
    val data: Map<String, Value> = mapOf(),
    val outputs: List<Output> = listOf(),
) : IContext {

    /**
     * Execute an action in this context
     * @param action Action to execute
     * @return New context after execution
     */
    @JsName("executeAction")
    @Throws(ExecutionException::class)
    fun execute(action: Action): Context {
        return action.execute(this)
    }

    /**
     * Execute a list of actions in this context
     * @param actions Actions to execute
     * @return New context after execution
     */
    @JsName("executeActions")
    @Throws(ExecutionException::class)
    fun execute(actions: List<Action>): Context {
        var context = this
        for (action in actions) {
            context = action.execute(context)
        }
        return context
    }

}
