package me.nathanfallet.makth.lexers

import me.nathanfallet.makth.actions.IfAction
import me.nathanfallet.makth.actions.PrintAction
import me.nathanfallet.makth.actions.SetAction
import me.nathanfallet.makth.actions.WhileAction
import me.nathanfallet.makth.extensions.StringValue
import me.nathanfallet.makth.interfaces.Action
import me.nathanfallet.makth.interfaces.Value
import me.nathanfallet.makth.resolvables.Context
import me.nathanfallet.makth.resolvables.Variable
import kotlin.reflect.KClass

class AlgorithmLexer(private var content: String) {

    // Errors

    open class SyntaxException(
        message: String
    ) : Exception(message)

    open class UnknownKeywordException(
        val keyword: String
    ) : SyntaxException("Unknown keyword: $keyword")

    open class UnexpectedKeywordException(
        val keyword: String
    ) : SyntaxException("Unexpected keyword: $keyword")

    open class UnexpectedBraceException(
        val character: String
    ) : SyntaxException("Unexpected brace: $character")

    open class IncorrectArgumentCountException(
        val keyword: String,
        val count: Int,
        val expected: Int
    ) : SyntaxException("Incorrect argument count for $keyword, got $count, expected $expected")

    open class IncorrectArgumentTypeException(
        val keyword: String,
        val value: Value,
        val expected: KClass<out Value>
    ) : SyntaxException("Incorrect argument type for $keyword, got ${value.toRawString()}, expected $expected")

    // Constants

    object Constants {
        const val CHARACTERS = "abcdefghijklmnopqrstuvwxyz_"
    }

    // Parsing vars

    private var lastKeyword: String? = null
    private var arguments = ArrayList<Value>()
    private var i = 0
    private var actions = ArrayList<Action>()

    // Keyword handlers

    private var keywordHandlers: Map<String, (List<Value>) -> Action> = mapOf(
        "print" to { args ->
            PrintAction(args.toList())
        },
        "set" to { args ->
            if (args.count() != 2) {
                throw IncorrectArgumentCountException("set", arguments.count(), 2)
            }
            val identifier = when (val identifierValue = args[0]) {
                is Variable -> identifierValue.name
                is StringValue -> identifierValue.value
                else -> throw IncorrectArgumentTypeException(
                    "set",
                    identifierValue,
                    Variable::class
                )
            }
            SetAction(identifier, args[1])
        },
        "if" to { args ->
            if (args.count() != 1) {
                throw IncorrectArgumentCountException("if", arguments.count(), 1)
            }
            IfAction(args[0], listOf())
        },
        "while" to { args ->
            if (args.count() != 1) {
                throw IncorrectArgumentCountException("while", arguments.count(), 1)
            }
            WhileAction(args[0], listOf())
        }
    )

    fun registerKeyword(keyword: String, handler: (List<Value>) -> Action): AlgorithmLexer {
        keywordHandlers += mapOf(keyword to handler)
        return this
    }

    fun registerKeywords(keywords: Map<String, (List<Value>) -> Action>): AlgorithmLexer {
        keywordHandlers += keywords
        return this
    }

    // Parse an algorithm

    @Throws(SyntaxException::class)
    fun execute(): List<Action> {
        // For each character of the string
        while (i < content.length) {
            val current = content[i]

            // Opening curly brace
            if (current == '{') {
                // Some vars
                i++
                val subContent = StringBuilder()
                var count = 0

                // Get text until its closing brace
                while (content[i] != '}' || count != 0) {
                    // Add current
                    subContent.append(content[i])

                    // Check for another opening brace
                    if (content[i] == '{') {
                        count++
                    }

                    // Closing
                    if (content[i] == '}') {
                        count--
                    }

                    // Increment i
                    i++
                }

                // Parse block
                val block = AlgorithmLexer(subContent.toString())
                    .registerKeywords(keywordHandlers)
                    .execute()

                // Get last action
                when (val lastAction = actions.removeLastOrNull()) {
                    is IfAction -> {
                        when (lastKeyword) {
                            null -> actions.add(
                                IfAction(
                                    lastAction.condition,
                                    block
                                )
                            )
                            "else" -> actions.add(
                                IfAction(
                                    lastAction.condition,
                                    lastAction.actions,
                                    block
                                )
                            )
                            else -> throw UnexpectedKeywordException(lastKeyword ?: "none")
                        }
                    }
                    is WhileAction -> {
                        actions.add(
                            WhileAction(
                                lastAction.condition,
                                block
                            )
                        )
                    }
                    else -> throw UnexpectedBraceException("{")
                }
            }

            // Opening brace
            else if (current == '(') {
                // Some vars
                i++
                val argument = StringBuilder()
                var count = 0

                // Get text until its closing brace
                while (content[i] != ')' || count != 0) {
                    // Add current
                    argument.append(content[i])

                    // Check for special characters
                    when (content[i]) {
                        '(' -> count++
                        ')' -> count--
                        ',' -> {
                            argument.deleteAt(argument.count() - 1) // Remove ','
                            arguments.add(MathLexer(argument.toString().trim()).execute(Context()))
                            argument.clear()
                        }
                    }

                    // Increment i
                    i++
                }

                // Add last
                val lastArgument = argument.toString().trim()
                if (lastArgument.isNotEmpty()) {
                    arguments.add(MathLexer(lastArgument).execute(Context()))
                }

                // Create an action with those args
                actions.add(createAction())
            }

            // Keywords
            else if (Constants.CHARACTERS.indexOf(current) != -1) {
                val keyword = StringBuilder()

                // Get all the characters
                while (i < content.length && Constants.CHARACTERS.indexOf(content[i]) != -1) {
                    keyword.append(content[i])
                    i++
                }

                // Save keyword
                if (lastKeyword == null) {
                    lastKeyword = keyword.toString()
                } else {
                    throw UnexpectedKeywordException(keyword.toString())
                }

                // Remove one, else current character is skipped
                i--
            }

            // Increment i
            i++
        }

        // Return actions
        return actions
    }

    private fun createAction(): Action {
        // Check for the keyword
        val result = keywordHandlers[lastKeyword ?: "none"]?.invoke(arguments) ?: throw
            if (lastKeyword != null) UnknownKeywordException(lastKeyword ?: "none")
            else UnexpectedBraceException("(")

        // Clear storage
        arguments.clear()
        lastKeyword = null

        // Return the result action
        return result
    }

}