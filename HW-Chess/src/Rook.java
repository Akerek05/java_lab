import java.util.List;
import java.util.ArrayList;

public class Rook extends Piece {
    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public List<Move> possibleMovesFrom(int x, int y) {
        List<Move> moves = new ArrayList<>();
        // Horizontal and vertical moves and attacks
        for (int i = 1; i < 8; i++) {
            moves.add(new Move(x,y,x + i, y, Move.MoveType.MOVE));  // Right
            moves.add(new Move(x,y,x + i, y, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x - i, y, Move.MoveType.MOVE));  // Left
            moves.add(new Move(x,y,x - i, y, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x, y + i, Move.MoveType.MOVE));  // Up
            moves.add(new Move(x,y,x, y + i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x, y - i, Move.MoveType.MOVE));  // Down
            moves.add(new Move(x,y,x, y - i, Move.MoveType.ATTACK));
        }
        return moves;
    }
    @Override
    public Piece clone() {
        return new Rook(this.isWhite());
    }
}