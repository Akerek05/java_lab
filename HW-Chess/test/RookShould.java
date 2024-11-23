import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RookShould {

    private Board board;

    @BeforeEach
    void setUp() throws Exception {
        board = new Board();
        board.setupEmptyBoard();
    }

    @Test
    void testRookValidMovesOnEmptyBoard() {
        // Place a white rook at (3, 3)
        board.getPieces()[3][3] = new Rook(true);

        // Get all valid moves for the rook
        List<Move> rookMoves = board.filterMoves(3, 3);

        // Test valid horizontal and vertical moves
        assertTrue(rookMoves.contains(new Move(3, 3, 3, 0, Move.MoveType.MOVE)), "Rook can move horizontally to (3, 0)");
        assertTrue(rookMoves.contains(new Move(3, 3, 3, 7, Move.MoveType.MOVE)), "Rook can move horizontally to (3, 7)");
        assertTrue(rookMoves.contains(new Move(3, 3, 0, 3, Move.MoveType.MOVE)), "Rook can move vertically to (0, 3)");
        assertTrue(rookMoves.contains(new Move(3, 3, 7, 3, Move.MoveType.MOVE)), "Rook can move vertically to (7, 3)");
    }

    @Test
    void testRookBlockedByOwnPiece() {
        // Place a white rook at (3, 3) and a white pawn blocking at (3, 5)
        board.getPieces()[3][3] = new Rook(true);
        board.getPieces()[3][5] = new Pawn(true);

        // Get rook's moves
        List<Move> rookMoves = board.filterMoves(3, 3);

        // Ensure the rook cannot move past or attack its own piece
        assertFalse(rookMoves.contains(new Move(3, 3, 3, 5, Move.MoveType.ATTACK)), "Rook cannot attack its own pawn at (3, 5)");
        assertFalse(rookMoves.contains(new Move(3, 3, 3, 6, Move.MoveType.MOVE)), "Rook cannot move past its own pawn at (3, 5)");
    }

    @Test
    void testRookAttackEnemyPiece() {
        // Place a white rook at (3, 3) and a black pawn at (5, 3)
        board.getPieces()[3][3] = new Rook(true);
        board.getPieces()[5][3] = new Pawn(false);

        // Get rook's moves
        List<Move> rookMoves = board.filterMoves(3, 3);

        // Test that rook can attack the black pawn
        assertTrue(rookMoves.contains(new Move(3, 3, 5, 3, Move.MoveType.ATTACK)), "Rook can attack enemy pawn at (5, 3)");
    }

    @Test
    void testRookBlockedByEnemyPiece() {
        // Place a white rook at (3, 3) and a black pawn at (3, 5)
        board.getPieces()[3][3] = new Rook(true);
        board.getPieces()[3][5] = new Pawn(false);

        // Get rook's moves
        List<Move> rookMoves = board.filterMoves(3, 3);

        // Test rook can attack the black pawn but cannot move past it
        assertTrue(rookMoves.contains(new Move(3, 3, 3, 5, Move.MoveType.ATTACK)), "Rook can attack enemy pawn at (3, 5)");
        assertFalse(rookMoves.contains(new Move(3, 3, 3, 6, Move.MoveType.MOVE)), "Rook cannot move past enemy pawn at (3, 5)");
    }

    @Test
    void testRookMoveAndAttackCombination() {
        // Place a white rook at (4, 4), a black knight at (4, 6), and a black rook at (4, 7)
        board.getPieces()[4][4] = new Rook(true);
        board.getPieces()[4][6] = new Knight(false);
        board.getPieces()[4][7] = new Rook(false);

        // Get rook's moves
        List<Move> rookMoves = board.filterMoves(4, 4);

        // Test rook can attack the black knight
        assertTrue(rookMoves.contains(new Move(4, 4, 4, 6, Move.MoveType.ATTACK)), "Rook can attack the black knight at (4, 6)");

        // Test rook cannot move past the black rook
        assertFalse(rookMoves.contains(new Move(4, 4, 4, 8, Move.MoveType.MOVE)), "Rook cannot move past the black rook at (4, 7)");
    }

    @Test
    void testRookCornerMoves() {
        // Place a white rook at (0, 0) - corner of the board
        board.getPieces()[0][0] = new Rook(true);

        // Get rook's moves
        List<Move> rookMoves = board.filterMoves(0, 0);

        // Test horizontal and vertical moves from the corner
        assertTrue(rookMoves.contains(new Move(0, 0, 0, 7, Move.MoveType.MOVE)), "Rook can move horizontally to (0, 7)");
        assertTrue(rookMoves.contains(new Move(0, 0, 7, 0, Move.MoveType.MOVE)), "Rook can move vertically to (7, 0)");
    }
}
