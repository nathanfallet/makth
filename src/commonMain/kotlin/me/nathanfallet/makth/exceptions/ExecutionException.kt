package me.nathanfallet.makth.exceptions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.resolvables.Context
import kotlin.js.JsExport

/**
 * Base class for all execution errors
 * @param action Action that failed
 * @param context Context of the action
 * @param message Error message
 */
@JsExport
open class ExecutionException(
    val action: Action,
    val context: Context,
    message: String
) : Exception(message)
