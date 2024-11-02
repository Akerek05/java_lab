import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class Board {
private Piece[][] pieces;
private final Scanner scanner = new Scanner(System.in);
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

        // Check if target is within boundaries
        if (targetX >= 0 && targetX < 8 && targetY >= 0 && targetY < 8) {
            Piece targetPiece = pieces[targetX][targetY];

            if (currentPiece.type() == Piece.PieceType.KNIGHT) {
                // Knights can move without path clearance
                if (move.getMoveType() == Move.MoveType.MOVE && targetPiece.type() == Piece.PieceType.EMPTY) {
                    validMoves.add(move);
                } else if (move.getMoveType() == Move.MoveType.ATTACK && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite()) {
                    validMoves.add(move);
                }
            } else {
                // Other pieces require path clearance
                if (move.getMoveType() == Move.MoveType.MOVE && isPathClear(x, y, targetX, targetY) && targetPiece.type() == Piece.PieceType.EMPTY) {
                    validMoves.add(move);
                } else if (move.getMoveType() == Move.MoveType.ATTACK && isPathClear(x, y, targetX, targetY) && targetPiece.isNotEmpty() && targetPiece.isWhite() != currentPiece.isWhite()) {
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
    // Validate the move by checking if it's in the list of valid moves
    List<Move> validMoves = filterMoves(x, y);
    if (!validMoves.contains(move)) {
        return false; // Move is invalid
    }

    int targetX = move.getX();
    int targetY = move.getY();
    Piece movingPiece = pieces[x][y];

    // Check the move type
    if (move.getMoveType() == Move.MoveType.ATTACK) {
        // Attack move: capture the target piece
        pieces[targetX][targetY] = movingPiece; // Place the piece in the target position
        pieces[x][y] = new Empty(); // Clear the original position
    } else if (move.getMoveType() == Move.MoveType.MOVE) {
        // Normal move: no capture
        pieces[targetX][targetY] = movingPiece; // Move piece to the target position
        pieces[x][y] = new Empty(); // Clear the original position
    }

    return true; // Move was successful
}

public void game(boolean onePlayer, boolean colourPicked) {
    setupBoard();
    boolean playerTurn = colourPicked; // true if it's player's turn as white

    while (true) {
        System.out.println(this); // Display the current board

        if (isCheckmate(playerTurn)) {
            System.out.println((playerTurn ? "White" : "Black") + " is in checkmate. Game over!");
            break;
        }

        if (playerTurn) {
            System.out.println("Player's turn (" + (colourPicked ? "White" : "Black") + ")");
            makePlayerMove(colourPicked);
        } else if (onePlayer) {
            System.out.println("Computer's turn");
            makeComputerMove(!colourPicked); // Computer is the opposite color
        } else {
            System.out.println("Other player's turn (" + (!colourPicked ? "White" : "Black") + ")");
            makePlayerMove(!colourPicked);
        }

        playerTurn = !playerTurn; // Switch turns
    }
}
private void makePlayerMove(boolean isWhite) {
    System.out.print("Enter start position (e.g., 1 0): ");
    int startX = scanner.nextInt();
    int startY = scanner.nextInt();

    System.out.print("Enter end position (e.g., 2 0): ");
    int endX = scanner.nextInt();
    int endY = scanner.nextInt();

    Piece piece = pieces[startX][startY];
    if (piece.isWhite() != isWhite) {
        System.out.println("Invalid move: not your piece.");
        return;
    }

    List<Move> validMoves = filterMoves(startX, startY);
    Move move = new Move(endX, endY, getMoveType(startX, startY, endX, endY));

    if (validMoves.contains(move) && move(startX, startY, move)) {
        System.out.println("Move executed: " + startX + "," + startY + " to " + endX + "," + endY);
    } else {
        System.out.println("Invalid move. Try again.");
    }
}
private void makeComputerMove(boolean isWhite) {
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
                        System.out.println("Computer moves from " + i + "," + j + " to " + randomMove.getX() + "," + randomMove.getY());
                        return;
                    }
                }
            }
        }
    }
}
private boolean isCheckmate(boolean whiteTurn) {
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
    }
    return true;
}
private Move.MoveType getMoveType(int startX, int startY, int endX, int endY) {
    Piece targetPiece = pieces[endX][endY];
    return targetPiece.isNotEmpty() && targetPiece.isWhite() != pieces[startX][startY].isWhite() 
        ? Move.MoveType.ATTACK 
        : Move.MoveType.MOVE;
}

};
