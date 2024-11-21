package com.example.generator

import com.example.ir.IntermediateRepresentation

class ARM64Generator {

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
            is IntermediateRepresentation.Assign -> handleAssign(c)
            is IntermediateRepresentation.Print -> handlePrint(c)
            is IntermediateRepresentation.OpenScope -> handleOpenScope(c)
            is IntermediateRepresentation.ClosedScope -> handleClosedScope(c)
        }
    }

    private fun handleAssign(c: IntermediateRepresentation.Assign) {
        scopeStack.assignVar(c.variable, c.value)
    }

    private fun handlePrint(c: IntermediateRepresentation.Print) {
        instructions.add(".ascii \"${scopeStack.getVariable(c.variable)}\\n\"")
    }

    private fun handleOpenScope(c: IntermediateRepresentation.OpenScope) {
        scopeStack.openScope()
    }

    private fun handleClosedScope(c: IntermediateRepresentation.ClosedScope) {
        scopeStack.closeScope()
    }

}