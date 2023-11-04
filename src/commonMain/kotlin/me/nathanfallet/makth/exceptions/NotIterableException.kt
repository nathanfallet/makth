package me.nathanfallet.makth.exceptions

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import kotlin.js.JsExport

/**
 * Exception thrown when a variable is not iterable
 * @param action Action that failed
 * @param context Context of the action
 * @param value Value that is not iterable
 */
@JsExport
open class NotIterableException(
    action: Action,
    context: Context,
    val value: Value
) : ExecutionException(
    action, context,
    "Value is not iterable: ${value.rawString}"
)