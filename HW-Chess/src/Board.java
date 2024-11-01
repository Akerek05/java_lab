import java.util.ArrayList;
import java.util.List;

public class Board {
private Piece[][] pieces;
public Board() {
	pieces = new Piece[8][8];
	
}
public Piece[][] getPieces() {
	return pieces;
}
public void setupEmptyBoard() {
    for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
            pieces[x][y] = new Empty(); // Fill with empty pieces
        }
    }
}

public void setupBoard() {
    pieces = new Piece[8][8]; 

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
    Piece currentPiece = pieces[x][y];

    for (Move move : moves) {
        int targetX = move.getX();
        int targetY = move.getY();

        // Check if the target is within board boundaries
        if (targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8) {
            Piece targetPiece = pieces[targetX][targetY];

            if (move.getMoveType() == Move.MoveType.MOVE) {
                // For MOVE, check each square along the path is empty until the target
                if (isPathClear(x, y, targetX, targetY)) {
                    if (targetPiece.type() == Piece.PieceType.EMPTY) {
                        validMoves.add(move);
                    }
                }
            } else if (move.getMoveType() == Move.MoveType.ATTACK) {
                // For ATTACK, path must be clear until the target square, which should have an enemy
                if (isPathClear(x, y, targetX, targetY) && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite()) {
                    validMoves.add(move);
                }
            }
        }
    }
    return validMoves;
}

private boolean isPathClear(int startX, int startY, int endX, int endY) {
    int deltaX = Integer.compare(endX, startX);
    int deltaY = Integer.compare(endY, startY);

    int currentX = startX + deltaX;
    int currentY = startY + deltaY;

    // Traverse along the path until reaching the end coordinates
    while (currentX != endX || currentY != endY) {
        if (pieces[currentX][currentY].type() != Piece.PieceType.EMPTY) {
            return false;  // Path is blocked by another piece
        }
        currentX += deltaX;
        currentY += deltaY;
    }
    return true;  // Path is clear up to (endX, endY)
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
