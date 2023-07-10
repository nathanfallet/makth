package me.nathanfallet.makth.extensions

import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable

/**
 * Boolean value
 * @param value Boolean value
 */
data class BooleanValue(val value: Boolean) : Value {

    override fun compute(context: Context): Value {
        return this
    }

    override val rawString: String get() {
        return if (value) "true" else "false"
    }

    override val laTeXString: String get() {
        return if (value) "\\text{true}" else "\\text{false}"
    }

    override val variables: Set<Variable> get() {
        return setOf()
    }

    override fun equals(right: Value): Boolean {
        if (right is BooleanValue) {
            return value == right.value
        }
        return super.equals(right)
    }

}