package com.example.parser

sealed class ASTNode{
    data class Assign(val name: String, val value: String) : ASTNode()
    data class Print(val name: String) : ASTNode()
    data class Scope(val body: List<ASTNode>) : ASTNode()
}