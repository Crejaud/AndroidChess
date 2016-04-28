package jsutula.crejaud.androidchess.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.model.RecordedGamesList;


/**
 * Main Activity for Chess.
 * The Home Screen.
 * Has a button to play a new game of Chess.
 * Has a button to view the list of recorded games of Chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class MainActivity extends AppCompatActivity {

    RecordedGamesList games;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        Log.d("Height of titlebar", result +"");

    }

    /**
     * This method is called when the user clicks the
     * play chess button in activity_main.xml.
     * Go to the play chess activity.
     * @param view - play chess button view
     */
    public void playChess(View view) {
        Intent i = new Intent(this, ChessActivity.class);
        startActivity(i);
    }

    /**
     * This method is called when the user clicks the
     * recorded games button in activity_main.xml.
     * Go to the recorded games activity.
     * @param view - recorded games button view
     */
    public void viewRecordedGames(View view) {
        Intent i = new Intent(this, RecordedGamesActivity.class);
        startActivity(i);
    }
}
