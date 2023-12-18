package me.nathanfallet.makth.usecases

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.usecases.base.IPairUseCase
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
interface IParseAlgorithmUseCase : IPairUseCase<String, Map<String, (List<Value>) -> Action>, List<Action>> {

    @JsName("invokeDefault")
    operator fun invoke(input: String): List<Action> {
        return invoke(input, emptyMap())
    }

}
