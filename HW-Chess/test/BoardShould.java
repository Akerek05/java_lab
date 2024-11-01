import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
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
    void testGetPlayablePieces() {
        Board board = new Board();
        board.setupBoard();

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
    // Helper method to assert piece type and colour at a specific position
    private void assertPiece(Piece[][] pieces, int x, int y, Piece.PieceType expectedType, boolean expectedIsWhite) {
        assertEquals(expectedType, pieces[x][y].type(), "Unexpected piece type at position (" + x + ", " + y + ")");
        assertEquals(expectedIsWhite, pieces[x][y].isWhite(), "Unexpected piece color at position (" + x + ", " + y + ")");
    }
    
 // Helper method to count playable pieces of a specific colour
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
	
	 @Test
	    public void testFilterMoves() {
		 	Board board = new Board();
		 	board.setupEmptyBoard();
		 	// Scenario 1: Test a white pawn move from initial position
	        board.getPieces()[1][0] = new Pawn(true);  // Place a white pawn at (1, 0)
	        List<Move> pawnMoves = board.filterMoves(1, 0); 
	        assertEquals(2, pawnMoves.size(), "Pawn should have two initial moves from (1,0)");
	        assertTrue(pawnMoves.contains(new Move(2, 0, Move.MoveType.MOVE)), "Pawn can move to (2,0)");
	        assertTrue(pawnMoves.contains(new Move(3, 0, Move.MoveType.MOVE)), "Pawn can move to (3,0)");

	        // Scenario 2: Place a black piece in front of the pawn to block its movement
	        board.setupEmptyBoard();
	        board.getPieces()[2][0] = new Rook(false);  // Place a black rook at (2, 0)
	        pawnMoves = board.filterMoves(1, 0);
	        assertEquals(0, pawnMoves.size(), "Pawn's move should be blocked by black rook");

	        // Scenario 3: Test a white rook's moves with no obstruction
	        board.setupEmptyBoard();
	        board.getPieces()[0][0] = new Rook(true);  // White rook at (0, 0)
	        List<Move> rookMoves = board.filterMoves(0, 0);
	        assertTrue(rookMoves.contains(new Move(1, 0, Move.MoveType.MOVE)), "Rook can move to (1,0)");
	        assertTrue(rookMoves.contains(new Move(0, 1, Move.MoveType.MOVE)), "Rook can move to (0,1)");

	        // Scenario 4: Place an enemy piece in a rook's path to allow attack but stop further movement
	        board.setupEmptyBoard();
	        board.getPieces()[3][0] = new Pawn(false);  // Place a black pawn at (3, 0)
	        board.getPieces()[1][0] = new Rook(true);  // Place a black pawn at (3, 0)
	        rookMoves = board.filterMoves(0, 0);
	        assertTrue(rookMoves.contains(new Move(3, 0, Move.MoveType.ATTACK)), "Rook can attack at (3,0)");
	        assertFalse(rookMoves.contains(new Move(4, 0, Move.MoveType.MOVE)), "Rook should not move past attack target");

	        // Scenario 5: Test a black knight's moves that are within board bounds
	        board.setupEmptyBoard();
	        board.getPieces()[7][1] = new Knight(false);  // Black knight at (7, 1)
	        List<Move> knightMoves = board.filterMoves(7, 1);
	        assertEquals(2, knightMoves.size(), "Knight should have two possible moves from (7,1)");
	        assertTrue(knightMoves.contains(new Move(5, 0, Move.MoveType.MOVE)), "Knight can move to (5,0)");
	        assertTrue(knightMoves.contains(new Move(5, 2, Move.MoveType.MOVE)), "Knight can move to (5,2)");
	    }
	}

