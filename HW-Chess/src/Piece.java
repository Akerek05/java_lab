import java.util.List;

public abstract class Piece {
	public enum PieceType {
	    EMPTY, PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING;
	}
	
	public boolean isNotEmpty() {
		return type()!=PieceType.EMPTY;
	}
	private boolean isWhite;
	public boolean isWhite() {
		return isWhite;
	}
	public PieceType type() {
		return PieceType.EMPTY;		
	} 
	public Piece (boolean isWhite) {
		this.isWhite=isWhite;
	}
	public abstract List<Move> possibleMovesFrom(int x, int y);	
}
