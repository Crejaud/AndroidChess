package jsutula.crejaud.androidchess.model;


import android.content.Context;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores and provides functionality to maintain a list of recorded games
 *
 * This is core of the serialization. The list of recorded games is serialized into the games.dat file in the dat folder directory
 *
 * @author      Corentin Rejaud
 * @author		Julia Sutula
 */

public class RecordedGamesList implements Serializable {

    private static final long serialVersionUID = 9221355046218690511L;
   // private File storeDir = null;
    public static final String storeFile = "games";

    private List<RecordedGame> games;


    /**
     * RecordedGamesList constructor
     */
    public RecordedGamesList() {
        games = new ArrayList<RecordedGame>();
    }


    /**
     * getGamesList
     * Returns the overall list of Recorded games for the application
     *
     * @return the overall list of recorded games for the application
     */
    public List<RecordedGame> getGamesList()
    {
        return games;
    }

    /**
     * addGameToList
     * Adds indicated game to the overall list of games
     *
     * @param r - the game to add to the overall list of games
     */
    public void addGameToList(RecordedGame r)
    {
        games.add(r);
    }

    /**removeGameFromList
     * Remove indicated game from the overall list of games
     *
     * @param r - the game to remove from the overall list of games
     */
    public void removeGameFromList(RecordedGame r)
    {
        games.remove(r);
    }


    /**
     * gameTitleExists
     * Checks if a specific name for a game already exists
     *
     * @param r - the name to check for in the from the overall list of recorded games
     * @return boolean - return true if name is in the list, false if it isnt
     */
    public boolean gameTitleExists(String r)
    {
        for(RecordedGame g: games)
        {
            if (g.getTitle().equals(r))
                return true;
        }
        return false;
    }

    /**
     * getUserByUsername
     * @param r	the recorded games title
     * @return game with the same title
     */
    public RecordedGame getGameByTitle(String r)
    {
        for(RecordedGame g: games)
        {
            if (g.getTitle().equals(r))
                return g;
        }
        return null;
    }

    /**
     * toString
     * Will display the Titles of all recorded games
     *
     * @return String - all recorded games
     */
    public String toString() {
        if (games == null)
            return "No Recorded Games Available";
        String output = "";
        for(RecordedGame u : games)
        {
            output += u.getTitle() + " ";
        }
        return output;
    }

    /**
     * Read the games.bin file and return the RecordedGamesList model containing the list of all games.
     * @return	return the RecordedGamesList model of all games
     * @throws IOException		Exception for serialization
     * @throws ClassNotFoundException		Exception for serialization
     */
    public static RecordedGamesList read(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(storeFile);
        ObjectInputStream ois = new ObjectInputStream(fis);
        RecordedGamesList glist = (RecordedGamesList) ois.readObject();
        ois.close();
        return glist;
    }

    /**
     * Given the RecordedGamesList model, write this data into games.dat, overwriting anything on there.
     * @param glist	The RecordedGamesList model to write with
     * @throws IOException		Exception for serialization
     */
    public static void write (Context context, RecordedGamesList glist) throws IOException {
        FileOutputStream fos = context.openFileOutput(storeFile, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(glist);
        oos.close();
        fos.close();
    }


}

