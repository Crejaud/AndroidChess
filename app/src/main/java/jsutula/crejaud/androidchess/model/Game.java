package jsutula.crejaud.androidchess.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Game class, which holds the board in a game of chess.
 *
 * Essentially holds an instance of a game in chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Game {

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

    private List<RecordedMove> recordedMoves;

    public Game(Context context) {
        board = new Square[8][8];

        // Initialize first rank of Black's side
        board[0][7] = new Square(context, new Rook(BLACK), WHITE);
        board[7][7] = new Square(context, new Rook(BLACK), BLACK);
        board[1][7] = new Square(context, new Knight(BLACK), BLACK);
        board[6][7] = new Square(context, new Knight(BLACK), WHITE);
        board[2][7] = new Square(context, new Bishop(BLACK), WHITE);
        board[5][7] = new Square(context, new Bishop(BLACK), BLACK);
        board[3][7] = new Square(context, new Queen(BLACK), BLACK);
        board[4][7] = new Square(context, new King(BLACK), WHITE);

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
                board[file][rank] = new Square(context, new Pawn(BLACK), BLACK);
            else
                board[file][rank] = new Square(context, new Pawn(BLACK), WHITE);
        }

        // Initialize all empty squares
        for (rank = 5; rank >=2; rank--) {
            for (file = 0; file < 8; file++) {
                if ((file%2 == 0 && rank%2 == 0) || (file%2 != 0 && rank%2 != 0))
                    board[file][rank] = new Square(context, BLACK);
                else
                    board[file][rank] = new Square(context, WHITE);
            }
        }

        // Initialize all white pawns
        rank = 1;
        for (file = 0; file < 8; file++) {
            if (file%2 == 0)
                board[file][rank] = new Square(context, new Pawn(WHITE), WHITE);
            else
                board[file][rank] = new Square(context, new Pawn(WHITE), BLACK);
        }

        // Initialize all private booleans
        isWhitesMove = true;
        isDone = false;
        isStalemate = false;
        isResign = false;
        isWhiteWinner = false;
        isWhiteInCheck = false;
        isBlackInCheck = false;
        isInCheck = false;
        isDrawAvailable = false;

        recordedMoves = new ArrayList<RecordedMove>();
    }

    public Square[][] getBoard() {
        return board;
    }

    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank) {
        if (initFile == finalFile && initRank == finalRank) {
            Log.d("CHECK", "the file and rank are the same.");
            return false;
        }
        if (!board[initFile][initRank].hasPiece()) {
            Log.d("CHECK", "No init piece");
            return false;
        }
        if (board[initFile][initRank].getPiece().isWhite() != isWhitesMove()) {
            Log.d("CHECK", "Wrong init color, bud!");
            return false;
        }

        return board[initFile][initRank].getPiece().isValidMove(initFile, initRank, finalFile, finalRank, board);
    }

    public void move(int initFile, int initRank, int finalFile, int finalRank) {
        RecordedMove recordedMove = board[initFile][initRank].getPiece().move(initFile, initRank, finalFile, finalRank, board);

        recordedMoves.add(recordedMove);

        changePlayer();
    }

    public void undo() {

        RecordedMove recordedMove = recordedMoves.remove(recordedMoves.size() - 1);

        // move piece back to it's original square
        board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
                .setPiece(board[recordedMove.getMovingFinalFile()][recordedMove.getMovingFinalRank()].getPiece());

        // remove the duplicate piece
        board[recordedMove.getMovingFinalFile()][recordedMove.getMovingFinalRank()].setPiece(null);

        // revert piece back to it's previous hasMoved value
        board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
                .getPiece().setHasMoved(recordedMove.hasPreviouslyMoved());

        // place recovered piece
        board[recordedMove.getDeletedFile()][recordedMove.getDeletedRank()]
                .setPiece(recordedMove.getDeletedPiece());

        // check for castling
        if (recordedMove.getCastledInitFile() != -1) {
            // revert the rook
            board[recordedMove.getCastledInitFile()][recordedMove.getCastledinitRank()]
                    .setPiece(board[recordedMove.getCastledFinalFile()][recordedMove.getCastledFinalRank()].getPiece());

            // remove duplicate piece
            board[recordedMove.getCastledFinalFile()][recordedMove.getCastledFinalRank()].setPiece(null);

            // revert piece back to a hasMoved value of false
            board[recordedMove.getCastledInitFile()][recordedMove.getCastledinitRank()].getPiece().setHasMoved(false);
        }

        // go back to previous player's turn
        changePlayer();

    }

    public boolean isWhitesMove() {
        return isWhitesMove;
    }

    public void changePlayer() {
        isWhitesMove = !isWhitesMove;
    }

}
