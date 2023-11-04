package me.nathanfallet.makth.exceptions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import kotlin.js.JsExport

/**
 * Exception thrown when a variable is not a boolean
 * @param action Action that failed
 * @param context Context of the action
 * @param value Value that is not a boolean
 */
@JsExport
open class NotABooleanException(
    action: Action,
    context: Context,
    val value: Value
) : ExecutionException(
    action, context,
    "Value is not a boolean: ${value.rawString}"
)