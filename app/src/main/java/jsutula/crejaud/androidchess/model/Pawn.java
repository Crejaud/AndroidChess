package jsutula.crejaud.androidchess.model;

import jsutula.crejaud.androidchess.R;

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
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        return false;
    }

    @Override
    public void move(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {

    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_pawn;
        else
            return R.drawable.ic_black_pawn;
    }
}
