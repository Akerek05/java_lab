import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class PawnShould {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setupEmptyBoard(); // Clear the board before each test
    }

    @AfterEach
    void tearDown() {
        board = null;
    }

    @Test
    void testPawnType() {
        Pawn piece = new Pawn(true);
        assertEquals(Piece.PieceType.PAWN, piece.type());
        assertTrue(piece.isWhite());
    }

    @Test
    void testPawnInitialMoves() {
        Pawn whitePawn = new Pawn(true);
        
        board.getPieces()[1][1] = whitePawn; // Directly place the pawn on the board

        List<Move> validMoves = board.filterMoves(1, 1);
        assertEquals(2, validMoves.size());
        assertTrue(validMoves.contains(new Move(1, 1, 2, 1, Move.MoveType.MOVE))); // One square forward
        assertTrue(validMoves.contains(new Move(1, 1, 3, 1, Move.MoveType.MOVE))); // Two squares forward
    }

    @Test
    void testPawnBlockedMoves() {
        Pawn whitePawn = new Pawn(true);
        Pawn blockingPiece = new Pawn(true);

        board.getPieces()[1][1] = whitePawn;
        board.getPieces()[2][1] = blockingPiece; // Place a blocking piece directly in front of the pawn

        List<Move> validMoves = board.filterMoves(1, 1);
        assertTrue(validMoves.isEmpty()); // Pawn cannot move when path is blocked
    }

    @Test
    void testPawnAttackingMoves() {
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawnLeft = new Pawn(false);
        Pawn blackPawnRight = new Pawn(false);

        board.getPieces()[4][4] = whitePawn;
        board.getPieces()[5][3] = blackPawnLeft; // Place opponent's piece diagonally left
        board.getPieces()[5][5] = blackPawnRight; // Place opponent's piece diagonally right

        List<Move> validMoves = board.filterMoves(4, 4);
        assertEquals(3, validMoves.size());
        assertTrue(validMoves.contains(new Move(4, 4, 5, 3, Move.MoveType.ATTACK))); // Attack left
        assertTrue(validMoves.contains(new Move(4, 4, 5, 5, Move.MoveType.ATTACK))); // Attack right
        assertTrue(validMoves.contains(new Move(4, 4, 5, 4, Move.MoveType.MOVE))); // Move forward
    }

    @Test
    void testPawnCannotAttackEmptyDiagonal() {
        Pawn whitePawn = new Pawn(true);
        board.getPieces()[4][4] = whitePawn;

        List<Move> validMoves = board.filterMoves(4, 4);
        assertEquals(1, validMoves.size()); // Only forward move is valid
        assertTrue(validMoves.contains(new Move(4, 4, 5, 4, Move.MoveType.MOVE))); // Forward move
    }

    @Test
    void testPawnPromotion() {
        Pawn whitePawn = new Pawn(true);
        board.getPieces()[6][0] = whitePawn; // Place the pawn one row before promotion

        List<Move> validMoves = board.filterMoves(6, 0);
        assertEquals(1, validMoves.size());
        assertTrue(validMoves.contains(new Move(6, 0, 7, 0, Move.MoveType.MOVE))); // Move to promotion row
    }

    @Test
    void testEdgeOfBoardNoAttack() {
        Pawn whitePawn = new Pawn(true);
        board.getPieces()[4][0] = whitePawn; // Place the pawn on the leftmost column

        List<Move> validMoves = board.filterMoves(4, 0);
        assertEquals(1, validMoves.size());
        assertTrue(validMoves.contains(new Move(4, 0, 5, 0, Move.MoveType.MOVE))); // Forward move only
    }

    @Test
    void testPawnBlockedAttack() {
        Pawn whitePawn = new Pawn(true);
        Pawn whiteBlockingLeft = new Pawn(true);
        Pawn whiteBlockingRight = new Pawn(true);

        board.getPieces()[4][4] = whitePawn;
        board.getPieces()[5][3] = whiteBlockingLeft; // Place friendly piece diagonally left
        board.getPieces()[5][5] = whiteBlockingRight; // Place friendly piece diagonally right

        List<Move> validMoves = board.filterMoves(4, 4);
        assertEquals(1, validMoves.size());
        assertTrue(validMoves.contains(new Move(4, 4, 5, 4, Move.MoveType.MOVE))); // Forward move only
    }
}
