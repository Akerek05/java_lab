import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

class KingCheckLogicTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
        board.setupEmptyBoard();
    }

    @Test
    void testKingCannotMoveIntoCheck() {
        // Place a white king at (4, 4) and a black queen at (0, 4) which can check the king
        board.getPieces()[4][4] = new King(true);
        board.getPieces()[0][4] = new Queen(false); // The black queen checks along the same file

        // Try to get valid moves for the king using filterMoves
        List<Move> kingMoves = board.filterMoves(4, 4);

        // Ensure that the king cannot move into a position where it would be in check
        assertFalse(kingMoves.contains(new Move(4, 4, 3, 4, Move.MoveType.MOVE)), "King cannot move to (3, 4) as it would be in check");
        assertFalse(kingMoves.contains(new Move(4, 4, 5, 4, Move.MoveType.MOVE)), "King cannot move to (5, 4) as it would be in check");
    }

    @Test
    void testKingCanMoveOutOfCheck() {
        // Place a white king at (4, 4) and a black queen at (0, 4) putting the king in check
        board.getPieces()[4][4] = new King(true);
        board.getPieces()[0][4] = new Queen(false); // The black queen checks along the same file

        // Place a white pawn at (5, 4) to block the check
        board.getPieces()[5][4] = new Pawn(true); // The pawn blocks the check path

        // Get valid moves for the king using filterMoves
        List<Move> kingMoves = board.filterMoves(4, 4);
        
        // Ensure the king can move to a square that takes it out of check (like (4, 5))
        assertTrue(kingMoves.contains(new Move(4, 4, 4, 5, Move.MoveType.MOVE)), "King can move to (4, 5) without being in check");
    }

    @Test
    void testKingCannotMoveWhileInCheck() {
        // Place a white king at (4, 4) and a black queen at (0, 4) putting the king in check
        board.getPieces()[4][4] = new King(true);
        board.getPieces()[0][4] = new Queen(false); // The black queen checks along the same file

        // Try to move the king while it's in check (e.g., move to (5, 4))
        List<Move> kingMoves = board.filterMoves(4, 4);

        // Ensure that no move is valid while the king is in check
        assertFalse(kingMoves.contains(new Move(4, 4, 5, 4, Move.MoveType.MOVE)), "King cannot move to (5, 4) while in check");
    }

    @Test
    void testKingBlockedByOwnPieceWhileInCheck() {
        // Place a white king at (4, 4), a white pawn at (5, 4), and a black queen at (0, 4)
        board.getPieces()[4][4] = new King(true);
        board.getPieces()[5][4] = new Pawn(true); // Blocking the king from being in check
        board.getPieces()[0][4] = new Queen(false); // The black queen puts the king in check along the same file

        // Get valid moves for the king using filterMoves
        List<Move> kingMoves = board.filterMoves(4, 4);

        // The king cannot move to (5, 4) as it is blocked by the pawn
        assertFalse(kingMoves.contains(new Move(4, 4, 5, 4, Move.MoveType.MOVE)), "King cannot move to (5, 4) as it's blocked by its own piece");

        // The king cannot move to (3, 4) as it would still be in check
        assertFalse(kingMoves.contains(new Move(4, 4, 3, 4, Move.MoveType.MOVE)), "King cannot move to (3, 4) as it would still be in check");
    }

    

        

        
    @Test
    void testKingAttackingPieceCheckingIt() {
            
            board.getPieces()[0][0] = new King(true);
            board.getPieces()[1][1] = new Queen(false); 
            List<Move> kingMoves = board.filterMoves(0, 0);
            assertTrue(kingMoves.contains(new Move(0, 0, 1, 1, Move.MoveType.ATTACK)), "King can attack piece next to it checking it");

    }
    @Test
    void testKingisSavedByFrendlyMovingtoBlock() {
            
            board.getPieces()[0][0] = new King(true);
            board.getPieces()[0][3] = new Queen(false);
            board.getPieces()[0][2] = new Rook(true);
            List<Move> kingMoves = board.filterMoves(0, 2);
            assertTrue(kingMoves.contains(new Move(0, 2, 0, 1, Move.MoveType.MOVE)), "King can be saved be rook blocking the way");

    }
}


