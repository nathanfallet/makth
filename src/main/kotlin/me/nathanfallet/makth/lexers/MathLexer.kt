package me.nathanfallet.makth.lexers

import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.numbers.Integer
import me.nathanfallet.makth.operations.Operation
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable


class MathLexer(private var content: String) {

    // Errors
    class SyntaxException : Exception()

    // Constants
    object Constants {
        const val VARIABLES =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZΑαΒβΓγΔδΕεΖζΗηΘθΙιΚκΛλΜμΝνΞξΟοΠπΣσςϹϲΤτΥυΦφΧχΨψΩω"
        const val VARIABLE_AND_NUMBERS = VARIABLES + "0123456789"
        const val PRODUCT_COEFFICIENTS = "$VARIABLE_AND_NUMBERS)"
        const val CONSTANTS = "ieπ"
        const val INPUT = VARIABLE_AND_NUMBERS + "_+-*/%^√,;(){}=<>! "
        val FUNCTIONS = arrayOf("sin", "cos", "tan", "sqrt", "exp", "log", "ln", "random")
    }

    // Parsing vars
    private var ops = ArrayList<String>()
    private var i = 0

    // Token vars
    private var values = ArrayList<Value>()

    // Parse an expression
    @Throws(SyntaxException::class)
    fun execute(context: Context): Value {
        // For each character of the string
        while (i < content.length) {
            val current = content[i]
            val previous = if (i > 0) content[i - 1] else ' '

            // Skip space
            if (current == ' ') {
                // Skip
            }

            // Opening brace
            else if (current == '(') {
                // Check if we have a token before without operator
                if (values.isNotEmpty() && Constants.PRODUCT_COEFFICIENTS.contains(previous)) {
                    // Check if last token is a function
                    /*if (values[0] is Variable) {
                        val prevar: Variable = values!![0] as Variable
                        if (process != null && process.variables.containsKey(prevar.getName()) && process.variables.get(
                                prevar.getName()
                            ) is FunctionDeclaration || Arrays.asList(TokenParser.funcs)
                                .contains(prevar.getName())
                        ) {
                            // Add a function operator
                            insertOperation("f")
                        } else {
                            // Add a multiplication operator
                            insertOperation("*")
                        }
                    } else {*/
                    // Add a multiplication operator
                    insertOperation("*")
                    //}
                }

                // Add it to operations
                ops.add(0, current.toString())
            }

            // String
            else if (current == '"') {
                val string = StringBuilder()
                i++

                // Get all the characters
                while (i < content.count() && content[i] != '"') {
                    // Check for \
                    if (content[i] == '\\' && i < content.count() - 1) {
                        // Skip it and get next character
                        i++
                    }

                    // Add character to string
                    string.append(content[i])
                    i++
                }

                // Insert the value
                insertValue(StringValue(string.toString()))
            }

            // Numerical
            else if (isLong(current.toString())) {
                var value = 0L
                var powerOfTen = 0L

                // Get other digits
                while (i < content.length && isLong(content[i].toString())) {
                    value = value * 10 + content[i].toString().toLong()
                    i++
                }

                // If we have a dot
                if (i < content.length - 1 && content[i] == '.') {
                    // Pass the dot
                    i++

                    // And start getting numbers after the dot
                    while (i < content.length && isLong(content[i].toString())) {
                        value = value * 10 + content[i].toString().toLong()
                        i++
                        powerOfTen++
                    }
                }

                // Check if we have a token before without operator
                if (values.isNotEmpty() && Constants.PRODUCT_COEFFICIENTS.contains(previous)) {
                    // Add a multiplication operator
                    insertOperation("*")
                }

                // Insert into values
                if (powerOfTen > 0) {
                    TODO("Power not implemented!")
                    //insertValue(Fraction(Number(`val`), Power(Number(10), Number(powerOfTen))))
                } else {
                    insertValue(Integer.instantiate(value))
                }

                // Remove one, else current character is skipped
                i--
            } else if (Constants.VARIABLES.contains(current)) {
                // Check name
                var name = StringBuilder()
                name.append(current)

                // Check for a function name
                val function = StringBuilder()
                function.append(name.toString())
                var j = i
                while (j < content.length - 1 && Constants.VARIABLE_AND_NUMBERS.contains(content[j + 1])) {
                    // Add character to function name
                    function.append(content[j + 1])

                    // Increment j to continue
                    j++
                }

                // Check if a function is recognized
                if (Constants.FUNCTIONS.contains(function.toString().lowercase())) {
                    // We have a function
                    name = function

                    // Set i to j to skip function name
                    i = j
                } else {
                    // It's a classic variable, continue

                    // Check for an index
                    if (i < content.length - 2 && content[i + 1] == '_') {
                        if (content[i + 2] == '{') {
                            // Get everything until closing brace
                            val indexBuilder = StringBuilder()
                            j = i + 2
                            while (j < content.length - 1 && Constants.INPUT.contains(content[j + 1]) && content[j + 1] != ')') {
                                // Add character to index
                                indexBuilder.append(content[j + 1])

                                // Increment j to continue
                                j++
                            }

                            // Increment i to skip brace
                            i = j + 1

                            // Trim
                            val index = indexBuilder.toString().trim()
                            if (index.isNotEmpty()) {
                                // Add index to variable
                                if (index.length == 1) {
                                    name.append('_')
                                    name.append(index)
                                } else {
                                    name.append("_{")
                                    name.append(index)
                                    name.append('}')
                                }
                            }
                        } else if (Constants.INPUT.contains(content[i + 2])) {
                            // Add index to variable
                            val index = content[i + 2]
                            name.append('_')
                            name.append(index)

                            // Increment i 2 times to skip index
                            i += 2
                        }
                    }
                }

                // Check if we have a token before without operator
                if (values.isNotEmpty() && Constants.PRODUCT_COEFFICIENTS.contains(previous)) {
                    // Add a multiplication operator
                    insertOperation("*")
                }

                // Insert into values
                insertValue(Variable(name.toString()))
            } else if (current == ')') {
                // Create the token
                while (ops.isNotEmpty() && ops[0] != "(") {
                    // Create a token
                    val value = createValue()
                    if (value != null) {
                        // Insert it into the list
                        insertValue(value)
                    }
                }

                // Remove opening brace
                if (ops.isNotEmpty()) {
                    ops.removeAt(0)
                }
            } else if (current == '√') {
                // Check if we have a token before without operator
                if (values.isNotEmpty() && Constants.PRODUCT_COEFFICIENTS.contains(previous)) {
                    // Add a multiplication operator
                    insertOperation("*")
                }

                // Insert the 2nd power
                insertValue(Integer.instantiate(2))

                // Add current operation
                insertOperation(current.toString())
            } else {
                // Insert operation
                insertOperation(current.toString())
            }

            // Increment i
            i++
        }

        // Entire expression parsed, apply remaining values
        while (ops.isNotEmpty()) {
            // Create a token
            val value = createValue()
            if (value != null) {
                // Insert it into the list
                insertValue(value)
            }
        }

        // Return token
        if (values.isNotEmpty()) {
            return values[0].compute(context)
        }

        throw SyntaxException()
    }

    private fun insertValue(value: Value) {
        // Insert the value in the list
        values.add(0, value)
    }

    @Throws(SyntaxException::class)
    private fun insertOperation(op: String) {
        // While first operation has same of greater precedence to current, apply to two first values
        while (ops.isNotEmpty() && Operation.Utils.getPrecedence(ops[0]) >= Operation.Utils.getPrecedence(
                op
            )
        ) {
            // Create a token
            val value = createValue()
            if (value != null) {
                // Insert it into the list
                insertValue(value)
            }
        }

        // If subtraction with no number before
        if (op == "-" && (values.isEmpty() || i > 0 && content[i - 1] == '(')) {
            insertValue(Integer.instantiate(0))
        }

        // If next is "="
        if (i < content.length - 1 && content[i + 1] == '=') {
            // Add it
            ops.add(0, "$op=")

            // Increment i
            i++
        } else {
            // Add current operation
            ops.add(0, op)
        }
    }

    @Throws(SyntaxException::class)
    private fun createValue(): Value? {
        // Get tokens
        val right = getFirstTokenAndRemove()
        val left = getFirstTokenAndRemove()

        // Get operator and apply
        val op = getFirstOperationAndRemove()
        return if (op != null) {
            Operation.Utils.initialize(op, left, right)
        } else null

        // Nothing found
    }

    @Throws(SyntaxException::class)
    private fun getFirstTokenAndRemove(): Value {
        // Check if exists
        if (values.isNotEmpty()) {
            return values.removeAt(0)
        }
        throw SyntaxException()
    }

    private fun getFirstOperationAndRemove(): String? {
        // Check if first
        if (ops.isNotEmpty()) {
            return ops.removeAt(0)
        }

        // Nothing found
        return null
    }

    // Check if a number is an integer
    private fun isLong(str: String): Boolean {
        try {
            str.toLong()
        } catch (nfe: NumberFormatException) {
            return false
        } catch (nfe: NullPointerException) {
            return false
        }
        return true
    }

}