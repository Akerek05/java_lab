import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Board {
private Piece[][] pieces;
private Piece[][] previousBoardState1; // Board state before player's move
private Piece[][] previousBoardState2; // Board state before AI's move
private Piece[][] previousBoardState3; // Board state before both moves

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
private void cloneBoard(Piece[][] sourceBoard, Piece[][] destinationBoard) {
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            destinationBoard[i][j] = sourceBoard[i][j].clone();
        }
    }
}
public void saveBoardState() {
    // Shift the board states
	cloneBoard(previousBoardState2, previousBoardState3); 
    cloneBoard(previousBoardState1, previousBoardState2); 
	cloneBoard(pieces, previousBoardState1); // Save the current board state to previousBoardState3
    
}

// Reset board to the saved state
public void restorePreviousState() {
	cloneBoard(previousBoardState1, pieces); // Restore to the most recent saved state
	cloneBoard(previousBoardState2, previousBoardState1); // Restore to the most recent saved state
	cloneBoard(previousBoardState3, previousBoardState2); // Restore to the most recent saved state
}
public int[] findKing(boolean isWhite) {
    for (int x = 0; x < 8; x++) {
        for (int y = 0; y < 8; y++) {
            Piece piece = pieces[x][y];
            if (piece.type() == Piece.PieceType.KING && piece.isWhite() == isWhite) {
                return new int[]{x, y}; // Return the coordinates of the King
            }
        }
    }
    return null; // King not found (shouldn't happen in a valid game state)
}

public List<Move> filterMoves(int x, int y) {
    List<Move> allMoves = pieces[x][y].possibleMovesFrom(x, y);
    List<Move> validMoves = new ArrayList<>();
    Piece currentPiece = pieces[x][y];

    for (Move move : allMoves) {
        int targetX = move.getX();
        int targetY = move.getY();

        if (targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8) {
            Piece targetPiece = pieces[targetX][targetY];
            boolean isValidMove = false;

            if (currentPiece.type() == Piece.PieceType.KNIGHT) {
                if (move.getMoveType() == Move.MoveType.MOVE && targetPiece.type() == Piece.PieceType.EMPTY) {
                    isValidMove = true;
                } else if (move.getMoveType() == Move.MoveType.ATTACK && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite()) {
                    isValidMove = true;
                }
            } else {
                if (move.getMoveType() == Move.MoveType.MOVE && isPathClear(x, y, targetX, targetY) && targetPiece.type() == Piece.PieceType.EMPTY) {
                    isValidMove = true;
                } else if (move.getMoveType() == Move.MoveType.ATTACK && isPathClear(x, y, targetX, targetY) && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite()) {
                    isValidMove = true;
                }
            }

            if (isValidMove) {
                validMoves.add(move);
                System.out.println("Adding valid move: " + move);
            }
        }
    }

    return validMoves.stream()
            .filter(m -> m.getOriginalX() == x && m.getOriginalY() == y)
            .toList();
}



private boolean isPathClear(int startX, int startY, int endX, int endY) {
    int deltaX = Integer.compare(endX, startX);
    int deltaY = Integer.compare(endY, startY);

    int currentX = startX + deltaX;
    int currentY = startY + deltaY;

    // Traverse along the path until reaching the end coordinates
    while (currentX != endX || currentY != endY) {
    	if (currentX < 0 || currentX >= 8 || currentY < 0 || currentY >= 8) {
            return false;  // Out of bounds, so path is considered blocked
        }
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

public boolean move(int x, int y, Move move) {
    System.out.println("Attempting to move piece from (" + x + "," + y + ") to (" + move.getX() + "," + move.getY() + ")");

    List<Move> validMoves = filterMoves(x, y);
    if (!validMoves.contains(move)) {
        System.out.println("Move rejected: Not in the list of valid moves.");
        return false;
    }

    int targetX = move.getX();
    int targetY = move.getY();
    Piece movingPiece = pieces[x][y];

    System.out.println("Moving piece: " + movingPiece + " to (" + targetX + "," + targetY + ")");

    if (move.getMoveType() == Move.MoveType.ATTACK) {
        pieces[targetX][targetY] = movingPiece;
        pieces[x][y] = new Empty();
    } else if (move.getMoveType() == Move.MoveType.MOVE) {
        pieces[targetX][targetY] = movingPiece;
        pieces[x][y] = new Empty();
    }

    System.out.println("Move successful. Updated board.");
    return true;
}


public void makeComputerMove(boolean isWhite) {
    Random random = new Random();
    Piece[][] playablePieces = getPlayablePieces(isWhite);

    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = playablePieces[i][j];
            if (piece != null) {
                List<Move> moves = filterMoves(i, j);
                if (!moves.isEmpty()) {
                    Move randomMove = moves.get(random.nextInt(moves.size()));
                    if (move(i, j, randomMove)) {
                        
                        System.out.println("AI moves from " + i + "," + j + " to " + randomMove.getX() + "," + randomMove.getY());
                        return;
                    }
                }
            }
        }
    }

    System.out.println("AI has no valid moves.");
}


public boolean isCheckmate(boolean whiteTurn) {
   /* 
	Piece[][] playablePieces = getPlayablePieces(whiteTurn);
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = playablePieces[i][j];
            if (piece != null) {
                List<Move> moves = filterMoves(i, j);
                for (Move move : moves) {
                    if (move(i, j, move)) {
                        undoMove(i, j, move); // Assuming an undoMove method exists
                        return false;
                    }
                }
            }
        }
    }*/
    return false;
}
public Move.MoveType getMoveType(int startX, int startY, int endX, int endY) {
    Piece targetPiece = pieces[endX][endY];
    return targetPiece.isNotEmpty() && targetPiece.isWhite() != pieces[startX][startY].isWhite() 
        ? Move.MoveType.ATTACK 
        : Move.MoveType.MOVE;
}

};
