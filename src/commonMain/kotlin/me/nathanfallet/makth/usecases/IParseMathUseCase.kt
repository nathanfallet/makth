package me.nathanfallet.makth.usecases

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.usecases.base.IPairUseCase
import kotlin.js.JsExport

@JsExport
interface IParseMathUseCase : IPairUseCase<String, Context, Value>
