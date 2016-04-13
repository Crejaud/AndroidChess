package jsutula.crejaud.androidchess.model;

/**
 * This class records the move
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class RecordedMove {

    private Piece deletedPiece;

    private boolean hasMoved;

    private int movingInitFile, movingInitRank, movingFinalFile, movingFinalRank,
            deletedFile, deletedRank, castledInitFile, castledinitRank,
            castledFinalFile, castledFinalRank;

    public RecordedMove (boolean hasMoved, int mif, int mir, int mff, int mfr, Piece dp, int df, int dr) {
        this(hasMoved, mif, mir,mff, mfr, dp, df, dr, -1, -1, -1, -1);
    }

    public RecordedMove (boolean hasMoved, int mif, int mir, int mff, int mfr, Piece dp, int df, int dr,
                         int cif, int cir, int cff, int cfr) {
        this.hasMoved = hasMoved;
        movingInitFile = mif;
        movingInitRank = mir;
        movingFinalFile = mff;
        movingFinalRank = mfr;
        deletedPiece = dp;
        deletedFile = df;
        deletedRank = dr;
        castledInitFile = cif;
        castledinitRank = cir;
        castledFinalFile = cff;
        castledFinalRank = cfr;
    }

    public boolean hasPreviouslyMoved() {
        return hasMoved;
    }

    public void setCastledInitFile(int castledInitFile) {
        this.castledInitFile = castledInitFile;
    }

    public void setCastledinitRank(int castledinitRank) {
        this.castledinitRank = castledinitRank;
    }

    public void setCastledFinalFile(int castledFinalFile) {
        this.castledFinalFile = castledFinalFile;
    }

    public void setCastledFinalRank(int castledFinalRank) {
        this.castledFinalRank = castledFinalRank;
    }

    public void setDeletedPiece(Piece deletedPiece) {
        this.deletedPiece = deletedPiece;
    }

    public void setDeletedFile(int deletedFile) {
        this.deletedFile = deletedFile;
    }

    public void setDeletedRank(int deletedRank) {
        this.deletedRank = deletedRank;
    }

    public Piece getDeletedPiece() {
        return deletedPiece;
    }

    public int getMovingInitFile() {
        return movingInitFile;
    }

    public int getMovingInitRank() {
        return movingInitRank;
    }

    public int getMovingFinalFile() {
        return movingFinalFile;
    }

    public int getDeletedFile() {
        return deletedFile;
    }

    public int getMovingFinalRank() {
        return movingFinalRank;
    }

    public int getDeletedRank() {
        return deletedRank;
    }

    public int getCastledInitFile() {
        return castledInitFile;
    }

    public int getCastledinitRank() {
        return castledinitRank;
    }

    public int getCastledFinalFile() {
        return castledFinalFile;
    }

    public int getCastledFinalRank() {
        return castledFinalRank;
    }
}
