package jsutula.crejaud.androidchess.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import jsutula.crejaud.androidchess.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
