package com.example.lexer


class Lexer(val code: String) {

    private val tokens = mutableListOf<Token>()
    private var ind = 0;

    private val tokenPatterns = listOf<Pair<String, TokenType?>>(
        "scope \\{" to TokenType.OPEN_SCOPE,
        "=" to TokenType.ASSIGN,
        "print" to TokenType.PRINT,
        "\\}" to TokenType.CLOSED_SCOPE,
        "\\d+" to TokenType.NUMBER,
        "\\w+" to TokenType.IDENT,
        "\\s+" to null
    )

    fun tokenize(): List<Token> {
        while(ind < code.length) {
            var matched = false

            for ((pattern, tokenType) in tokenPatterns) {
                val regex = Regex("^${pattern}")
                val match = regex.find(code.substring(ind))

                if (match != null) {
                    if (tokenType != null) {
                        val value = match.value
                        val newValue = if (tokenType.isNumberOrVar()) value else null
                        tokens.add(Token(tokenType, newValue))
                    }
                    ind += match.value.length
                    matched = true
                    break
                }
            }
            if (!matched) {
                throw IllegalArgumentException("Invalid type at index: ${ind}")
            }

        }
        return tokens
    }
}