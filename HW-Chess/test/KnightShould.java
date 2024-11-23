import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KnightShould {

    private Board board;

    @BeforeEach
    void setUp() throws Exception {
        board = new Board();
        board.setupEmptyBoard();
    }

    @Test
    void testKnightValidMovesOnEmptyBoard() {
        // Place a white knight at (3, 3)
        board.getPieces()[3][3] = new Knight(true);

        // Get all valid moves for the knight
        List<Move> knightMoves = board.filterMoves(3, 3);

        // Test the 8 possible L-shaped moves of the knight
        assertTrue(knightMoves.contains(new Move(3, 3, 1, 2, Move.MoveType.MOVE)), "Knight can move to (1, 2)");
        assertTrue(knightMoves.contains(new Move(3, 3, 1, 4, Move.MoveType.MOVE)), "Knight can move to (1, 4)");
        assertTrue(knightMoves.contains(new Move(3, 3, 2, 5, Move.MoveType.MOVE)), "Knight can move to (2, 5)");
        assertTrue(knightMoves.contains(new Move(3, 3, 4, 5, Move.MoveType.MOVE)), "Knight can move to (4, 5)");
        assertTrue(knightMoves.contains(new Move(3, 3, 5, 4, Move.MoveType.MOVE)), "Knight can move to (5, 4)");
        assertTrue(knightMoves.contains(new Move(3, 3, 5, 2, Move.MoveType.MOVE)), "Knight can move to (5, 2)");
        assertTrue(knightMoves.contains(new Move(3, 3, 4, 1, Move.MoveType.MOVE)), "Knight can move to (4, 1)");
        assertTrue(knightMoves.contains(new Move(3, 3, 2, 1, Move.MoveType.MOVE)), "Knight can move to (2, 1)");
    }

    @Test
    void testKnightBlockedByOwnPiece() {
        // Place a white knight at (3, 3) and a white pawn blocking at (2, 5)
        board.getPieces()[3][3] = new Knight(true);
        board.getPieces()[2][5] = new Pawn(true);

        // Get knight's moves
        List<Move> knightMoves = board.filterMoves(3, 3);

        // Ensure the knight cannot move to (2, 5), where its own piece is blocking
        assertFalse(knightMoves.contains(new Move(3, 3, 2, 5, Move.MoveType.MOVE)), "Knight cannot move to (2, 5), blocked by its own piece");
    }

    @Test
    void testKnightAttackEnemyPiece() {
        // Place a white knight at (3, 3) and a black pawn at (2, 5)
        board.getPieces()[3][3] = new Knight(true);
        board.getPieces()[2][5] = new Pawn(false);

        // Get knight's moves
        List<Move> knightMoves = board.filterMoves(3, 3);

        // Test that the knight can attack the black pawn
        assertTrue(knightMoves.contains(new Move(3, 3, 2, 5, Move.MoveType.ATTACK)), "Knight can attack enemy pawn at (2, 5)");
    }

    @Test
    void testKnightMoveAndAttackCombination() {
        // Place a white knight at (4, 4), a black rook at (2, 3), and a black pawn at (2, 5)
        board.getPieces()[4][4] = new Knight(true);
        board.getPieces()[2][3] = new Rook(false);
        board.getPieces()[2][5] = new Pawn(false);

        // Get knight's moves
        List<Move> knightMoves = board.filterMoves(4, 4);

        // Test knight can attack the black pawn
        assertTrue(knightMoves.contains(new Move(4, 4, 2, 5, Move.MoveType.ATTACK)), "Knight can attack black pawn at (2, 5)");

        // Test knight cannot move past the black rook
        assertFalse(knightMoves.contains(new Move(4, 4, 2, 3, Move.MoveType.MOVE)), "Knight cannot move past the black rook at (2, 3)");
    }

    @Test
    void testKnightCanJumpOverPieces() {
        // Place a white knight at (3, 3) and a white pawn blocking (4, 5)
        board.getPieces()[3][3] = new Knight(true);
        board.getPieces()[4][5] = new Pawn(true); // This piece should be jumped over

        // Get knight's moves
        List<Move> knightMoves = board.filterMoves(3, 3);

        // Test that the knight can still move to (5, 4) even though the pawn is in the way
        assertTrue(knightMoves.contains(new Move(3, 3, 5, 4, Move.MoveType.MOVE)), "Knight can jump over pawn at (4, 5) to move to (5, 4)");

        // Ensure knight cannot move to (4, 5) as it's blocked by its own piece
        assertFalse(knightMoves.contains(new Move(3, 3, 4, 5, Move.MoveType.MOVE)), "Knight cannot land on (4, 5), blocked by its own piece");
    }
}
