package me.nathanfallet.makth.interfaces

import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * Interface for all values that can be computed
 */
interface Value: Output {

    /**
     * Convert the value to a string that can be parsed by the algorithm lexer
     * @return Algorithm string
     */
    fun toAlgorithmString(): String {
        return toRawString()
    }

    /**
     * Compute the value in the given context
     * @param context Context of the value
     * @return Computed value
     */
    fun compute(context: Context): Value

    /**
     * Convert the value to a raw string
     * @return Raw string
     */
    fun toRawString(): String

    /**
     * Convert the value to a LaTeX string
     * @return LaTeX string
     */
    fun toLaTeXString(): String

    /**
     * Extract all variables from the value
     * @return Set of variables
     */
    fun extractVariables(): Set<Variable>

    /**
     * Get the precedence of the value, used for braces
     * @return Precedence
     */
    fun getMainPrecedence(): Int {
        return Int.MAX_VALUE
    }

}