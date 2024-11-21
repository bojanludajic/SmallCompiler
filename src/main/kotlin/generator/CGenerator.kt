package com.example.generator

import com.example.ir.IntermediateRepresentation

class CGenerator {

    private val instructions = mutableListOf<String>()
    private val scopeStack = ScopeManager()

    fun generateCode(ir: List<IntermediateRepresentation>): List<String> {
        for(c in ir) {
            processInstruction(c)
        }
        return instructions
    }

    private fun processInstruction(c: IntermediateRepresentation) {
        when(c) {
            is IntermediateRepresentation.Assign -> { handleAssign(c) }
            is IntermediateRepresentation.Print -> { handlePrint(c) }
            is IntermediateRepresentation.OpenScope -> { handleOpenScope() }
            is IntermediateRepresentation.ClosedScope -> { handleClosedScope() }
        }
    }

    private fun handleAssign(c: IntermediateRepresentation.Assign) {
        scopeStack.assignVar(c.variable, c.value)
    }

    private fun handlePrint(c: IntermediateRepresentation.Print) {
        instructions.add("printf(\"${scopeStack.getVariable(c.variable)}\\n\");")
    }

    private fun handleOpenScope() {
        scopeStack.openScope()
    }

    private fun handleClosedScope() {
        scopeStack.closeScope()
    }


}