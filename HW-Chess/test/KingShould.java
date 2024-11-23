import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KingShould {

    private Board board;

    @BeforeEach
    void setUp() throws Exception {
        board = new Board();
        board.setupEmptyBoard();
    }

    @Test
    void testKingValidMovesOnEmptyBoard() {
        // Place a white king at (3, 3)
        board.getPieces()[3][3] = new King(true);

        // Get all valid moves for the king
        List<Move> kingMoves = board.filterMoves(3, 3);

        // Test all 8 possible king moves (one square in any direction)
        assertTrue(kingMoves.contains(new Move(3, 3, 2, 2, Move.MoveType.MOVE)), "King can move to (2, 2)");
        assertTrue(kingMoves.contains(new Move(3, 3, 2, 3, Move.MoveType.MOVE)), "King can move to (2, 3)");
        assertTrue(kingMoves.contains(new Move(3, 3, 2, 4, Move.MoveType.MOVE)), "King can move to (2, 4)");
        assertTrue(kingMoves.contains(new Move(3, 3, 3, 2, Move.MoveType.MOVE)), "King can move to (3, 2)");
        assertTrue(kingMoves.contains(new Move(3, 3, 3, 4, Move.MoveType.MOVE)), "King can move to (3, 4)");
        assertTrue(kingMoves.contains(new Move(3, 3, 4, 2, Move.MoveType.MOVE)), "King can move to (4, 2)");
        assertTrue(kingMoves.contains(new Move(3, 3, 4, 3, Move.MoveType.MOVE)), "King can move to (4, 3)");
        assertTrue(kingMoves.contains(new Move(3, 3, 4, 4, Move.MoveType.MOVE)), "King can move to (4, 4)");
    }

    @Test
    void testKingBlockedByOwnPiece() {
        // Place a white king at (3, 3) and a white pawn blocking at (4, 4)
        board.getPieces()[3][3] = new King(true);
        board.getPieces()[4][4] = new Pawn(true);

        // Get king's moves
        List<Move> kingMoves = board.filterMoves(3, 3);

        // Ensure the king cannot move to (4, 4), where its own piece is blocking
        assertFalse(kingMoves.contains(new Move(3, 3, 4, 4, Move.MoveType.MOVE)), "King cannot move to (4, 4), blocked by its own piece");
    }

    @Test
    void testKingAttackEnemyPiece() {
        // Place a white king at (3, 3) and a black pawn at (4, 4)
        board.getPieces()[3][3] = new King(true);
        board.getPieces()[4][4] = new Pawn(false);

        // Get king's moves
        List<Move> kingMoves = board.filterMoves(3, 3);

        // Test that the king can attack the black pawn
        assertTrue(kingMoves.contains(new Move(3, 3, 4, 4, Move.MoveType.ATTACK)), "King can attack enemy pawn at (4, 4)");
    }

    @Test
    void testKingMoveAndAttackCombination() {
        // Place a white king at (4, 4), a black knight at (5, 5), and a black rook at (3, 3)
        board.getPieces()[4][4] = new King(true);
        board.getPieces()[5][5] = new Knight(false);
        board.getPieces()[3][3] = new Rook(false);

        // Get king's moves
        List<Move> kingMoves = board.filterMoves(4, 4);

        // Test king can attack the black knight
        assertTrue(kingMoves.contains(new Move(4, 4, 5, 5, Move.MoveType.ATTACK)), "King can attack black knight at (5, 5)");

        // Test king cannot move past the black rook
        assertFalse(kingMoves.contains(new Move(4, 4, 3, 3, Move.MoveType.MOVE)), "King cannot move past the black rook at (3, 3)");
    }
}
