package jsutula.crejaud.androidchess.model;

import jsutula.crejaud.androidchess.R;

/**
 * King class, which extends the LongMovementPiece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class King extends LongMovementPiece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(Square[][] board) {
        return false;
    }

    @Override
    public Square[][] move(Square[][] board) {
        return new Square[0][];
    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_king;
        else
            return R.drawable.ic_black_king;
    }
}
