import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Executors;

public class ChessGUI extends JFrame {
    private Board board;
    private JButton[][] boardButtons;
    private boolean singlePlayer;
    private boolean playerPlaysWhite;
    private boolean whiteTurn = true; // White always starts
    private int selectedX = -1, selectedY = -1;

    // The path to the pieces images
    private static final String PIECES_PATH = "./pieces/";

    public ChessGUI() {
        setupMainMenu();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setVisible(true);
    }

    private void setupMainMenu() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Chess Game");
        title.setFont(new Font("Serif", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton singlePlayerButton = new JButton("Single Player");
        JButton twoPlayerButton = new JButton("Two Player Hotseat");
        JButton exitButton = new JButton("Exit");

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(title);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        menuPanel.add(singlePlayerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(twoPlayerButton);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        menuPanel.add(exitButton);

        add(menuPanel);

        singlePlayerButton.addActionListener(e -> setupSinglePlayerMenu());
        twoPlayerButton.addActionListener(e -> startGame(false, true));
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void setupSinglePlayerMenu() {
        JPanel singlePlayerPanel = new JPanel();
        singlePlayerPanel.setLayout(new BoxLayout(singlePlayerPanel, BoxLayout.Y_AXIS));

        JLabel question = new JLabel("Do you want to play as White or Black?");
        question.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton whiteButton = new JButton("Play as White");
        JButton blackButton = new JButton("Play as Black");

        singlePlayerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        singlePlayerPanel.add(question);
        singlePlayerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        singlePlayerPanel.add(whiteButton);
        singlePlayerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        singlePlayerPanel.add(blackButton);

        setContentPane(singlePlayerPanel);
        revalidate();

        whiteButton.addActionListener(e -> startGame(true, true));
        blackButton.addActionListener(e -> startGame(true, false));
    }

    private void startGame(boolean singlePlayer, boolean playerPlaysWhite) {
        this.singlePlayer = singlePlayer;
        this.playerPlaysWhite = playerPlaysWhite;

        board = new Board();
        board.setupBoard();
        setupChessBoard();

        // Start the game in a separate thread for non-blocking execution
        Executors.newSingleThreadExecutor().submit(() -> Game(singlePlayer, playerPlaysWhite));
    }

    private void setupChessBoard() {
        JPanel chessBoardPanel = new JPanel(new GridLayout(8, 8));
        boardButtons = new JButton[8][8];

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JButton button = new JButton();
                button.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);
                final int fx = x, fy = y; // Required for lambda
                button.addActionListener(e -> handleSquareClick(fx, fy));
                boardButtons[x][y] = button;
                chessBoardPanel.add(button);
            }
        }

        updateBoard();
        setContentPane(chessBoardPanel);
        revalidate();
    }

    private void Game(boolean onePlayer, boolean playerIsWhite) {
        while (true) {
            if (board.isCheckmate(whiteTurn)) {
                JOptionPane.showMessageDialog(this, (whiteTurn ? "White" : "Black") + " is in checkmate. Game over!");
                break;
            }

            if (onePlayer) {
                if (whiteTurn == playerIsWhite) {
                    // Player's turn
                    waitForPlayerMove(true);
                } else {
                    // AI's turn
                    board.makeComputerMove(whiteTurn);
                    updateBoard();
                }
            } else {
                // Two-player mode
                waitForPlayerMove(whiteTurn);
            }

            whiteTurn = !whiteTurn; // Switch turns
        }
    }

    private synchronized void waitForPlayerMove(boolean isWhiteTurn) {
        try {
            while (true) {
                wait(); // Wait for player move to complete
                break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void handleSquareClick(int x, int y) {
        if (selectedX == -1 && selectedY == -1) {
            Piece piece = board.getPieces()[x][y];
            if (piece.isWhite() == whiteTurn && piece.isNotEmpty()) {
                selectedX = x;
                selectedY = y;
                highlightPossibleMoves(x, y);
            }
        } else {
            Move.MoveType moveType = board.getMoveType(selectedX, selectedY, x, y);
            Move move = new Move(x, y, moveType);

            if (board.filterMoves(selectedX, selectedY).contains(move) && board.move(selectedX, selectedY, move)) {
                updateBoard();
                resetSelection();
                synchronized (this) {
                    notify(); // Notify the game thread that the move is complete
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid move. Try again.");
                resetSelection();
            }
        }
    }

    private void highlightPossibleMoves(int x, int y) {
        List<Move> moves = board.filterMoves(x, y);
        for (Move move : moves) {
            int targetX = move.getX();
            int targetY = move.getY();
            boardButtons[targetX][targetY].setBackground(Color.GREEN);
        }
    }

    private void resetSelection() {
        selectedX = -1;
        selectedY = -1;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boardButtons[x][y].setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        }
    }

    private void updateBoard() {
        Piece[][] pieces = board.getPieces();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = pieces[x][y];
                if (piece.isNotEmpty()) {
                    String imageFileName = getImageFileName(piece);
                    ImageIcon pieceIcon = new ImageIcon(imageFileName);
                    boardButtons[x][y].setIcon(pieceIcon);
                } else {
                    boardButtons[x][y].setIcon(null); // No piece on this square
                }
            }
        }
    }

    private String getImageFileName(Piece piece) {
        String colorPrefix = piece.isWhite() ? "white" : "black";
        String pieceName = piece.type().toString().toLowerCase();
        return PIECES_PATH + colorPrefix + "-" + pieceName + ".png";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGUI::new);
    }
}
