package me.nathanfallet.makth.usecases

import dev.kaccelero.usecases.IPairUseCase
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import kotlin.js.JsExport

@JsExport
interface IParseMathUseCase : IPairUseCase<String, Context, Value>
