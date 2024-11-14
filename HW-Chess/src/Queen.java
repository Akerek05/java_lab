import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece {
    @Override
    public PieceType type() {
        return PieceType.QUEEN;
    }

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public List<Move> possibleMovesFrom(int x, int y) {
        List<Move> moves = new ArrayList<>();
        // Combination of Rook and Bishop moves for both movement and attack
        for (int i = 1; i < 8; i++) {
            moves.add(new Move(x,y,x + i, y, Move.MoveType.MOVE));     // Right
            moves.add(new Move(x,y,x + i, y, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x - i, y, Move.MoveType.MOVE));     // Left
            moves.add(new Move(x,y,x - i, y, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x, y + i, Move.MoveType.MOVE));     // Up
            moves.add(new Move(x,y,x, y + i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x, y - i, Move.MoveType.MOVE));     // Down
            moves.add(new Move(x,y,x, y - i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x + i, y + i, Move.MoveType.MOVE)); // Up-right
            moves.add(new Move(x,y,x + i, y + i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x + i, y - i, Move.MoveType.MOVE)); // Down-right
            moves.add(new Move(x,y,x + i, y - i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x - i, y + i, Move.MoveType.MOVE)); // Up-left
            moves.add(new Move(x,y,x - i, y + i, Move.MoveType.ATTACK));
            
            moves.add(new Move(x,y,x - i, y - i, Move.MoveType.MOVE)); // Down-left
            moves.add(new Move(x,y,x - i, y - i, Move.MoveType.ATTACK));
        }
        return moves;
    }
    @Override
    public Piece clone() {
        return new Queen(this.isWhite());
    }
}