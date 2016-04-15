package jsutula.crejaud.androidchess.model;

import android.content.Context;

import jsutula.crejaud.androidchess.R;

/**
 * Queen class, which extends the LongMovementPiece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Queen extends LongMovementPiece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        //check if valid long movement
        if ((Math.abs(initFile - finalFile) == Math.abs(initRank - finalRank)
                || ((initFile == finalFile && initRank != finalRank) || (initFile != finalFile && initRank == finalRank)))
                && !hasPiecesInbetween(initFile, initRank, finalFile, finalRank, board)) {
            if (board[finalFile][finalRank].getPiece() == null
                    || board[finalFile][finalRank].getPiece().isWhite() != isWhite()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_queen;
        else
            return R.drawable.ic_black_queen;
    }
}
