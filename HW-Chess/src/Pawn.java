import java.util.List;
import java.util.ArrayList;

public class Pawn extends Piece {
	private static final long serialVersionUID = 1L;
	@Override
	public PieceType type() {
		return PieceType.PAWN;		
	}
	public Pawn (boolean isWhite) {
		super(isWhite);
	}
	
	public List<Move> possibleMovesFrom(int x, int y) {
		List<Move> moves =  new ArrayList<Move>();
		if(isWhite()) {
			if(x==1) {
				moves.add(new Move(x,y,x+2,y,Move.MoveType.MOVE));
			}			
			moves.add(new Move(x,y,x+1,y,Move.MoveType.MOVE));							
			moves.add(new Move(x,y,x+1,y-1,Move.MoveType.ATTACK));								
			moves.add(new Move(x,y,x+1,y+1,Move.MoveType.ATTACK));							
		}
		else {
			if(x==6) {
				moves.add(new Move(x,y,x-2,y,Move.MoveType.MOVE));
			}
			moves.add(new Move(x,y,x-1,y,Move.MoveType.MOVE));
			moves.add(new Move(x,y,x-1,y-1,Move.MoveType.ATTACK));								
			moves.add(new Move(x,y,x-1,y+1,Move.MoveType.ATTACK));
		}
		return moves; 
	}
	@Override
    public Piece clone() {
        return new Pawn(this.isWhite());
    }
}
