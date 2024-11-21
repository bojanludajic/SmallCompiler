import com.example.lexer.Lexer
import com.example.parser.Parser
import com.example.parser.ASTNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

class ParserTest {

    @Test
    fun testParser() {
        val expectedTree = listOf(ASTNode.Assign("x", "5"),
            ASTNode.Assign("y", "3"), ASTNode.Scope(listOf(ASTNode.Print("x"),
                ASTNode.Print("y"))), ASTNode.Print("x"))
        val file = "src/test/resources/tokentest.txt"
        val br = BufferedReader(FileReader(file))
        val lexer = Lexer(br.readText())
        val parser = Parser(lexer.tokenize())
        assertEquals(expectedTree, parser.parse())
    }

}