package me.nathanfallet.makth.usecases

import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.AlgorithmLexer
import kotlin.js.JsExport

@JsExport
class ParseAlgorithmUseCase : IParseAlgorithmUseCase {

    override fun invoke(input1: String, input2: Map<String, (List<Value>) -> Action>): List<Action> {
        return AlgorithmLexer(input1).registerKeywords(input2).execute()
    }

}
