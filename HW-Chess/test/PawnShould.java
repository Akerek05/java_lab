import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
class PawnShould {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPawnType() {
		Pawn piece = new Pawn(true);
		assertEquals(piece.type(),Piece.PieceType.PAWN);
		assertEquals(piece.isWhite(),true);
	}	
	@Test
	void testPawnMoves() {
	    Pawn whitePawn = new Pawn(true);
	    List<Move> whiteMoves = whitePawn.possibleMovesFrom(1, 1);
	    assertEquals(4, whiteMoves.size());
	    assertTrue(whiteMoves.contains(new Move(3, 1, Move.MoveType.MOVE))); // Moving two squares forward
	    assertTrue(whiteMoves.contains(new Move(2, 1, Move.MoveType.MOVE))); // Moving one squares forward
	    assertTrue(whiteMoves.contains(new Move(2, 0, Move.MoveType.ATTACK))); // Attacking diagonally left
	    assertTrue(whiteMoves.contains(new Move(2, 2, Move.MoveType.ATTACK))); // Attacking diagonally right

	    Pawn blackPawn = new Pawn(false);
	    List<Move> blackMoves = blackPawn.possibleMovesFrom(6, 1);
	    assertEquals(4, blackMoves.size());
	    assertTrue(blackMoves.contains(new Move(4, 1, Move.MoveType.MOVE))); // Moving one square forward
	    assertTrue(blackMoves.contains(new Move(5, 1, Move.MoveType.MOVE))); // Moving one square forward
	    assertTrue(blackMoves.contains(new Move(5, 0, Move.MoveType.ATTACK))); // Attacking diagonally left
	    assertTrue(blackMoves.contains(new Move(5, 2, Move.MoveType.ATTACK))); // Attacking diagonally right
	}

	

	
}
