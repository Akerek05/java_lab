import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BishopShould {

    private Board board;

    @BeforeEach
    void setUp() throws Exception {
        board = new Board();
        board.setupEmptyBoard();
    }

    @Test
    void testBishopValidMovesOnEmptyBoard() {
        // Place a white bishop at (3, 3)
        board.getPieces()[3][3] = new Bishop(true);

        // Get all valid moves for the bishop
        List<Move> bishopMoves = board.filterMoves(3, 3);

        // Test valid diagonal moves
        assertTrue(bishopMoves.contains(new Move(3, 3, 2, 2, Move.MoveType.MOVE)), "Bishop can move diagonally to (2, 2)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 1, 1, Move.MoveType.MOVE)), "Bishop can move diagonally to (1, 1)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 0, 0, Move.MoveType.MOVE)), "Bishop can move diagonally to (0, 0)");

        assertTrue(bishopMoves.contains(new Move(3, 3, 4, 4, Move.MoveType.MOVE)), "Bishop can move diagonally to (4, 4)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 5, 5, Move.MoveType.MOVE)), "Bishop can move diagonally to (5, 5)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 6, 6, Move.MoveType.MOVE)), "Bishop can move diagonally to (6, 6)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 7, 7, Move.MoveType.MOVE)), "Bishop can move diagonally to (7, 7)");

        assertTrue(bishopMoves.contains(new Move(3, 3, 4, 2, Move.MoveType.MOVE)), "Bishop can move diagonally to (4, 2)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 5, 1, Move.MoveType.MOVE)), "Bishop can move diagonally to (5, 1)");
        assertTrue(bishopMoves.contains(new Move(3, 3, 6, 0, Move.MoveType.MOVE)), "Bishop can move diagonally to (6, 0)");
    }

    @Test
    void testBishopBlockedByOwnPiece() {
        // Place a white bishop at (3, 3) and a white pawn blocking at (5, 5)
        board.getPieces()[3][3] = new Bishop(true);
        board.getPieces()[5][5] = new Pawn(true);

        // Get bishop's moves
        List<Move> bishopMoves = board.filterMoves(3, 3);

        // Ensure the bishop cannot move past or attack its own piece
        assertFalse(bishopMoves.contains(new Move(3, 3, 5, 5, Move.MoveType.ATTACK)), "Bishop cannot attack its own pawn at (5, 5)");
        assertFalse(bishopMoves.contains(new Move(3, 3, 6, 6, Move.MoveType.MOVE)), "Bishop cannot move past its own pawn at (5, 5)");
    }

    @Test
    void testBishopAttackEnemyPiece() {
        // Place a white bishop at (3, 3) and a black pawn at (5, 5)
        board.getPieces()[3][3] = new Bishop(true);
        board.getPieces()[5][5] = new Pawn(false);

        // Get bishop's moves
        List<Move> bishopMoves = board.filterMoves(3, 3);

        // Test that bishop can attack the black pawn
        assertTrue(bishopMoves.contains(new Move(3, 3, 5, 5, Move.MoveType.ATTACK)), "Bishop can attack enemy pawn at (5, 5)");
    }

    @Test
    void testBishopBlockedByEnemyPiece() {
        // Place a white bishop at (3, 3) and a black pawn at (5, 5)
        board.getPieces()[3][3] = new Bishop(true);
        board.getPieces()[5][5] = new Pawn(false);

        // Get bishop's moves
        List<Move> bishopMoves = board.filterMoves(3, 3);

        // Test bishop can attack the black pawn but cannot move past it
        assertTrue(bishopMoves.contains(new Move(3, 3, 5, 5, Move.MoveType.ATTACK)), "Bishop can attack enemy pawn at (5, 5)");
        assertFalse(bishopMoves.contains(new Move(3, 3, 6, 6, Move.MoveType.MOVE)), "Bishop cannot move past enemy pawn at (5, 5)");
    }

    @Test
    void testBishopCornerMoves() {
        // Place a white bishop at (0, 0) - corner of the board
        board.getPieces()[0][0] = new Bishop(true);

        // Get bishop's moves
        List<Move> bishopMoves = board.filterMoves(0, 0);

        // Test diagonal moves from the corner
        assertTrue(bishopMoves.contains(new Move(0, 0, 1, 1, Move.MoveType.MOVE)), "Bishop can move diagonally to (1, 1)");
        assertTrue(bishopMoves.contains(new Move(0, 0, 2, 2, Move.MoveType.MOVE)), "Bishop can move diagonally to (2, 2)");
        assertTrue(bishopMoves.contains(new Move(0, 0, 3, 3, Move.MoveType.MOVE)), "Bishop can move diagonally to (3, 3)");
        assertTrue(bishopMoves.contains(new Move(0, 0, 4, 4, Move.MoveType.MOVE)), "Bishop can move diagonally to (4, 4)");
        assertTrue(bishopMoves.contains(new Move(0, 0, 5, 5, Move.MoveType.MOVE)), "Bishop can move diagonally to (5, 5)");
        assertTrue(bishopMoves.contains(new Move(0, 0, 6, 6, Move.MoveType.MOVE)), "Bishop can move diagonally to (6, 6)");
        assertTrue(bishopMoves.contains(new Move(0, 0, 7, 7, Move.MoveType.MOVE)), "Bishop can move diagonally to (7, 7)");
    }
}
