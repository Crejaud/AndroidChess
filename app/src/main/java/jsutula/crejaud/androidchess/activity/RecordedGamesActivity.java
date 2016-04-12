package jsutula.crejaud.androidchess.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jsutula.crejaud.androidchess.R;

/**
 * The activity for viewing the list of recorded games of Chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class RecordedGamesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded_games);
    }
}
