package jsutula.crejaud.androidchess.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * This class holds the necessary information to playback a game of chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class RecordedGame  implements Serializable {

    private List<RecordedMove> recordedMoves;
    private String title;
    private Calendar date;

    public RecordedGame(String title, Calendar date, List<RecordedMove> recordedMoves) {
        this.title = title;
        this.date = date;
        this.recordedMoves = recordedMoves;
    }

    public String getTitle(){
        return title;
    }

    public Calendar getDate(){
        return date;
    }
    public List<RecordedMove> getRecordedMoves() {
        return recordedMoves;
    }
    public String toString() {

        return title + ":\n     " + date.getTime().toString();
    }

}
