package com.example.ir

sealed class IntermediateCode {
    data class Assign(val variable: String, val value: String) : IntermediateCode()
    data class Print(val variable: String) : IntermediateCode()
    class OpenScope : IntermediateCode() {
        override fun toString(): String {
            return "OpenScope()"
        }
    }
    class ClosedScope : IntermediateCode() {
        override fun toString(): String {
            return "ClosedScope()"
        }
    }
}