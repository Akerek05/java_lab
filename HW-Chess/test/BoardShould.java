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
		
		for(int i=0;i<8;i++) {
			assertEquals(pieces[1][i].type(),Piece.PieceType.PAWN);
			assertEquals(pieces[6][i].type(),Piece.PieceType.PAWN);
			assertTrue(pieces[1][i].isWhite());
			assertFalse(pieces[6][i].isWhite());
		}
	}
	@Test 
	void testGetPlayablePieces() {
	    Board board = new Board(); // Assuming you have a Board class
	    board.setupBoard(); // Call the setup method to initialize the board

	    // Get playable pieces for white
	    Piece[][] playableWhitePieces = board.getPlayablePieces(true);

	    // Assertions for white pieces
	    assertNotNull(playableWhitePieces);
	    assertEquals(8, countPlayablePieces(playableWhitePieces, true)); // Expecting 8 white pawns
	    for (int i = 0; i < 8; i++) {
	        assertNotNull(playableWhitePieces[1][i]); // Ensure all white pawns are included
	    }

	    // Get playable pieces for black
	    Piece[][] playableBlackPieces = board.getPlayablePieces(false);

	    // Assertions for black pieces
	    assertNotNull(playableBlackPieces);
	    assertEquals(8, countPlayablePieces(playableBlackPieces, false)); // Expecting 8 black pawns
	    for (int i = 0; i < 8; i++) {
	        assertNotNull(playableBlackPieces[6][i]); // Ensure all black pawns are included
	    }
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
