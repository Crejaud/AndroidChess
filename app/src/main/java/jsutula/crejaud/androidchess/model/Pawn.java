package jsutula.crejaud.androidchess.model;

import android.util.Log;

import jsutula.crejaud.androidchess.R;

/**
 * Pawn class, which extends the Piece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Pawn extends Piece {


    public Pawn(boolean isWhite) {
        super(isWhite);
        setEnpassant(true);
    }

    @Override
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {

        // If pawn is going straight
        if (initFile == finalFile) {
            if (isWhite() && initRank+1 == finalRank && board[finalFile][finalRank].getPiece() == null){
                setEnpassant(false);
                return true;
            }

            // is white and it's the first turn, then can move up to 2 spots
            if (isWhite() && initRank == 1 && initRank+2 == finalRank && board[finalFile][finalRank].getPiece() == null){
                //	System.out.println("Is now enpassant eligible");
                setEnpassant(true);
                return true;
            }

            if (!isWhite() && initRank-1 == finalRank && board[finalFile][finalRank].getPiece() == null)
            {
                setEnpassant(false);
                return true;
            }

            // is black and it's the first turn, then can move up to 2 spots
            if (!isWhite() && initRank == 6 && initRank-2 == finalRank && board[finalFile][finalRank].getPiece() == null){
                //System.out.println("Is now enpassant eligible");
                setEnpassant(true);
                return true;
            }
        }

        //If pawn is going diagonal into spot of opponent
        if (isWhite() && initRank+1 == finalRank && initFile+1 == finalFile && board[initFile+1][initRank+1].getPiece() != null && !board[initFile+1][initRank+1].getPiece().isWhite())
        {
            setEnpassant(false);
            return true;
        }
        if (isWhite() && initRank+1 == finalRank && initFile-1 == finalFile && board[initFile-1][initRank+1].getPiece() != null && !board[initFile-1][initRank+1].getPiece().isWhite())
        {
            setEnpassant(false);
            return true;
        }

        if (!isWhite() && initRank-1 == finalRank && initFile+1 == finalFile && board[initFile+1][initRank-1].getPiece() != null && board[initFile+1][initRank-1].getPiece().isWhite())
        {
            setEnpassant(false);
            return true;
        }
        if (!isWhite() && initRank-1 == finalRank && initFile-1 == finalFile && board[initFile-1][initRank-1].getPiece() != null && board[initFile-1][initRank-1].getPiece().isWhite())
        {
            setEnpassant(false);
            return true;
        }

        // If pawn is going diagonal for enpassant
        if (shouldEnpassant(initFile, initRank, finalFile, finalRank, board)) {
            setEnpassant(false);
            //Log.d("piece will enpass", shouldEnpassant(initFile, initRank, finalFile, finalRank, board) + "");
            return true;
        }
        //If pawn is going diagonal for enpassant
//        if (isWhite() && initRank == 4 && finalRank == 5 && initFile+1 == finalFile && board[initFile+1][initRank].getPiece() != null && !board[initFile+1][initRank].getPiece().isWhite() && canEnpassant(initFile+1, initRank,board) && board[finalFile][finalRank].getPiece() == null)
//        {
//            setEnpassant(false);
//            willEnpassant = true;
//            return true;
//        }
//        if (isWhite() && initRank == 4 && finalRank == 5 && initFile-1 == finalFile && board[initFile-1][initRank].getPiece() != null && !board[initFile-1][initRank].getPiece().isWhite() && canEnpassant(initFile-1, initRank,board) && board[finalFile][finalRank].getPiece() == null)
//        {
//            setEnpassant(false);
//            willEnpassant = true;
//            return true;
//        }
//        if (!isWhite() && initRank == 3 && finalRank == 2&& initFile+1 == finalFile && board[initFile+1][initRank].getPiece() != null && board[initFile+1][initRank].getPiece().isWhite() && canEnpassant(initFile+1, initRank,board) && board[finalFile][finalRank].getPiece() == null)
//        {
//            setEnpassant(false);
//            willEnpassant = true;
//            return true;
//        }
//        if (!isWhite() && initRank == 3 && finalRank == 2 && initFile-1 == finalFile && board[initFile-1][initRank].getPiece() != null && board[initFile-1][initRank].getPiece().isWhite() && canEnpassant(initFile-1, initRank, board) && board[finalFile][finalRank].getPiece() == null)
//        {
//            setEnpassant(false);
//            willEnpassant = true;
//            return true;
//        }

        return false;
    }

    @Override
    public RecordedMove move(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        RecordedMove recordedMove = new RecordedMove(hasMoved(), getEnpassant(),
                initFile, initRank, finalFile, finalRank,
                board[finalFile][finalRank].getPiece(), finalFile, finalRank);

        boolean willEnpassant = shouldEnpassant(initFile, initRank, finalFile, finalRank, board);

        Piece initPiece = board[initFile][initRank].getPiece();

        board[finalFile][finalRank].setPiece(initPiece);
        board[initFile][initRank].setPiece(null);

        //Log.d("piece will enpass", willEnpassant + "");

        //if pawn is going diagonal for enpassant remove piece it is passing
        if (initFile != finalFile && willEnpassant) {

            recordedMove.setDeletedRank(initRank);

            if (isWhite() && initRank+1 == finalRank && initFile+1 == finalFile) {
                recordedMove.setDeletedPiece(board[initFile + 1][initRank].getPiece());
                recordedMove.setDeletedFile(initFile + 1);
                board[initFile + 1][initRank].setPiece(null);
            }
            if (isWhite() && initRank+1 == finalRank && initFile-1 == finalFile) {
                recordedMove.setDeletedPiece(board[initFile - 1][initRank].getPiece());
                recordedMove.setDeletedFile(initFile - 1);
                board[initFile - 1][initRank].setPiece(null);
            }

            if (!isWhite() && initRank-1 == finalRank && initFile+1 == finalFile) {
                recordedMove.setDeletedPiece(board[initFile + 1][initRank].getPiece());
                recordedMove.setDeletedFile(initFile + 1);
                board[initFile + 1][initRank].setPiece(null);
            }
            if (!isWhite() && initRank-1 == finalRank && initFile-1 == finalFile) {
                recordedMove.setDeletedPiece(board[initFile - 1][initRank].getPiece());
                recordedMove.setDeletedFile(initFile - 1);
                board[initFile - 1][initRank].setPiece(null);
            }
        }

        // check for promotion
//        if (isWhite && finalRank == 7)
//            promotion(input, finalFile, finalRank, board, "white");
//        if (!isWhite && finalRank == 0)
//            promotion(input, finalFile, finalRank, board, "black");

        setHasMoved(true); // set hasMoved to true

        return recordedMove;
    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_pawn;
        else
            return R.drawable.ic_black_pawn;
    }

    public boolean shouldEnpassant(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        if (isWhite() && initRank == 4 && finalRank == 5 && initFile+1 == finalFile && board[initFile+1][initRank].getPiece() != null && !board[initFile+1][initRank].getPiece().isWhite() && canEnpassant(initFile+1, initRank,board) && board[finalFile][finalRank].getPiece() == null)
        {
            return true;
        }
        if (isWhite() && initRank == 4 && finalRank == 5 && initFile-1 == finalFile && board[initFile-1][initRank].getPiece() != null && !board[initFile-1][initRank].getPiece().isWhite() && canEnpassant(initFile-1, initRank,board) && board[finalFile][finalRank].getPiece() == null)
        {
            return true;
        }
        if (!isWhite() && initRank == 3 && finalRank == 2&& initFile+1 == finalFile && board[initFile+1][initRank].getPiece() != null && board[initFile+1][initRank].getPiece().isWhite() && canEnpassant(initFile+1, initRank,board) && board[finalFile][finalRank].getPiece() == null)
        {
            return true;
        }
        if (!isWhite() && initRank == 3 && finalRank == 2 && initFile-1 == finalFile && board[initFile-1][initRank].getPiece() != null && board[initFile-1][initRank].getPiece().isWhite() && canEnpassant(initFile-1, initRank, board) && board[finalFile][finalRank].getPiece() == null)
        {
            return true;
        }
        return false;
    }

    /**
     * canEnpassant
     * Indicates whether pawn can perform enpassant
     * @param file
     * @param rank
     * @param board
     * @return boolean
     */
    public boolean canEnpassant(int file, int rank, Square[][] board)
    {
        boolean wouldBeUnderAttack = false;
        boolean isWhite = board[file][rank].getPiece().isWhite();

        //Check for different locations to see if piece would have been under attack if it only moved one space up instead of two
        if(isWhite && rank!=0 && file!=7 && board[file + 1][rank].getPiece()!= null && !board[file + 1][rank].getPiece().isWhite()){
            wouldBeUnderAttack = true;
        }
        else if(isWhite && rank!=0 && file!=0 && board[file - 1][rank].getPiece()!= null && !board[file -1][rank].getPiece().isWhite()){
            wouldBeUnderAttack = true;
        }
        else if(!isWhite && rank!=7 && file!=7 && board[file + 1][rank].getPiece()!= null && board[file+1][rank].getPiece().isWhite()){
            wouldBeUnderAttack = true;
        }
        else if(!isWhite && rank!=7 && file!=0 &&board[file - 1][rank].getPiece()!= null && board[file-1][rank].getPiece().isWhite()){
            wouldBeUnderAttack = true;
        }

        return board[file][rank].getPiece().getEnpassant() && wouldBeUnderAttack;
    }
}
