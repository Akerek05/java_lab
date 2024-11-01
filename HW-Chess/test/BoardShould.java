import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardShould {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    void testBoardSetup() {
        Board board = new Board();
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
    void testGetPlayablePieces() {
        Board board = new Board();
        board.setupBoard(); // Initialize the board with pieces

        // Get playable pieces for white
        Piece[][] playableWhitePieces = board.getPlayablePieces(true);

        // Assertions for white pieces
        assertNotNull(playableWhitePieces);
        assertEquals(16, countPlayablePieces(playableWhitePieces, true)); // Expecting 16 white pieces

        // Verify white pawns
        for (int i = 0; i < 8; i++) {
            assertEquals(Piece.PieceType.PAWN, playableWhitePieces[1][i].type());
            assertTrue(playableWhitePieces[1][i].isWhite());
        }

        // Verify other white pieces in their starting positions
        assertPiece(playableWhitePieces, 0, 0, Piece.PieceType.ROOK, true);
        assertPiece(playableWhitePieces, 0, 1, Piece.PieceType.KNIGHT, true);
        assertPiece(playableWhitePieces, 0, 2, Piece.PieceType.BISHOP, true);
        assertPiece(playableWhitePieces, 0, 3, Piece.PieceType.QUEEN, true);
        assertPiece(playableWhitePieces, 0, 4, Piece.PieceType.KING, true);
        assertPiece(playableWhitePieces, 0, 5, Piece.PieceType.BISHOP, true);
        assertPiece(playableWhitePieces, 0, 6, Piece.PieceType.KNIGHT, true);
        assertPiece(playableWhitePieces, 0, 7, Piece.PieceType.ROOK, true);

        // Get playable pieces for black
        Piece[][] playableBlackPieces = board.getPlayablePieces(false);

        // Assertions for black pieces
        assertNotNull(playableBlackPieces);
        assertEquals(16, countPlayablePieces(playableBlackPieces, false)); // Expecting 16 black pieces

        // Verify black pawns
        for (int i = 0; i < 8; i++) {
            assertEquals(Piece.PieceType.PAWN, playableBlackPieces[6][i].type());
            assertFalse(playableBlackPieces[6][i].isWhite());
        }

        // Verify other black pieces in their starting positions
        assertPiece(playableBlackPieces, 7, 0, Piece.PieceType.ROOK, false);
        assertPiece(playableBlackPieces, 7, 1, Piece.PieceType.KNIGHT, false);
        assertPiece(playableBlackPieces, 7, 2, Piece.PieceType.BISHOP, false);
        assertPiece(playableBlackPieces, 7, 3, Piece.PieceType.QUEEN, false);
        assertPiece(playableBlackPieces, 7, 4, Piece.PieceType.KING, false);
        assertPiece(playableBlackPieces, 7, 5, Piece.PieceType.BISHOP, false);
        assertPiece(playableBlackPieces, 7, 6, Piece.PieceType.KNIGHT, false);
        assertPiece(playableBlackPieces, 7, 7, Piece.PieceType.ROOK, false);
    }
    // Helper method to assert piece type and color at a specific position
    private void assertPiece(Piece[][] pieces, int x, int y, Piece.PieceType expectedType, boolean expectedIsWhite) {
        assertEquals(expectedType, pieces[x][y].type(), "Unexpected piece type at position (" + x + ", " + y + ")");
        assertEquals(expectedIsWhite, pieces[x][y].isWhite(), "Unexpected piece color at position (" + x + ", " + y + ")");
    }
    
    
	private int countPlayablePieces(Piece[][] pieces, boolean isWhite) {
	    int count = 0;
	    for (int i = 0; i < pieces.length; i++) {
	        for (int j = 0; j < pieces[i].length; j++) {
	            if (pieces[i][j] != null && pieces[i][j].isWhite() == isWhite) {
	                count++;
	            }
	        }
	    }
	    return count;
	}
	

}
