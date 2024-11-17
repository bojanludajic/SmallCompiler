    package com.example.parser

    import com.example.lexer.Token
    import com.example.lexer.TokenType

    class Parser(
        private val tokens: List<Token>
    ) {
        private var curInd = 0

        val Parser.isAtEnd: Boolean
            get() = curInd >= tokens.size

        val Parser.curToken: Token
            get() {
                if(isAtEnd) {
                    throw IllegalArgumentException("No more tokens")
                } else {
                    return tokens[curInd]
                }
            }

        fun parse(): List<ASTNode> {
            val nodes = mutableListOf<ASTNode>()
            while(!isAtEnd) {
                nodes.add(parseStatement())
            }
            return nodes
        }

        private fun parseStatement(): ASTNode = when(curToken.type) {
                TokenType.IDENT -> parseAssign()
                TokenType.PRINT -> parsePrint()
                TokenType.OPEN_SCOPE -> parseScope()
                else -> throw IllegalArgumentException("Invalid token: $curToken")
        }

        private fun parseAssign(): ASTNode {
            val name = helper(TokenType.IDENT).value!!
            helper(TokenType.ASSIGN)

            val rhs: String = when(curToken.type) {
                TokenType.NUMBER -> helper(TokenType.NUMBER).value!!
                TokenType.IDENT -> helper(TokenType.IDENT).value!!
                else -> throw IllegalArgumentException("Invalid right hand side of the assignment: $curToken")
            }

            return ASTNode.Assign(name, rhs)
        }

        private fun parsePrint(): ASTNode {
            helper(TokenType.PRINT)
            val name = helper(TokenType.IDENT).value!!
            return ASTNode.Print(name)
        }

        private fun parseScope(): ASTNode {
            helper(TokenType.OPEN_SCOPE)
            val scopeBody = mutableListOf<ASTNode>()
            while(!isAtEnd && curToken.type != TokenType.CLOSED_SCOPE) {
                scopeBody.add(parseStatement())
            }
            helper(TokenType.CLOSED_SCOPE)
            return ASTNode.Scope(scopeBody)
        }

        private fun helper(expected: TokenType): Token {
            if (isAtEnd) {
                throw IllegalArgumentException("Unexpected end of input while expecting $expected")
            }
            if(curToken.type != expected) {
                throw IllegalArgumentException("Expected $expected but found ${curToken.type}")
            }
            val token = curToken
            curInd++
            return token
        }

    }
