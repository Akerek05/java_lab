import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece {
    @Override
    public PieceType type() {
        return PieceType.BISHOP;
    }

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public List<Move> possibleMovesFrom(int x, int y) {
        List<Move> moves = new ArrayList<>();
        // Diagonal moves for both movement and attack
        for (int i = 1; i < 8; i++) {
            moves.add(new Move(x + i, y + i, Move.MoveType.MOVE));  // Up-right
            moves.add(new Move(x + i, y + i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x + i, y - i, Move.MoveType.MOVE));  // Down-right
            moves.add(new Move(x + i, y - i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x - i, y + i, Move.MoveType.MOVE));  // Up-left
            moves.add(new Move(x - i, y + i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x - i, y - i, Move.MoveType.MOVE));  // Down-left
            moves.add(new Move(x - i, y - i, Move.MoveType.ATTACK));
        }
        return moves;
    }
}
