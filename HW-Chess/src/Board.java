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

public void setupBoard() {
    pieces = new Piece[8][8]; // Assuming 'pieces' is a 2D array of Piece objects

    // Set up pawns
    for (int i = 0; i < 8; i++) {
        pieces[1][i] = new Pawn(true);  // White pawns
        pieces[6][i] = new Pawn(false); // Black pawns
    }

    // Set up rooks
    pieces[0][0] = new Rook(true);
    pieces[0][7] = new Rook(true);
    pieces[7][0] = new Rook(false);
    pieces[7][7] = new Rook(false);

    // Set up knights
    pieces[0][1] = new Knight(true);
    pieces[0][6] = new Knight(true);
    pieces[7][1] = new Knight(false);
    pieces[7][6] = new Knight(false);

    // Set up bishops
    pieces[0][2] = new Bishop(true);
    pieces[0][5] = new Bishop(true);
    pieces[7][2] = new Bishop(false);
    pieces[7][5] = new Bishop(false);

    // Set up queens
    pieces[0][3] = new Queen(true);
    pieces[7][3] = new Queen(false);

    // Set up kings
    pieces[0][4] = new King(true);
    pieces[7][4] = new King(false);

    // Fill the remaining spaces with Empty pieces
    for (int i = 2; i < 6; i++) {
        for (int j = 0; j < 8; j++) {
            pieces[i][j] = new Empty();
        }
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
