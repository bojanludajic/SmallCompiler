package com.example.parser

sealed class ASTNode{
    data class Assign(val name: String, val value: String) : ASTNode() {
        override fun toString(): String {
            return "Assign: $name = $value"
        }
    }
    data class Print(val name: String) : ASTNode() {
        override fun toString(): String {
            return "Print: $name"
        }
    }
    data class Scope(val body: List<ASTNode>) : ASTNode() {
        override fun toString(): String {
            return buildString {
                appendLine("Scope {")
                body.forEach {
                    appendLine(indentations(it.toString()))
                }
                append("}")
            }
        }

        private fun indentations(name: String): String {
            val indentation = "    ".repeat(1)
            return name.lineSequence()
                .joinToString("\n") { "$indentation$it" }
        }
    }
}