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
	        assertTrue(pawnMoves.contains(new Move(1,0,2, 0, Move.MoveType.MOVE)), "Pawn can move to (2,0)");
	        assertTrue(pawnMoves.contains(new Move(1,0,3, 0, Move.MoveType.MOVE)), "Pawn can move to (3,0)");

	        // Scenario 2: Place a black piece in front of the pawn to block its movement
	        board.setupEmptyBoard();
	        board.getPieces()[2][0] = new Rook(false);  // Place a black rook at (2, 0)
	        pawnMoves = board.filterMoves(1, 0);
	        assertEquals(0, pawnMoves.size(), "Pawn's move should be blocked by black rook");

	        // Scenario 3: Test a white rook's moves with no obstruction
	        board.setupEmptyBoard();
	        board.getPieces()[0][0] = new Rook(true);  // White rook at (0, 0)
	        List<Move> rookMoves = board.filterMoves(0, 0);
	        assertTrue(rookMoves.contains(new Move(0,0,1, 0, Move.MoveType.MOVE)), "Rook can move to (1,0)");
	        assertTrue(rookMoves.contains(new Move(0,0,0, 1, Move.MoveType.MOVE)), "Rook can move to (0,1)");

	        // Scenario 4: Place an enemy piece in a rook's path to allow attack but stop further movement
	        board.setupEmptyBoard();
	        board.getPieces()[3][0] = new Pawn(false);  // Place a black pawn at (3, 0)
	        board.getPieces()[0][0] = new Rook(true);  // Place a black pawn at (3, 0)
	        rookMoves = board.filterMoves(0, 0);
	        assertTrue(rookMoves.contains(new Move(0,0,3, 0, Move.MoveType.ATTACK)), "Rook can attack at (3,0)");
	        assertFalse(rookMoves.contains(new Move(0,0,4, 0, Move.MoveType.MOVE)), "Rook should not move past attack target");

	        // Scenario 5: Test a black knight's moves that are within board bounds
	        board.setupEmptyBoard();
	        board.getPieces()[7][1] = new Knight(false);  // Black knight at (7, 1)
	        List<Move> knightMoves = board.filterMoves(7, 1);
	        assertEquals(3, knightMoves.size(), "Knight should have three possible moves from (7,1)");
	        assertTrue(knightMoves.contains(new Move(7,1,5, 0, Move.MoveType.MOVE)), "Knight can move to (5,0)");
	        assertTrue(knightMoves.contains(new Move(7,1,5, 2, Move.MoveType.MOVE)), "Knight can move to (5,2)");
	        assertTrue(knightMoves.contains(new Move(7,1,6, 3, Move.MoveType.MOVE)), "Knight can move to (6,3)");
	        
	        // Scenario 6: Test a white queen's moves with no obstruction
	        board.setupEmptyBoard();
	        board.getPieces()[3][3] = new Queen(true);  // Place a white queen at (3, 3)
	        List<Move> queenMoves = board.filterMoves(3, 3);
	        assertTrue(queenMoves.contains(new Move(3, 3,3, 0, Move.MoveType.MOVE)), "Queen can move to (3,0)");
	        assertTrue(queenMoves.contains(new Move(3, 3,3, 7, Move.MoveType.MOVE)), "Queen can move to (3,7)");
	        assertTrue(queenMoves.contains(new Move(3, 3,0, 3, Move.MoveType.MOVE)), "Queen can move to (0,3)");
	        assertTrue(queenMoves.contains(new Move(3, 3,7, 3, Move.MoveType.MOVE)), "Queen can move to (7,3)");
	        assertTrue(queenMoves.contains(new Move(3, 3,0, 0, Move.MoveType.MOVE)), "Queen can move to (0,0)");
	        assertTrue(queenMoves.contains(new Move(3, 3,7, 7, Move.MoveType.MOVE)), "Queen can move to (7,7)");

	        // Scenario 7: Place an enemy piece in the queen's path
	        board.setupEmptyBoard();
	        board.getPieces()[3][3] = new Queen(true);  // Place a white queen at (3, 3)
	        board.getPieces()[5][3] = new Rook(false);  // Place a black rook at (5, 3)
	        queenMoves = board.filterMoves(3, 3);
	        assertTrue(queenMoves.contains(new Move(3, 3,5, 3, Move.MoveType.ATTACK)), "Queen can attack at (5,3)");
	        assertFalse(queenMoves.contains(new Move(3, 3,6, 3, Move.MoveType.MOVE)), "Queen should not move past attack target");

	        // Scenario 8: Test a white bishop's moves with no obstruction
	        board.setupEmptyBoard();
	        board.getPieces()[2][2] = new Bishop(true);  // Place a white bishop at (2, 2)
	        List<Move> bishopMoves = board.filterMoves(2, 2);
	        assertTrue(bishopMoves.contains(new Move(2, 2,0, 0, Move.MoveType.MOVE)), "Bishop can move to (0,0)");
	        assertTrue(bishopMoves.contains(new Move(2, 2,5, 5, Move.MoveType.MOVE)), "Bishop can move to (5,5)");

	        // Scenario 9: Place an enemy piece in the bishop's path
	        board.setupEmptyBoard();
	        board.getPieces()[2][2] = new Bishop(true);  // Place a white bishop at (2, 2)
	        board.getPieces()[4][4] = new Pawn(false);   // Place a black pawn at (4, 4)
	        bishopMoves = board.filterMoves(2, 2);
	        assertTrue(bishopMoves.contains(new Move(2, 2,4, 4, Move.MoveType.ATTACK)), "Bishop can attack at (4,4)");
	        assertFalse(bishopMoves.contains(new Move(2, 2,5, 5, Move.MoveType.MOVE)), "Bishop should not move past attack target");

	        // Scenario 10: Test a king's moves with no obstruction
	        board.setupEmptyBoard();
	        board.getPieces()[4][4] = new King(true);  // Place a white king at (4, 4)
	        List<Move> kingMoves = board.filterMoves(4, 4);
	        assertTrue(kingMoves.contains(new Move(4, 4,3, 3, Move.MoveType.MOVE)), "King can move to (3,3)");
	        assertTrue(kingMoves.contains(new Move(4, 4,3, 4, Move.MoveType.MOVE)), "King can move to (3,4)");
	        assertTrue(kingMoves.contains(new Move(4, 4,3, 5, Move.MoveType.MOVE)), "King can move to (3,5)");
	        assertTrue(kingMoves.contains(new Move(4, 4,5, 3, Move.MoveType.MOVE)), "King can move to (5,3)");
	        assertTrue(kingMoves.contains(new Move(4, 4,5, 4, Move.MoveType.MOVE)), "King can move to (5,4)");
	        assertTrue(kingMoves.contains(new Move(4, 4,5, 5, Move.MoveType.MOVE)), "King can move to (5,5)");

	        // Scenario 11: Test a king's moves with an adjacent enemy piece
	        board.setupEmptyBoard();
	        board.getPieces()[4][4] = new King(true);  // Place a white king at (4, 4)
	        board.getPieces()[4][5] = new Pawn(false); // Place a black pawn at (4, 5)
	        kingMoves = board.filterMoves(4, 4);
	        assertTrue(kingMoves.contains(new Move(4, 4,4, 5, Move.MoveType.ATTACK)), "King can attack at (4,5)");
	    }
	
	@Test
	public void testMovePawnForward() {
	    Board board = new Board();
	    board.setupEmptyBoard();
	
	    // Place a white pawn at (1, 0)
	    board.getPieces()[1][0] = new Pawn(true);
	
	    // Test moving the pawn forward by one square
	    boolean moveSuccess = board.move(1, 0, new Move(1,0,2, 0, Move.MoveType.MOVE));
	    assertTrue(moveSuccess, "Pawn should be able to move forward to (2, 0)");
	    assertTrue(board.getPieces()[2][0] instanceof Pawn, "Pawn should be at (2, 0)");
	    assertTrue(board.getPieces()[1][0] instanceof Empty, "Original position (1, 0) should be empty");
	}

	@Test
	public void testPawnBlockedByPiece() {
	    Board board = new Board();
	    board.setupEmptyBoard();
	
	    // Place a white pawn at (1, 0) and a black rook blocking it at (2, 0)
	    board.getPieces()[1][0] = new Pawn(true);
	    board.getPieces()[2][0] = new Rook(false);
	
	    // Try to move the pawn forward to the blocked square
	    boolean moveSuccess = board.move(1, 0, new Move(1,0,2, 0, Move.MoveType.MOVE));
	    assertFalse(moveSuccess, "Pawn should not be able to move to (2, 0) as it is blocked by a piece");
	    assertTrue(board.getPieces()[1][0] instanceof Pawn, "Pawn should remain at (1, 0)");
	}

	@Test
	public void testRookAttack() {
	    Board board = new Board();
	    board.setupEmptyBoard();
	
	    // Place a white rook at (0, 0) and a black pawn at (0, 3)
	    board.getPieces()[0][0] = new Rook(true);
	    board.getPieces()[0][3] = new Pawn(false);
	
	    // Test rook attack move
	    boolean moveSuccess = board.move(0, 0, new Move(0,0,0, 3, Move.MoveType.ATTACK));
	    assertTrue(moveSuccess, "Rook should be able to attack the black pawn at (0, 3)");
	    assertTrue(board.getPieces()[0][3] instanceof Rook, "Rook should be at (0, 3) after the attack");
	    assertTrue(board.getPieces()[0][0] instanceof Empty, "Original position (0, 0) should be empty");
	}

	@Test
	public void testInvalidKnightMove() {
	    Board board = new Board();
	    board.setupEmptyBoard();
	
	    // Place a black knight at (7, 1)
	    board.getPieces()[7][1] = new Knight(false);
	
	    // Attempt an invalid move
	    boolean moveSuccess = board.move(7, 1, new Move(7,1,7, 3, Move.MoveType.MOVE));
	    assertFalse(moveSuccess, "Knight should not be able to move horizontally to (7, 3)");
	    assertTrue(board.getPieces()[7][1] instanceof Knight, "Knight should remain at (7, 1)");
	}

	@Test
	public void testKingMoveAndAttack() {
	    Board board = new Board();
	    board.setupEmptyBoard();
	
	    // Place a white king at (4, 4) and a black knight at (5, 5)
	    board.getPieces()[4][4] = new King(true);
	    board.getPieces()[5][5] = new Knight(false);
	
	    // Test moving the king to an adjacent empty square
	    boolean moveSuccess = board.move(4, 4, new Move(4,4,4, 5, Move.MoveType.MOVE));
	    assertTrue(moveSuccess, "King should be able to move to (4, 5)");
	    assertTrue(board.getPieces()[4][5] instanceof King, "King should be at (4, 5)");
	    assertTrue(board.getPieces()[4][4] instanceof Empty, "Original position (4, 4) should be empty");
	
	    // Test the king attacking the black knight
	    moveSuccess = board.move(4, 5, new Move(4,5,5, 5, Move.MoveType.ATTACK));
	    assertTrue(moveSuccess, "King should be able to attack the black knight at (5, 5)");
	    assertTrue(board.getPieces()[5][5] instanceof King, "King should be at (5, 5) after attack");
	    assertTrue(board.getPieces()[4][5] instanceof Empty, "Original position (4, 5) should be empty");
	}

	@Test
	public void testQueenLongMoveAndBlockedPath() {
	    Board board = new Board();
	    board.setupEmptyBoard();
	
	    // Place a white queen at (0, 0)
	    board.getPieces()[0][0] = new Queen(true);
	
	    // Test moving the queen diagonally
	    boolean moveSuccess = board.move(0, 0, new Move(0,0,4, 4, Move.MoveType.MOVE));
	    assertTrue(moveSuccess, "Queen should be able to move to (4, 4) diagonally");
	    assertTrue(board.getPieces()[4][4] instanceof Queen, "Queen should be at (4, 4)");
	    assertTrue(board.getPieces()[0][0] instanceof Empty, "Original position (0, 0) should be empty");
	
	    // Reset board, place a blocking piece
	    board.setupEmptyBoard();
	    board.getPieces()[0][0] = new Queen(true);
	    board.getPieces()[2][2] = new Pawn(false);
	
	    // Test move blocked by pawn
	    moveSuccess = board.move(0, 0, new Move(0,0,4, 4, Move.MoveType.MOVE));
	    assertFalse(moveSuccess, "Queen should not be able to move to (4, 4) as path is blocked by a pawn");
	    assertTrue(board.getPieces()[0][0] instanceof Queen, "Queen should remain at (0, 0)");
	}
	}

