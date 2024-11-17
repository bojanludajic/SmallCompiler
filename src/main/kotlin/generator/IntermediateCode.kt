package com.example.ir

sealed class IntermediateCode {
    data class Assign(val variable: String, val value: String) : IntermediateCode()
    data class Print(val variable: String) : IntermediateCode()
    class OpenScope : IntermediateCode()
    class ClosedScope : IntermediateCode()
}