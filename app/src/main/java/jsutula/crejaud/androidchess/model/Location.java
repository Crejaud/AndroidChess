package jsutula.crejaud.androidchess.model;

/**
 * Created by creja_000 on 4/13/2016.
 */
public class Location {

    private int file, rank;

    public Location(int f, int r) {
        file = f;
        rank = r;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
