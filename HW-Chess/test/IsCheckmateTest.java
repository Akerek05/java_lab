import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IsCheckmateTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setupEmptyBoard(); // Clear the board for specific test setups
    }

    @Test
    void testCheckmateScenario() {
        // White king is checkmated by black rook and queen
        board.getPieces()[0][0] = new King(true);    // White king
        board.getPieces()[1][0] = new Rook(false);  // Black rook
        board.getPieces()[2][1] = new Queen(false); // Black queen

        assertTrue(board.isCheckmate(true), "White king should be in checkmate");
    }

    @Test
    void testNotCheckmateScenario() {
        // White king is in check but can escape
        board.getPieces()[0][0] = new King(true);    // White king
        board.getPieces()[1][0] = new Rook(false);  // Black rook

        assertFalse(board.isCheckmate(true), "White king should not be in checkmate because it can move");
    }

    @Test
    void testStalemateScenario() {
        // Stalemate: White king has no valid moves but is not in check
        board.getPieces()[0][0] = new King(true);    // White king
        board.getPieces()[1][1] = new King(false);  // Black king

        assertFalse(board.isCheckmate(true), "White king is not in checkmate; it is a stalemate");
    }

    @Test
    void testOpenPlayScenario() {
        // White king is not in check and has valid moves
        board.getPieces()[4][4] = new King(true);    // White king
        board.getPieces()[0][4] = new Queen(false); // Black queen (not attacking the king)

        assertFalse(board.isCheckmate(true), "White king is not in checkmate; it has valid moves");
    }

    @Test
    void testCheckmateWithBlockingPiece() {
        // White king is in check but can be saved by another white piece
        board.getPieces()[0][0] = new King(true);    // White king
        board.getPieces()[1][0] = new Rook(false);  // Black rook
        board.getPieces()[2][2] = new Bishop(true); // White bishop can block the check

        assertFalse(board.isCheckmate(true), "White king should not be in checkmate because it can be saved");
    }
}
