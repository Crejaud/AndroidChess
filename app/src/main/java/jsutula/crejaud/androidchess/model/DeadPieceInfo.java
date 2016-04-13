package jsutula.crejaud.androidchess.model;

/**
 * Created by creja_000 on 4/13/2016.
 */
public class DeadPieceInfo {
    private Piece piece;
    private int file;
    private int rank;

    public DeadPieceInfo(Piece p, int f, int r)
    {
        this.piece = p;
        this.file = f;
        this.rank = r;
    }

    public Piece getPiece(){ return piece;}
    public int getFile(){ return file;}
    public int getRank(){ return rank;}
}
