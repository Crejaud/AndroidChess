package jsutula.crejaud.androidchess.model;

import jsutula.crejaud.androidchess.R;

/**
 * Bishop class, which extends the LongMovementPiece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Bishop extends LongMovementPiece {

    public Bishop(boolean isWhite) {
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
        return 0;
    }
}
