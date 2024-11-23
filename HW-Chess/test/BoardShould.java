import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardShould {
	
	private Board board;
	
	@BeforeEach
    void setUp() {
        board = new Board();
        board.setupEmptyBoard();
    }

	@Test
    void testBoardSetup() {
        board.setupBoard();
        Piece[][] pieces = board.getPieces();

        // Check pawns
        for (int i = 0; i < 8; i++) {
            assertEquals(Piece.PieceType.PAWN, pieces[1][i].type());
            assertEquals(Piece.PieceType.PAWN, pieces[6][i].type());
            assertTrue(pieces[1][i].isWhite());
            assertFalse(pieces[6][i].isWhite());
        }

        // Check rooks
        assertEquals(Piece.PieceType.ROOK, pieces[0][0].type());
        assertEquals(Piece.PieceType.ROOK, pieces[0][7].type());
        assertTrue(pieces[0][0].isWhite());
        assertTrue(pieces[0][7].isWhite());
        
        assertEquals(Piece.PieceType.ROOK, pieces[7][0].type());
        assertEquals(Piece.PieceType.ROOK, pieces[7][7].type());
        assertFalse(pieces[7][0].isWhite());
        assertFalse(pieces[7][7].isWhite());

        // Check knights
        assertEquals(Piece.PieceType.KNIGHT, pieces[0][1].type());
        assertEquals(Piece.PieceType.KNIGHT, pieces[0][6].type());
        assertTrue(pieces[0][1].isWhite());
        assertTrue(pieces[0][6].isWhite());
        
        assertEquals(Piece.PieceType.KNIGHT, pieces[7][1].type());
        assertEquals(Piece.PieceType.KNIGHT, pieces[7][6].type());
        assertFalse(pieces[7][1].isWhite());
        assertFalse(pieces[7][6].isWhite());

        // Check bishops
        assertEquals(Piece.PieceType.BISHOP, pieces[0][2].type());
        assertEquals(Piece.PieceType.BISHOP, pieces[0][5].type());
        assertTrue(pieces[0][2].isWhite());
        assertTrue(pieces[0][5].isWhite());
        
        assertEquals(Piece.PieceType.BISHOP, pieces[7][2].type());
        assertEquals(Piece.PieceType.BISHOP, pieces[7][5].type());
        assertFalse(pieces[7][2].isWhite());
        assertFalse(pieces[7][5].isWhite());

        // Check queens
        assertEquals(Piece.PieceType.QUEEN, pieces[0][3].type());
        assertTrue(pieces[0][3].isWhite());
        
        assertEquals(Piece.PieceType.QUEEN, pieces[7][3].type());
        assertFalse(pieces[7][3].isWhite());

        // Check kings
        assertEquals(Piece.PieceType.KING, pieces[0][4].type());
        assertTrue(pieces[0][4].isWhite());
        
        assertEquals(Piece.PieceType.KING, pieces[7][4].type());
        assertFalse(pieces[7][4].isWhite());

        // Check empty squares in the middle of the board
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                assertEquals(Piece.PieceType.EMPTY, pieces[i][j].type());
            }
        }
    }
	
    
    @Test
    void testSaveBoardState() {
        // Place a white king at (4, 4)
        board.getPieces()[4][4] = new King(true);

        // Save the initial board state
        board.saveBoardState();

        // Modify the board
        board.getPieces()[4][4] = new Empty();
        board.getPieces()[3][3] = new Queen(false);

        // Save the modified board state
        board.saveBoardState();

        // Check previous board states
        Piece[][] previousState1 = board.getPreviousBoardState1();
        Piece[][] previousState2 = board.getPreviousBoardState2();

        // Assert the states are saved correctly
        assertNotNull(previousState1[3][3], "Previous state 1 should have a black queen at (3, 3)");
        assertEquals(Piece.PieceType.QUEEN, previousState1[3][3].type(), "Previous state 1 should have a queen at (3, 3)");
        assertEquals(previousState1[4][4].type(), board.getPieces()[4][4].type(),"Previous state 1 should not have a piece at (4, 4)");

        assertNotNull(previousState2[4][4], "Previous state 2 should have the white king at (4, 4)");
        assertEquals(Piece.PieceType.KING, previousState2[4][4].type(), "Previous state 2 should have a king at (4, 4)");
    }

    @Test
    void testRestorePreviousState() {
        // Place initial pieces
        board.getPieces()[4][4] = new King(true);
        board.saveBoardState();

        // Modify the board
        board.getPieces()[4][4] = new Empty();
        board.getPieces()[3][3] = new Queen(false);
        board.saveBoardState();

        // Modify the board again
        board.getPieces()[2][2] = new Rook(true);
        board.saveBoardState();

        // Restore to the previous state
        board.restorePreviousState();
        board.restorePreviousState();
        Piece[][] restoredState = board.getPieces();

        // Assert the board matches the last saved state
        assertNotNull(restoredState[3][3], "Restored state should have a black queen at (3, 3)");
        assertEquals(Piece.PieceType.QUEEN, restoredState[3][3].type(), "Restored state should have a queen at (3, 3)");
        assertEquals(restoredState[2][2].type(),Piece.PieceType.EMPTY, "Restored state should not have a rook at (2, 2)");

        // Restore again
        board.restorePreviousState();
        restoredState = board.getPieces();

        assertNotNull(restoredState[4][4], "Restored state should have a white king at (4, 4)");
        assertEquals(Piece.PieceType.KING, restoredState[4][4].type(), "Restored state should have a king at (4, 4)");
        assertEquals(restoredState[3][3].type(),Piece.PieceType.EMPTY, "Restored state should not have a queen at (3, 3)");
    }
}

