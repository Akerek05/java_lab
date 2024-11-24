import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class QueenShould {
	private Board board;
    @BeforeEach
    void setUp() throws Exception {
    	board = new Board();
        board.setupEmptyBoard();
    }

    
    @Test
    void testQueenValidMovesOnEmptyBoard() {
        

        // Place a white queen at (3, 3)
        board.getPieces()[3][3] = new Queen(true);

        // Get all valid moves for the queen
        List<Move> queenMoves = board.filterMoves(3, 3);

        // Test valid horizontal, vertical, and diagonal moves
        assertTrue(queenMoves.contains(new Move(3, 3, 3, 0, Move.MoveType.MOVE)), "Queen can move horizontally to (3, 0)");
        assertTrue(queenMoves.contains(new Move(3, 3, 3, 7, Move.MoveType.MOVE)), "Queen can move horizontally to (3, 7)");
        assertTrue(queenMoves.contains(new Move(3, 3, 0, 3, Move.MoveType.MOVE)), "Queen can move vertically to (0, 3)");
        assertTrue(queenMoves.contains(new Move(3, 3, 7, 3, Move.MoveType.MOVE)), "Queen can move vertically to (7, 3)");
        assertTrue(queenMoves.contains(new Move(3, 3, 0, 0, Move.MoveType.MOVE)), "Queen can move diagonally to (0, 0)");
        assertTrue(queenMoves.contains(new Move(3, 3, 7, 7, Move.MoveType.MOVE)), "Queen can move diagonally to (7, 7)");
    }

    @Test
    void testQueenBlockedByOwnPiece() {
       
        // Place a white queen at (3, 3) and a white pawn blocking at (3, 5)
        board.getPieces()[3][3] = new Queen(true);
        board.getPieces()[3][5] = new Pawn(true);

        // Get queen's moves
        List<Move> queenMoves = board.filterMoves(3, 3);

        // Ensure the queen cannot move past or attack its own piece
        assertFalse(queenMoves.contains(new Move(3, 3, 3, 5, Move.MoveType.ATTACK)), "Queen cannot attack its own pawn at (3, 5)");
        assertFalse(queenMoves.contains(new Move(3, 3, 3, 6, Move.MoveType.MOVE)), "Queen cannot move past its own pawn at (3, 5)");
    }

    @Test
    void testQueenAttackEnemyPiece() {
    	
        // Place a white queen at (3, 3) and a black pawn at (5, 5)
        board.getPieces()[3][3] = new Queen(true);
        board.getPieces()[5][5] = new Pawn(false);

        // Get queen's moves
        List<Move> queenMoves = board.filterMoves(3, 3);

        // Test that queen can attack the black pawn
        assertTrue(queenMoves.contains(new Move(3, 3, 5, 5, Move.MoveType.ATTACK)), "Queen can attack enemy pawn at (5, 5)");
    }

    @Test
    void testQueenBlockedByEnemyPiece() {
 
        // Place a white queen at (3, 3) and a black pawn at (3, 5)
        board.getPieces()[3][3] = new Queen(true);
        board.getPieces()[3][5] = new Pawn(false);

        // Get queen's moves
        List<Move> queenMoves = board.filterMoves(3, 3);

        // Test queen can attack the black pawn but cannot move past it
        assertTrue(queenMoves.contains(new Move(3, 3, 3, 5, Move.MoveType.ATTACK)), "Queen can attack enemy pawn at (3, 5)");
        assertFalse(queenMoves.contains(new Move(3, 3, 3, 6, Move.MoveType.MOVE)), "Queen cannot move past enemy pawn at (3, 5)");
    }

    @Test
    void testQueenMoveAndAttackCombination() {

        // Place a white queen at (4, 4), a black knight at (4, 6), and a black rook at (4, 7)
        board.getPieces()[4][4] = new Queen(true);
        board.getPieces()[4][6] = new Knight(false);
        board.getPieces()[4][7] = new Rook(false);

        // Get queen's moves
        List<Move> queenMoves = board.filterMoves(4, 4);

        // Test queen can attack the black knight
        assertTrue(queenMoves.contains(new Move(4, 4, 4, 6, Move.MoveType.ATTACK)), "Queen can attack the black knight at (4, 6)");

        // Test queen cannot move past the black rook
        assertFalse(queenMoves.contains(new Move(4, 4, 4, 8, Move.MoveType.MOVE)), "Queen cannot move past the black rook at (4, 7)");
    }
}
