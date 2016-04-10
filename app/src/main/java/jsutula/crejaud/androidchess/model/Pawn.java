package jsutula.crejaud.androidchess.model;

/**
 * Pawn class, which extends the Piece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Pawn extends Piece {

    private boolean willEnpassant, enpassant;

    public Pawn(boolean isWhite) {
        super(isWhite);
        enpassant = true;
        willEnpassant = false;
    }

    @Override
    public boolean isValidMove(Square[][] board) {
        return false;
    }

    @Override
    public Square[][] move(Square[][] board) {
        return new Square[0][];
    }
}
