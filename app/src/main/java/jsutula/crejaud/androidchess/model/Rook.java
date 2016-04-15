package jsutula.crejaud.androidchess.model;

import android.content.Context;

import jsutula.crejaud.androidchess.R;

/**
 * Rook class, which extends the LongMovementPiece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Rook extends LongMovementPiece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        if ( ((initFile == finalFile && initRank != finalRank) || (initFile != finalFile && initRank == finalRank))
                && !hasPiecesInbetween(initFile, initRank, finalFile, finalRank, board)) {
            if (board[finalFile][finalRank].getPiece() == null)
                return true;
            if (board[finalFile][finalRank].getPiece().isWhite() != board[initFile][initRank].getPiece().isWhite())
                return true;
        }

        return false;
    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_rook;
        else
            return R.drawable.ic_black_rook;
    }
}
