package com.example

import com.example.generator.ARM64Generator
import com.example.generator.CGenerator
import com.example.generator.IRGenerator
import com.example.lexer.Lexer
import com.example.parser.Parser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

const val scriptPath = "buildandrunasm.sh"

fun main() {
    println("Enter the file containing your code (press enter for default):")
    val input = readLine()
    var source: String
    if(input!!.isNotBlank()) {
        source = input
    } else {
        source = "src/main/resources/code.txt"
    }

    val br = BufferedReader(FileReader(source))
    val fileText = br.readText()

    if(fileText.isNotBlank()) {
        execute(fileText)

        println("Compilation successful. Process finished with exit code 0")
    } else {
        println("Provided file has no code")
    }
}

private fun saveCodeASM(file: String, instructions: List<String>) {
    val outFile = File(file)
    outFile.writeText("")
    outFile.appendText(".global _main\n")
    outFile.appendText(".text\n")
    outFile.appendText(".align 8\n")
    outFile.appendText("\n_main:\n   ")
    outFile.appendText(" mov X0, #1\n    ")
    outFile.appendText("adrp X1, content@page\n    ")
    outFile.appendText("add X1, X1, content@pageoff\n    ")
    outFile.appendText("mov X2, content_len\n    ")
    outFile.appendText("mov X16, #4\n    ")
    outFile.appendText("svc     #0x80\n    ")
    outFile.appendText("\n    ")
    outFile.appendText("mov     X0, #0\n    ")
    outFile.appendText("mov     X16, #1\n    ")
    outFile.appendText("svc     #0x80\n    ")
    outFile.appendText("\n")
    outFile.appendText("    .data\n")
    outFile.appendText("content:\n    ")
    outFile.appendText(instructions.joinToString("\n    "))
    outFile.appendText("\n")
    outFile.appendText("content_len = . - content")
}

private fun saveCodeC(file: String, instructions: List<String>) {
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
        .directory(File("src/main/resources/bin"))
        .start()
        .waitFor()
}

fun execute(fileText: String) {
    val lexer = Lexer(fileText)
    val parser = Parser(lexer.tokenize())
    val AST = parser.parse()

    val IRGenerator = IRGenerator()
    val instructions = IRGenerator.generate(AST)
    val assemblyGenerator = ARM64Generator()

    saveCodeASM("src/main/resources/program.asm",
        assemblyGenerator.generateCode(instructions))

    val exitCode = runShell(scriptPath)

    if(exitCode != 0) {
        return
    }
}

