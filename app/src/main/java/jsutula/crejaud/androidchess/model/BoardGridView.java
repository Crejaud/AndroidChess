package jsutula.crejaud.androidchess.model;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.GridView;

/**
 * Board class, which holds the board in a game of chess.
 *
 * Essentially holds an instance of a game in chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class BoardGridView extends GridView {

    static final boolean WHITE = true;
    static final boolean BLACK = false;

    private Square[][] board;
    private boolean isDone,
        isStalemate,
        isResign,
        isWhiteWinner,
        isWhitesMove,
        isWhiteInCheck,
        isBlackInCheck,
        isInCheck,
        isDrawAvailable;

    public BoardGridView(Context context) {
        super(context);
        board = new Square[8][8];

        // Initialize first rank of Black's side
        board[0][7] = new Square(context, new Rook(BLACK), BLACK);
        board[7][7] = new Square(context, new Rook(BLACK), WHITE);
        board[1][7] = new Square(context, new Knight(BLACK), WHITE);
        board[6][7] = new Square(context, new Knight(BLACK), BLACK);
        board[2][7] = new Square(context, new Bishop(BLACK), BLACK);
        board[5][7] = new Square(context, new Bishop(BLACK), WHITE);
        board[3][7] = new Square(context, new Queen(BLACK), WHITE);
        board[4][7] = new Square(context, new King(BLACK), BLACK);

        // Initialize first rank of White's side
        board[0][0] = new Square(context, new Rook(WHITE), BLACK);
        board[7][0] = new Square(context, new Rook(WHITE), WHITE);
        board[1][0] = new Square(context, new Knight(WHITE), WHITE);
        board[6][0] = new Square(context, new Knight(WHITE), BLACK);
        board[2][0] = new Square(context, new Bishop(WHITE), BLACK);
        board[5][0] = new Square(context, new Bishop(WHITE), WHITE);
        board[3][0] = new Square(context, new Queen(WHITE), WHITE);
        board[4][0] = new Square(context, new King(WHITE), BLACK);

        int file, rank;

        // Initialize all black pawns
        rank = 6;
        for (file = 0; file < 8; file++) {
            if (file%2 == 0)
                board[file][rank] = new Square(context, new Pawn(BLACK), WHITE);
            else
                board[file][rank] = new Square(context, new Pawn(BLACK), BLACK);
        }

        // Initialize all empty squares
        for (rank = 5; rank >=2; rank--) {
            for (file = 0; file < 8; file++) {
                if ((file%2 == 0 && rank%2 == 0) || (file%2 != 0 && rank%2 != 0))
                    board[file][rank] = new Square(context, WHITE);
                else
                    board[file][rank] = new Square(context, BLACK);
            }
        }

        // Initialize all white pawns
        rank = 1;
        for (file = 0; file < 8; file++) {
            if (file%2 == 0)
                board[file][rank] = new Square(context, new Pawn(WHITE), BLACK);
            else
                board[file][rank] = new Square(context, new Pawn(WHITE), WHITE);
        }

        // Initialize all private booleans
        isDone = false;
        isStalemate = false;
        isResign = false;
        isWhiteWinner = false;
        isWhiteInCheck = false;
        isBlackInCheck = false;
        isInCheck = false;
        isDrawAvailable = false;
    }

    public Square[][] getBoard() {
        return board;
    }

}
