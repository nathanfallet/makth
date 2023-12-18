package me.nathanfallet.makth.usecases

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.lexers.MathLexer
import me.nathanfallet.makth.resolvables.Context
import kotlin.js.JsExport

@JsExport
class ParseMathUseCase : IParseMathUseCase {

    override fun invoke(input1: String, input2: Context): Value {
        return MathLexer(input1).execute(input2)
    }

}
