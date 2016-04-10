package jsutula.crejaud.androidchess.model;

/**
 * Square class represents a spot on the board, which has a color and an occupying piece
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Square {

    private Piece occupyingPiece;
    private boolean isWhite;

    public Square(boolean isWhite) {
        this(null, isWhite);
    }

    public Square(Piece piece, boolean isWhite) {
        this.occupyingPiece = piece;
        this.isWhite = !isWhite;
    }

    /**
     * Sets the piece occupying the square with the parameter
     * @param piece - the new piece occupying the square
     */
    public void setPiece(Piece piece) {
        this.occupyingPiece = piece;
    }

    /**
     * occupyingPiece Accessor
     * @return the occupying piece
     */
    public Piece getPiece() {
        return occupyingPiece;
    }

    /**
     * Checks to see if there exists an occupyingPiece on the square
     * @return true if there is an occupying piece, else false
     */
    public boolean hasPiece() {
        return occupyingPiece != null;
    }

    /**
     * isWhite Accessor
     * @return true if square is white, false is square is black
     */
    public boolean isSquareWhite() {
        return isWhite;
    }
}
