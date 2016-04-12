package jsutula.crejaud.androidchess.model;

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
        return false;
    }

    @Override
    public void move(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {

    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_queen;
        else
            return R.drawable.ic_black_queen;
    }
}
