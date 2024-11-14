import java.util.List;
import java.util.ArrayList;

public class King extends Piece {
    @Override
    public PieceType type() {
        return PieceType.KING;
    }

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public List<Move> possibleMovesFrom(int x, int y) {
        List<Move> moves = new ArrayList<>();
        // King's one-square moves in all directions for both movement and attack
        int[][] kingMoves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        for (int[] move : kingMoves) {
            moves.add(new Move(x,y,x + move[0], y + move[1], Move.MoveType.MOVE));
            moves.add(new Move(x,y,x + move[0], y + move[1], Move.MoveType.ATTACK));
        }
        return moves;
    }
    @Override
    public Piece clone() {
        return new King(this.isWhite());
    }
}