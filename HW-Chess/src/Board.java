import java.util.ArrayList;
import java.util.List;

public class Board {
private Piece[][] pieces;
public Board() {
	pieces = new Piece[8][8];
	setupBoard();
}
public Piece[][] getPieces() {
	return pieces;
}

public void  setupBoard() {
	for(int i=0;i<8;i++) {
		pieces[1][i] = new Pawn(true);
		pieces[6][i] = new Pawn(false);
	
	}
}
public List<Move> filterMoves(int x, int y) {
    List<Move> moves = pieces[x][y].possibleMovesFrom(x, y);
    List<Move> validMoves = new ArrayList<>();

    for (int i = 0; i < moves.size(); i++) {
        Move move = moves.get(i);
        int targetX = move.getX(); // Assuming Move class has a method getX()
        int targetY = move.getY(); // Assuming Move class has a method getY()
        
        // Example condition: check if the move is within board boundaries
        if (targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8) {
            validMoves.add(move);
        }
    
    }
    return validMoves;
}
public Piece[][] getPlayablePieces(boolean white){
	Piece[][] playablePieces = new Piece[8][8];
	for(int i=0;i<8;i++) {
		for(int j=0;j<8;j++) {
			if(pieces[i][j].isWhite()==white & pieces[i][j].isNotEmpty()) {				
				playablePieces[i][j]=pieces[i][j];				
			}
		}
	}
	return playablePieces;
}


};
