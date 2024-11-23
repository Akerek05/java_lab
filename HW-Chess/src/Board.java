import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.Serializable;
public class Board implements Serializable{
private static final long serialVersionUID = 1L;
private Piece[][] pieces;
private Piece[][] previousBoardState1; // Board state before player's move
private Piece[][] previousBoardState2; // Board state before AI's move
private Piece[][] previousBoardState3; // Board state before both moves

public Board() {
	pieces = new Piece[8][8];
	previousBoardState1 = new Piece[8][8];
	previousBoardState2 = new Piece[8][8];
	previousBoardState3 = new Piece[8][8];
	
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
            if (sourceBoard[i][j] != null) {
                destinationBoard[i][j] = sourceBoard[i][j].clone();
            } else {
                destinationBoard[i][j] = null; // Set to null if the square is empty
            }
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

public List<Move> filterMoves(int x, int y) {
    List<Move> allMoves = pieces[x][y].possibleMovesFrom(x, y);
    List<Move> validMoves = new ArrayList<>();
    Piece currentPiece = pieces[x][y];
    boolean isKingChecked = false; // Flag to track if the current player's king is in check

    // Locate the current player's king
    int kingX = -1, kingY = -1;
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = pieces[i][j];
            if (piece.type() == Piece.PieceType.KING && piece.isWhite() == currentPiece.isWhite()) {
                kingX = i;
                kingY = j;
                break;
            }
        }
        if (kingX != -1) break;
    }

    // Check moves for validity
    for (Move move : allMoves) {
        int targetX = move.getX();
        int targetY = move.getY();

        if (targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8) {
            Piece targetPiece = pieces[targetX][targetY];
            boolean isValidMove = false;

            // Validate moves based on piece type
            if (currentPiece.type() == Piece.PieceType.KNIGHT) {
                if (move.getMoveType() == Move.MoveType.MOVE && targetPiece.type() == Piece.PieceType.EMPTY) {
                    isValidMove = true;
                } else if (move.getMoveType() == Move.MoveType.ATTACK && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite()) {
                    isValidMove = true;
                }
            } else if (currentPiece.type() == Piece.PieceType.KING) {
                // For the king, ensure the target square is not under attack after moving
                List<Move> attackingMoves = getAttackingMoves(targetX, targetY, !currentPiece.isWhite());
                if (move.getMoveType() == Move.MoveType.MOVE && targetPiece.type() == Piece.PieceType.EMPTY && attackingMoves.isEmpty()) {
                    isValidMove = true;
                } else if (move.getMoveType() == Move.MoveType.ATTACK && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite() && attackingMoves.isEmpty()) {
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
                // Simulate the move
                Piece originalPiece = pieces[targetX][targetY];
                pieces[targetX][targetY] = currentPiece;
                pieces[x][y] = new Empty(); // Assuming EmptyPiece represents an empty square

                // If the piece being moved is the king, update the king's position
                if (currentPiece.type() == Piece.PieceType.KING) {
                    kingX = targetX;
                    kingY = targetY;  // Update the king's position after the move
                }

                // Check if the king is under attack after this move
                List<Move> attackingMoves = getAttackingMoves(kingX, kingY, !currentPiece.isWhite());
                boolean kingInCheckAfterMove = !attackingMoves.isEmpty();

                if (!kingInCheckAfterMove) {
                    validMoves.add(move); // Add the move if it doesn't leave the king in check
                } else {
                    isKingChecked = true; // Flag remains true if any move leaves the king in check
                }

                // Revert the move
                pieces[x][y] = currentPiece;
                pieces[targetX][targetY] = originalPiece;
            }

        }
    }

    // Debug message for the isKingChecked flag
    System.out.println("Is king in check: " + isKingChecked);

    return validMoves.stream()
            .filter(m -> m.getOriginalX() == x && m.getOriginalY() == y)
            .toList();
}

public void promotePawn(int x, int y, char promotionType) {
    boolean isWhite = pieces[x][y].isWhite();
    switch (promotionType) {
        case 'Q':
            pieces[x][y] = new Queen(isWhite);
            break;
        case 'R':
            pieces[x][y] = new Rook(isWhite);
            break;
        case 'B':
            pieces[x][y] = new Bishop(isWhite);
            break;
        case 'K':
            pieces[x][y] = new Knight(isWhite);
            break;
        default:
            throw new IllegalArgumentException("Invalid promotion type: " + promotionType);
    }
}


private List<Move> getAttackingMoves(int x, int y, boolean opponentIsWhite) {
    List<Move> attackingMoves = new ArrayList<>();

    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = pieces[i][j];
            if (piece.isNotEmpty() && piece.isWhite() == opponentIsWhite) {
                List<Move> possibleMoves = piece.possibleMovesFrom(i, j);
                for (Move move : possibleMoves) {
                    int targetX = move.getX();
                    int targetY = move.getY();

                    // Check if the move targets the specified square
                    if (targetX == x && targetY == y) {
                        boolean isValid = false;

                        // Special case for knights: no path clearance needed
                        if (piece.type() == Piece.PieceType.KNIGHT) {
                            isValid = true;
                        } else {
                            // Check if the path is clear for other pieces
                            isValid = isPathClear(i, j, targetX, targetY);
                        }

                        if (isValid) {
                            attackingMoves.add(move);
                        }
                    }
                }
            }
        }
    }

    return attackingMoves;
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

    // Save the board state before making the move
    saveBoardState();

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
    Piece[][] playablePieces = getPlayablePieces(whiteTurn);

    // Locate the current player's king
    int kingX = -1, kingY = -1;
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = pieces[i][j];
            if (piece.type() == Piece.PieceType.KING && piece.isWhite() == whiteTurn) {
                kingX = i;
                kingY = j;
                break;
            }
        }
        if (kingX != -1) break;
    }

    // Check if the king is in check
    boolean kingInCheck = !getAttackingMoves(kingX, kingY, !whiteTurn).isEmpty();

    // Check if there are any valid moves for the current player
    for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
            Piece piece = playablePieces[i][j];
            if (piece != null) {
                List<Move> moves = filterMoves(i, j);
                if (!moves.isEmpty()) {
                    return false; // The player has valid moves
                }
            }
        }
    }

    return kingInCheck; // Checkmate occurs if the king is in check and no valid moves exist
}

public Move.MoveType getMoveType(int startX, int startY, int endX, int endY) {
    Piece targetPiece = pieces[endX][endY];
    return targetPiece.isNotEmpty() && targetPiece.isWhite() != pieces[startX][startY].isWhite() 
        ? Move.MoveType.ATTACK 
        : Move.MoveType.MOVE;
}
public Piece[][] getPreviousBoardState1() {
	
	return previousBoardState1;
}
public Piece[][] getPreviousBoardState2() {
	
	return previousBoardState2;
}


};
