package com.example.generator

import com.example.ir.IntermediateCode
import com.example.parser.ASTNode

class CodeGenerator {

    private val instructions = mutableListOf<IntermediateCode>()

    fun generate(astNodes: List<ASTNode>): List<IntermediateCode> {
        for(node in astNodes) {
            process(node)
        }
        return instructions
    }

    private fun process(node: ASTNode) {
        when(node) {
            is ASTNode.Assign -> { instructions.add(IntermediateCode.Assign(node.name, node.value)) }
            is ASTNode.Print -> { instructions.add(IntermediateCode.Print(node.name)) }
            is ASTNode.Scope -> {
                instructions.add(IntermediateCode.OpenScope())
                for(child in node.body) {
                    process(child)
                }
                instructions.add(IntermediateCode.ClosedScope())
            }
        }
    }

}