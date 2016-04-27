package jsutula.crejaud.androidchess.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.Comparator;

import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.model.RecordedGame;
import jsutula.crejaud.androidchess.model.RecordedGamesList;

/**
 * The activity for viewing the list of recorded games of Chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class RecordedGamesActivity extends AppCompatActivity {

    RecordedGamesList games;
    Comparator<RecordedGame> byDate;
    Comparator<RecordedGame> byTitle;
    ArrayAdapter<RecordedGame> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_games);

        //This code is only to be used to reset the list of games to be empty
       /* games = new RecordedGamesList();
        try {
            RecordedGamesList.write(this, games);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        try {
            games = RecordedGamesList.read(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        adapter = new ArrayAdapter<RecordedGame>(this, android.R.layout.simple_list_item_1, games.getGamesList());


        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.setSelection(position);
                view.setSelected(true);
            }
        });

        byDate = new Comparator<RecordedGame>() {
            @Override
            public int compare(RecordedGame object1, RecordedGame object2) {
                return object1.getDate().compareTo(object2.getDate());
            }
        };

        byTitle = new Comparator<RecordedGame>() {
            public int compare(RecordedGame object1, RecordedGame object2) {
                return object1.getTitle().compareTo(object2.getTitle());
            }
        };



        adapter.sort(byTitle);


    }

    /**
     * This method is called when the user clicks the
     * Sort By Date button in activity_recorded_games.xml.
     * .
     * @param view - sort by date button view
     */
    public void sortByDate(View view) {
        adapter.sort(byDate);
    }

    /**
     * This method is called when the user clicks the
     * Sort By Title button in activity_recorded_games.xml.
     * .
     * @param view - sort by title button view
     */
    public void sortByTitle(View view) {
        adapter.sort(byTitle);
    }

}
