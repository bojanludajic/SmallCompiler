package com.example.lexer

enum class TokenType {
    IDENT,
    ASSIGN,
    PRINT,
    NUMBER,
    CLOSED_SCOPE,
    OPEN_SCOPE;

    fun isNumberOrVar(): Boolean {
        return this == NUMBER || this == IDENT
    }
}

data class Token(
    val type: TokenType,
    val value: String? = null
) {
    override fun toString(): String {
        return "$type - ${value ?: ""}".trim().trimEnd('-').trim()
    }
}





