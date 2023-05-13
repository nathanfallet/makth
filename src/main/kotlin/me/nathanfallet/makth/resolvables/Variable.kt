package me.nathanfallet.makth.resolvables

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Real
import me.nathanfallet.makth.extensions.BooleanValue

/**
 * Variable representation
 */
data class Variable private constructor(val name: String) : Value {

    companion object {

        /**
         * Instantiate a variable from a name
         * @param name Variable name
         * @return Variable instance
         */
        @JvmStatic
        fun instantiate(name: String): Value {
            // Check for booleans
            if (name == "true" || name == "false") {
                return BooleanValue(name == "true")
            }

            // Check for some constants
            if (name == "pi" || name == "\u03C0") {
                return Real.pi
            }

            return Variable(name)
        }

    }

    override fun compute(context: Context): Value {
        return context.data[name] ?: this
    }

    override fun toRawString(): String {
        return name
    }

    override fun toLaTeXString(): String {
        return name
    }

    override fun extractVariables(): Set<Variable> {
        return setOf(this)
    }

}
