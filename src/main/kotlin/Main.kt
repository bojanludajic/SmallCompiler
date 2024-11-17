package com.example

import com.example.generator.CodeGenerator
import com.example.lexer.Lexer
import com.example.parser.Parser
import java.io.BufferedReader
import java.io.FileReader

const val path = "/Users/bojanludajic/IdeaProjects/SmallLanguageCompiler/src/main/resources/bojan.txt"

fun main() {
    val br = BufferedReader(FileReader(path))
    var text = br.readText()
    val lexer = Lexer(text)
    val parser = Parser(lexer.tokenize())
    val ast = parser.parse()
    val generator = CodeGenerator()
    println(generator.generate(ast))
    br.close()
}