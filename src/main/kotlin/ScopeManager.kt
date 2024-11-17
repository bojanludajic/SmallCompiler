package com.example

import java.util.ArrayDeque
import java.util.EmptyStackException

class ScopeManager {

    private val stack: ArrayDeque<MutableMap<String, String>> = ArrayDeque()

    fun enterScope() {
        stack.push(mutableMapOf())
    }

    fun exitScope() {
        if(stack.isNotEmpty()) {
            stack.pop()
        } else {
            throw EmptyStackException()
        }
    }

    fun declareVar(name: String, value: String) {
        val scope = stack.peek()
        scope[name] = value
    }

    fun getVariable(name: String): String {
        return stack.peek()[name] ?: "null"
    }

}