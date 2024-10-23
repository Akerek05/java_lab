import java.util.Objects;

public class Move {
	public enum MoveType {
		MOVE, ATTACK;
	}
	private MoveType type;
	public MoveType getMoveType() {
		return type;
	}
	private int x; //where you move
	private int y;
	public int getX () {
		return x;
	}
	public int getY() {
		return y;
	}
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Move move = (Move) obj;
	    return x == move.x && y == move.y && type == move.type;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(x, y, type);
	}
	
	public Move(int x, int y, MoveType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
