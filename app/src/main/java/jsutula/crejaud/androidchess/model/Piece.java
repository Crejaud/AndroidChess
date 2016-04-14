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
    private boolean enpassant;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
        hasMoved = false;
        enpassant = false;
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

    /**
     * Check to see if a move is valid
     * @param initFile - The starting file
     * @param initRank - The starting rank
     * @param finalFile - The ending file
     * @param finalRank - The ending rank
     * @param board - the chess board
     * @return true if the move is valid, else false
     */
    public abstract boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board);

    /**
     * Moves the piece from to the finalFile and finalRank and removes pieces as necessary
     * @param initFile - The starting file
     * @param initRank - The starting rank
     * @param finalFile - The ending file
     * @param finalRank - The ending rank
     * @param board - the chess board
     * @return Pair of the deleted piece and it's integer
     */
    public RecordedMove move(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {

        Piece initPiece = board[initFile][initRank].getPiece();

        Piece deadPiece = board[finalFile][finalRank].getPiece();

        RecordedMove recordedMove = new RecordedMove(hasMoved(), getEnpassant(),
                initFile, initRank, finalFile, finalRank,
                deadPiece, finalFile, finalRank);

        board[finalFile][finalRank].setPiece(initPiece);
        board[initFile][initRank].setPiece(null);

        setHasMoved(true); // set hasMoved to true

        return recordedMove;

    }

    /**
     * getEnpassant()
     * @return boolean
     *
     * Returns boolean indicating whether this piece can be captured by enpassant
     */
    public boolean getEnpassant()
    {
        return enpassant;
    }

    /**
     * setEnpassant()
     * @param e - new enpassant value
     */
    public void setEnpassant(boolean e) {
        enpassant = e;
    }

    /**
     * Depending on the piece, return the correct drawable id corresponding to the piece and color
     * @return the drawable id corresponding to the piece and color oombination
     */
    public abstract int getDrawable();
}
