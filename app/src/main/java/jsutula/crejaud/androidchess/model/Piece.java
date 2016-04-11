package jsutula.crejaud.androidchess.model;

/**
 * The abstract class of Chess Pieces
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public abstract class Piece {

    private boolean isWhite;
    private boolean hasMoved;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        hasMoved = false;
    }

    /**
     * isWhite Accessor
     * @return true if piece is white, else false
     */
    public boolean isWhite() {
        return isWhite;
    }

    /**
     * hasMoved Accessor
     * @return true if piece has moved, else false
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * hasMoved Mutator
     * Changes the value of hasMoved to true, if piece has moved
     * Changes the value of hasMoved to false, if piece has not moved
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract boolean isValidMove(Square[][] board);

    public abstract Square[][] move(Square[][] board);

    public abstract int getDrawable();
}
