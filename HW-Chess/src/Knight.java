import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece {
    @Override
    public PieceType type() {
        return PieceType.KNIGHT;
    }

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public List<Move> possibleMovesFrom(int x, int y) {
        List<Move> moves = new ArrayList<>();
        // Knight's "L" shaped moves for both movement and attack
        int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};
        for (int[] move : knightMoves) {
            moves.add(new Move(x,y,x + move[0], y + move[1], Move.MoveType.MOVE));
            moves.add(new Move(x,y,x + move[0], y + move[1], Move.MoveType.ATTACK));
        }
        return moves;
    }
    @Override
    public Piece clone() {
        return new Knight(this.isWhite());
    }
}