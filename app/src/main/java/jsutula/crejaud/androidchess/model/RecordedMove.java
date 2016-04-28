package jsutula.crejaud.androidchess.model;

import java.io.Serializable;

/**
 * This class records the move
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class RecordedMove implements Serializable {

    private Piece deletedPiece, promotedPiece;

    private boolean hasMoved, enpassant, isInCheck, isInCheckmate, isInStalemate, isResign, isDraw, isPromotion;

    private int movingInitFile, movingInitRank, movingFinalFile, movingFinalRank,
            deletedFile, deletedRank, castledInitFile, castledinitRank,
            castledFinalFile, castledFinalRank;

    public RecordedMove (boolean hasMoved, boolean enpassant, int mif, int mir, int mff, int mfr, Piece dp, int df, int dr) {
        this(hasMoved, enpassant, mif, mir,mff, mfr, dp, df, dr, -1, -1, -1, -1);
    }

    public RecordedMove (boolean hasMoved, boolean enpassant, int mif, int mir, int mff, int mfr, Piece dp, int df, int dr,
                         int cif, int cir, int cff, int cfr) {
        this.hasMoved = hasMoved;
        this.enpassant = enpassant;
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
        isInCheck = false;
        isInCheckmate = false;
        isInStalemate = false;
        isResign = false;
        isDraw = false;
        isPromotion = false;
        promotedPiece = null;
    }


    public Piece getPromotedPiece() {
        return promotedPiece;
    }

    public void setPromotedPiece(Piece promotedPiece) {
        this.promotedPiece = promotedPiece;
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

    public boolean isResign() {
        return isResign;
    }

    public void setResign(boolean resign) {
        isResign = resign;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public boolean isPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        isPromotion = promotion;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isInCheck() {
        return isInCheck;
    }

    public void setInCheck(boolean inCheck) {
        isInCheck = inCheck;
    }

    public boolean isInCheckmate() {
        return isInCheckmate;
    }

    public void setInCheckmate(boolean inCheckmate) {
        isInCheckmate = inCheckmate;
    }

    public boolean isInStalemate() {
        return isInStalemate;
    }

    public void setInStalemate(boolean inStalemate) {
        isInStalemate = inStalemate;
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
