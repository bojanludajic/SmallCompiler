package com.example.ir

sealed class IntermediateRepresentation {
    data class Assign(val variable: String, val value: String) : IntermediateRepresentation() {
        override fun toString(): String {
            return ("Assign $variable = $value")
        }
    }
    data class Print(val variable: String) : IntermediateRepresentation() {
        override fun toString(): String {
            return ("Print $variable")
        }
    }
    class OpenScope : IntermediateRepresentation() {
        override fun toString(): String {
            return "openScope()"
        }
    }
    class ClosedScope : IntermediateRepresentation() {
        override fun toString(): String {
            return "closeScope()"
        }
    }
}