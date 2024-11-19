import java.util.List;
import java.util.ArrayList;

public class Empty extends Piece {
	 private static final long serialVersionUID = 1L;
	public Empty() {
        super(false); // Empty squares are neither white nor black
    }

    @Override
    public PieceType type() {
        return PieceType.EMPTY;
    }

    @Override
    public List<Move> possibleMovesFrom(int x, int y) {
        return new ArrayList<>(); // No moves for an empty space
    }
    @Override
    public Piece clone() {
        return new Empty();
    }
}