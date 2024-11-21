package com.example

import com.example.generator.ARM64Generator
import com.example.generator.CGenerator
import com.example.generator.IRGenerator
import com.example.lexer.Lexer
import com.example.parser.Parser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val path = "/Users/bojanludajic/IdeaProjects/SmallLanguageCompiler/src/main/resources/code.txt"
const val scriptPath = "/Users/bojanludajic/IdeaProjects/SmallLanguageCompiler/src/main/resources/bin/buildandrun.sh"

fun main() {
    println("Enter absolute path of file to parse:")
    //val input = readLine()
    val br = BufferedReader(FileReader("/Users/bojanludajic/IdeaProjects/SmallLanguageCompiler/src/main/resources/code.txt"))
    val file = br.readText()

    if(file.isNotEmpty()) {
        val lexer = Lexer(file)
        val parser = Parser(lexer.tokenize())
        val AST = parser.parse()

        val IRGenerator = IRGenerator()
        val instructions = IRGenerator.generate(AST)
        val assemblyGenerator = ARM64Generator()
        val cgenerator = CGenerator()
        instructions.forEach {
            println(it)
        }

        saveCCode("/Users/bojanludajic/IdeaProjects/SmallLanguageCompiler/src/main/resources/program.c",
            cgenerator.generateCode(instructions))

        val exitCode = runShell(scriptPath)
        if(exitCode != 0) {
            return
        }
        println("Compilation successful. Process finished with exit code 0")
    }
}

private fun saveCode(file: String, instructions: List<String>) {
    val outFile = File(file)
    outFile.writeText("")
    outFile.appendText(".text\n")
    outFile.appendText(".global  _start\n")
    outFile.appendText(".align 4\n")
    outFile.appendText("\n_start:\n   ")
    outFile.appendText(" sub sp, sp, #0x100\n    ")
    outFile.appendText("mov x1, sp\n    ")
    outFile.appendText(instructions.joinToString("\n    "))
    outFile.appendText("\n    ")
    outFile.appendText("add sp, sp, #0x100\n    ")
    outFile.appendText("mov w0, #0\n    ")
    outFile.appendText("ret\n")
}

private fun saveCCode(file: String, instructions: List<String>) {
    val outFile = File(file)
    outFile.writeText("")
    outFile.appendText("#include <stdio.h>\n\n")
    outFile.appendText("int main() {\n    ")
    outFile.appendText(instructions.joinToString("\n    "))
    outFile.appendText("\n    ")
    outFile.appendText("return 0;\n")
    outFile.appendText("}")
}

fun runShell(path: String): Int {
    return ProcessBuilder("bash", path)
        .redirectErrorStream(true)
        .inheritIO()
        .start()
        .waitFor()
}