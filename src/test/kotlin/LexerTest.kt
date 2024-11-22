import com.example.lexer.Lexer
import com.example.lexer.Token
import com.example.lexer.TokenType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.FileReader

class LexerTest {
    @Test
    fun testTokenization() {
        val expectedTokens = listOf(Token(TokenType.IDENT, "x"), Token(TokenType.ASSIGN),
            Token(TokenType.NUMBER, "5"), Token(TokenType.IDENT, "y"), Token(TokenType.ASSIGN),
            Token(TokenType.NUMBER, "3"), Token(TokenType.OPEN_SCOPE), Token(TokenType.PRINT),
            Token(TokenType.IDENT, "x"), Token(TokenType.PRINT), Token(TokenType.IDENT, "y"),
            Token(TokenType.CLOSED_SCOPE), Token(TokenType.PRINT), Token(TokenType.IDENT, "x"))
        val file = "src/test/resources/tokentest.txt"
        val br = BufferedReader(FileReader(file))
        val lexer = Lexer(br.readText())
        assertEquals(expectedTokens, lexer.tokenize())
    }

    @Test
    fun testWhiteSpace() {
        val expectedTokens = listOf(Token(TokenType.IDENT, "x"), Token(TokenType.ASSIGN),
            Token(TokenType.NUMBER, "5"), Token(TokenType.IDENT, "y"),
            Token(TokenType.ASSIGN), Token(TokenType.NUMBER, "7"),
            Token(TokenType.OPEN_SCOPE), Token(TokenType.CLOSED_SCOPE))
        val file = "src/test/resources/whitespacetest.txt"
        val br = BufferedReader(FileReader(file))
        val lexer = Lexer(br.readText())
        assertEquals(expectedTokens, lexer.tokenize())
    }
}