import java.util.List;

public abstract class Piece {
    public enum PieceType {
        EMPTY, PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;
    }

    private boolean isWhite;

    public boolean isWhite() {
        return isWhite;
    }

    public PieceType type() {
        return PieceType.EMPTY;
    }

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    // Abstract method for cloning, to be implemented by subclasses
    public abstract Piece clone();

    // Abstract method for calculating possible moves
    public abstract List<Move> possibleMovesFrom(int x, int y);

    // Utility method to check if the piece is not empty
    public boolean isNotEmpty() {
        return type() != PieceType.EMPTY;
    }
}
