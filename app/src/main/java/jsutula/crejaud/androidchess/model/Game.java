package jsutula.crejaud.androidchess.model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import jsutula.crejaud.androidchess.R;

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

    private Square[][] clone;

    private boolean isDone,
        isStalemate,
        isCheckmate,
        isResign,
        isWhiteWinner,
        isWhitesMove,
        isWhiteInCheck,
        isBlackInCheck,
        isInCheck,
        isDrawAvailable;

    private Location whiteKingLocation, blackKingLocation;

    private List<RecordedMove> recordedMoves;

    private Context context;

    public Game(Context context) {
        board = new Square[8][8];

        this.context = context;

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
        isCheckmate = false;
        isResign = false;
        isWhiteWinner = false;
        isWhiteInCheck = false;
        isBlackInCheck = false;
        isInCheck = false;
        isDrawAvailable = false;

        recordedMoves = new ArrayList<RecordedMove>();

        whiteKingLocation = new Location(4,0);
        blackKingLocation = new Location(4,7);

        clone = createClone(board);
    }

    public Square[][] getBoard() {
        return board;
    }

    /**
     * Check to see if the move is a valid piece move (from within piece class)
     * @param initFile
     * @param initRank
     * @param finalFile
     * @param finalRank
     * @param gameBoard - can send either real board or clone board (for testing valid move)
     * @return true if it's a valid piece move, else false
     */
    public boolean isValidPieceMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] gameBoard) {
        if (initFile == finalFile && initRank == finalRank) {
            //Log.d("CHECK", "the file and rank are the same.");
            return false;
        }
        if (!gameBoard[initFile][initRank].hasPiece()) {
            //Log.d("CHECK", "No init piece");
            return false;
        }
        if (gameBoard[initFile][initRank].getPiece().isWhite() != isWhitesMove()) {
            //Log.d("CHECK", "Wrong init color, bud!");
            return false;
        }

        return gameBoard[initFile][initRank].getPiece().isValidMove(initFile, initRank, finalFile, finalRank, gameBoard);
    }

    /**
     * Check if move is truly valid (both valid piece move and does not bring own king in check)
     * @param initFile
     * @param initRank
     * @param finalFile
     * @param finalRank
     * @return true if it is a truly valid move, else false
     */
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank) {

        // if it is a valid piece move, then test to see if it brings own king into check
        if (isValidPieceMove(initFile, initRank, finalFile, finalRank, board)) {
            pieceMove(initFile, initRank, finalFile, finalRank, clone);
            // isWhitesMove is now changed. It is now the enemy, so check to see if original king is in check (invert isWhitesMove)
            boolean kingIsInCheck = isKingInCheck(clone, !isWhitesMove());
            undo(clone);
            // undo sets isWhitesMove back to original
            return !kingIsInCheck;
        }

        return false;
    }

    /**
     * Move the piece, and set isWhitesMove to the other side
     * @param initFile
     * @param initRank
     * @param finalFile
     * @param finalRank
     * @param gameBoard
     */
    public void pieceMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] gameBoard) {
        RecordedMove recordedMove = gameBoard[initFile][initRank].getPiece().move(initFile, initRank, finalFile, finalRank, gameBoard);

        //Log.d("TEST", board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
        //        .hasPiece() + "");

        recordedMoves.add(recordedMove);

        if (gameBoard[finalFile][finalRank].getPiece() instanceof King) {
            if (gameBoard[finalFile][finalRank].getPiece().isWhite()) {
                whiteKingLocation.setFile(finalFile);
                whiteKingLocation.setRank(finalRank);
            } else {
                blackKingLocation.setFile(finalFile);
                blackKingLocation.setRank(finalRank);
            }
        }

        if (gameBoard[finalFile][finalRank].getPiece() instanceof Pawn) {
            // check for promotion
            if (isWhitesMove() && finalRank == 7) {
                promotion(initFile, initRank, finalFile, finalRank, gameBoard, recordedMove);
            }
            if (!isWhitesMove() && finalRank == 0) {
                promotion(initFile, initRank, finalFile, finalRank, gameBoard, recordedMove);
            }
        }

        changePlayer();
    }

    /**
     * Move the piece, then test for check
     * @param initFile
     * @param initRank
     * @param finalFile
     * @param finalRank
     */
    public void move(int initFile, int initRank, int finalFile, int finalRank) {
        pieceMove(initFile, initRank, finalFile, finalRank, board);

        createClone();

        //test for check
        testCheck();

        recordedMoves.get(recordedMoves.size() - 1).setInCheck(isInCheck);
        recordedMoves.get(recordedMoves.size() - 1).setInCheckmate(isCheckmate);
        recordedMoves.get(recordedMoves.size() - 1).setInStalemate(isStalemate);
    }

    public void undo(Square[][] board) {

        RecordedMove recordedMove = recordedMoves.remove(recordedMoves.size() - 1);

        //Log.d("TEST", board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
        //        .hasPiece() + "");

        if (recordedMove.isPromotion())
            board[recordedMove.getMovingFinalFile()][recordedMove.getMovingFinalRank()]
                    .setPiece(recordedMove.getDeletedPiece());

        // move piece back to it's original square
        board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
                .setPiece(board[recordedMove.getMovingFinalFile()][recordedMove.getMovingFinalRank()].getPiece());

        //Log.d("TEST", board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
        //        .hasPiece() + "");

        // if it's a king, change location
        if (board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()].getPiece() instanceof King) {
            if (board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()].getPiece().isWhite()) {
                whiteKingLocation.setFile(recordedMove.getMovingInitFile());
                whiteKingLocation.setRank(recordedMove.getMovingInitRank());
            }
            else {
                blackKingLocation.setFile(recordedMove.getMovingInitFile());
                blackKingLocation.setRank(recordedMove.getMovingInitRank());
            }
        }

        // remove the duplicate piece
        board[recordedMove.getMovingFinalFile()][recordedMove.getMovingFinalRank()].setPiece(null);

        //Log.d("TEST", board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
        //        .hasPiece() + "");

        if (!recordedMove.isPromotion()) {

            // revert piece back to it's previous hasMoved value
            board[recordedMove.getMovingInitFile()][recordedMove.getMovingInitRank()]
                    .getPiece().setHasMoved(recordedMove.hasPreviouslyMoved());

            // place recovered piece
            board[recordedMove.getDeletedFile()][recordedMove.getDeletedRank()]
                    .setPiece(recordedMove.getDeletedPiece());
        }

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

    public List<Pair<Location, Location>> getAllValidMoves() {
        List<Pair<Location, Location>> allMoves = new ArrayList<Pair<Location, Location>>();
        Pair<Location, Location> move = null;

        for (int initFile = 0; initFile < 8; initFile++) {
            for (int initRank = 0; initRank < 8; initRank++) {
                for (int finalFile = 0; finalFile < 8; finalFile++) {
                    for (int finalRank = 0; finalRank < 8; finalRank++) {
                        if (isValidMove(initFile, initRank, finalFile, finalRank)) {
                            move = new Pair<Location,Location>(new Location(initFile, initRank), new Location(finalFile, finalRank));
                            allMoves.add(move);
                        }
                    }
                }
            }
        }

        return allMoves;
    }

    /**
     * Check to see if king is in check.
     * It's in check if there exists a valid piece move towards the enemy king (isKingWhite)
     * @param gameBoard
     * @param isKingWhite
     * @return
     */
    public boolean isKingInCheck(Square[][] gameBoard, boolean isKingWhite) {
        int kingFile, kingRank;
        if (isKingWhite) {
            kingFile = whiteKingLocation.getFile();
            kingRank = whiteKingLocation.getRank();
        }
        else {
            kingFile = blackKingLocation.getFile();
            kingRank = blackKingLocation.getRank();
        }

        for (int initFile = 0; initFile < 8; initFile++) {
            for (int initRank = 0; initRank < 8; initRank++) {
                if (isValidPieceMove(initFile, initRank, kingFile, kingRank, gameBoard)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * test to see if
     */
    public void testCheck() {
        // temporarily change
       // Log.d("test for check on", isWhitesMove() ? "white":"black");
        changePlayer();
        if (isKingInCheck(board, !isWhitesMove())) {
            setCheck();
        }
        else {
            unsetCheck();
        }
    }

    public void setCheck() {
        isInCheck = true;
      //  Log.d("Check", isWhitesMove() ? "black" : "white" + "is in check!");
        changePlayer();
        testForCheckmate();
    }

    public void testForCheckmate() {
        List<Pair<Location, Location>> allMoves = getAllValidMoves();
        //Log.d("Testing checkmate on", isWhitesMove() ? "white" : "black");
       // Log.d("Testing for Checkmate", "Legit Moves: " + allMoves.size() + "");
        isCheckmate = false;
        if (allMoves.isEmpty()) {
            isCheckmate = true;
         //   Log.d("Checkmate", isWhitesMove() ? "white" : "black" + "is in checkmate!");
        }
    }

    public void testForStalemate() {
        List<Pair<Location, Location>> allMoves = getAllValidMoves();
      //  Log.d("Testing stalemate on", isWhitesMove() ? "white" : "black");
        //Log.d("Testing for Stalemate", "Legit Moves: " + allMoves.size() + "");
        isStalemate = false;
        if (allMoves.isEmpty()) {
            isStalemate = true;
          //  Log.d("Stalemate", isWhitesMove() ? "white" : "black" + "is in stalemate!");
        }
    }

    public void unsetCheck() {
        isInCheck = false;
        changePlayer();
        testForStalemate();
    }

    public boolean isWhitesMove() {
        return isWhitesMove;
    }

    public void changePlayer() {
        isWhitesMove = !isWhitesMove;
    }

    public void createClone() {
        clone = createClone(board);
    }


    /**
     * createClone
     * @param board
     * @return a deep clone of board, for checking moves
     */
    public Square[][] createClone(Square[][] board) {
        Square[][] clone = new Square[8][8];

        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) {
                try {
                    clone[f][r] = (Square) board[f][r].clone();
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return clone;
    }

    public void ai() {
        List<Pair<Location, Location>> allMoves = getAllValidMoves();

        //Log.d("All Legit Moves", allMoves.size() + "");

        if (allMoves.size() == 0)
            return;

        int randomNum = (int) (Math.random() * allMoves.size());

       // Log.d("Random Number", randomNum + "");

        Pair<Location, Location> randomMove = allMoves.get(randomNum);
        move(randomMove.first.getFile(), randomMove.first.getRank(),
                randomMove.second.getFile(), randomMove.second.getRank());
    }

    public boolean isCheck() {
        return isInCheck;
    }

    public boolean isCheckmate() {
        return isCheckmate;
    }

    public boolean isStalemate() {
        return isStalemate;
    }

    public List<RecordedMove> getRecordedMoves() {
        return recordedMoves;
    }

    public void promotion(int initFile, int initRank, final int finalFile, final int finalRank, Square[][] gameBoard, final RecordedMove recordedMove) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final int[] pos = new int[1];

        Piece deletedPawn = gameBoard[finalFile][finalRank].getPiece();

        if (clone.equals(gameBoard)) {
            gameBoard[finalFile][finalRank].setPiece(new Queen(!isWhitesMove()));
            gameBoard[finalFile][finalRank].getPiece().setHasMoved(true);
            recordedMove.setPromotedPiece(gameBoard[finalFile][finalRank].getPiece());
        }
        else {

            builder.setTitle(R.string.promotion_title)
                    //.setMessage(R.string.promotion_message)
                    .setItems(R.array.promotion_array, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            pos[0] = which;
                            dialog.dismiss();
                            promote(finalFile, finalRank, pos, recordedMove);
                        }
                    })
                    .setCancelable(false)
                    .show();

        }

        recordedMove.setDeletedPiece(deletedPawn);
        recordedMove.setDeletedFile(initFile);
        recordedMove.setDeletedRank(initRank);
        recordedMove.setPromotion(true);
    }

    public void promote(int f, int r, int[] pos, RecordedMove recordedMove) {

        switch(pos[0]) {
            case 0:
                board[f][r].setPiece(new Queen(!isWhitesMove()));
                board[f][r].getPiece().setHasMoved(true);
                break;
            case 1:
                board[f][r].setPiece(new Knight(!isWhitesMove()));
                board[f][r].getPiece().setHasMoved(true);
                break;
            case 2:
                board[f][r].setPiece(new Rook(!isWhitesMove()));
                board[f][r].getPiece().setHasMoved(true);
                break;
            case 3:
                board[f][r].setPiece(new Bishop(!isWhitesMove()));
                board[f][r].getPiece().setHasMoved(true);
                break;
            default:
                break;
        }

        recordedMove.setPromotedPiece(board[f][r].getPiece());

    }

}
