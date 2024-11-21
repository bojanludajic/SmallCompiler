package com.example.generator

import java.util.ArrayDeque
import java.util.EmptyStackException

class ScopeManager {

    private val stack: ArrayDeque<MutableMap<String, String>> = ArrayDeque()

    init {
        openScope()
    }

    fun openScope() {
        stack.push(mutableMapOf())
    }

    fun closeScope() {
        if(stack.isNotEmpty()) {
            stack.pop()
        } else {
            throw EmptyStackException()
        }
    }

    fun assignVar(name: String, value: String) {
        val scope = stack.peek()
        if(value.toIntOrNull() != null) {
            scope[name] = value
        } else {
            scope[name] = scope[value] ?: "null"
        }

    }

    fun getVariable(name: String): String {
        for(scope in stack) {
            if(scope.containsKey(name)) {
                return scope[name]!!
            }
        }
        return "null"
    }

}