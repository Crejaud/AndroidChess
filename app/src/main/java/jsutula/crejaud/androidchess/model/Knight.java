package jsutula.crejaud.androidchess.model;

import jsutula.crejaud.androidchess.R;

/**
 * Knight class, which extends the Piece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {

        if (Math.abs(initFile - finalFile) == 1 && Math.abs(initRank - finalRank) == 2 && board[finalFile][finalRank].getPiece() == null)
            return true;
        else if (Math.abs(initFile - finalFile) == 2 && Math.abs(initRank - finalRank) == 1 && board[finalFile][finalRank].getPiece() == null)
            return true;
        else if (Math.abs(initFile - finalFile) == 1 && Math.abs(initRank - finalRank) == 2
                && board[finalFile][finalRank].getPiece().isWhite() != board[initFile][initRank].getPiece().isWhite())
            return true;
        else if (Math.abs(initFile - finalFile) == 2 && Math.abs(initRank - finalRank) == 1
                && board[finalFile][finalRank].getPiece().isWhite() != board[initFile][initRank].getPiece().isWhite())
            return true;
        else
            return false;
    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_knight;
        else
            return R.drawable.ic_black_knight;
    }
}
