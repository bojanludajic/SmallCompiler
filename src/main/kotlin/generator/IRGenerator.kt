package com.example.generator

import com.example.ir.IntermediateRepresentation
import com.example.parser.ASTNode

class IRGenerator {

    private val instructions = mutableListOf<IntermediateRepresentation>()

    fun generate(astNodes: List<ASTNode>): List<IntermediateRepresentation> {
        for(node in astNodes) {
            process(node)
        }
        return instructions
    }

    private fun process(node: ASTNode) {
        when(node) {
            is ASTNode.Assign -> { instructions.add(IntermediateRepresentation.Assign(node.name, node.value)) }
            is ASTNode.Print -> { instructions.add(IntermediateRepresentation.Print(node.name)) }
            is ASTNode.Scope -> {
                instructions.add(IntermediateRepresentation.OpenScope())
                for(child in node.body) {
                    process(child)
                }
                instructions.add(IntermediateRepresentation.ClosedScope())
            }
        }
    }

}