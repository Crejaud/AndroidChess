package jsutula.crejaud.androidchess.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import java.util.List;

import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.adapter.PlaybackAdapter;
import jsutula.crejaud.androidchess.model.Game;
import jsutula.crejaud.androidchess.model.RecordedGame;
import jsutula.crejaud.androidchess.model.RecordedMove;

/**
 * The activity for viewing the playback of a selected chess game
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class PlaybackActivity extends AppCompatActivity {



    private final String WHITE_TURN = "White's Turn";
    private final String BLACK_TURN = "Black's Turn";

    private GridView chessboardGrid;
    private Game game;
    private TextView playerTurnTextView, gameInfo;
    private Button prevBtn, nextBtn;

    private boolean isResign, isDraw, isDrawConfirmed;

    private RecordedGame playbackGame = null;

    private List<RecordedMove> moves = null;
    private int moveIndex = -1;
    private RecordedMove current = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playback);

        playbackGame = (RecordedGame) getIntent().getSerializableExtra("SelectedGame");
        moves = playbackGame.getRecordedMoves();

        playerTurnTextView = (TextView) findViewById(R.id.playerTurnTextView);
        gameInfo = (TextView) findViewById(R.id.gameInfo);

        prevBtn = (Button) findViewById(R.id.prevBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);


        playerTurnTextView.setText(WHITE_TURN);
        gameInfo.setText(playbackGame.getTitle() + ":\n" +playbackGame.getDate().getTime().toString());

        prevBtn.setEnabled(false);

        isResign = false;
        isDraw = false;

        game = new Game(this);

        chessboardGrid = (GridView) findViewById(R.id.chessboardGrid);

        PlaybackAdapter adapter = new PlaybackAdapter(this, game);

       chessboardGrid.setAdapter(adapter);


    }

    /**previous
     * Show the previous move.
     * @param view - preview button view
     */
    public void previous(View view) {
       game.undo(game.getBoard());
        moveIndex--;
        changePlayerTurnText();
        if(moveIndex < 0) prevBtn.setEnabled(false);
        if(moveIndex < moves.size()-1) nextBtn.setEnabled(true);
    }

    /**next
     * Shows the next move in recorded game
     * @param view - next button view
     */
    public void next(View view) {

        moveIndex++;
        showMove();
        changePlayerTurnText();
        prevBtn.setEnabled(true);
        if(moveIndex+1 >=moves.size())nextBtn.setEnabled(false);
    }

    /**showMove
     * Moves the pieces in proper location to show move of current RecordedMove
     */
    public void showMove(){

        current = moves.get(moveIndex);
        game.move(current.getMovingInitFile(), current.getMovingInitRank(), current.getMovingFinalFile(), current.getMovingFinalRank());
        if(current.isInCheck() || current.isInCheckmate() || current.isInStalemate() || current.isResign() || current.isDraw()){
            System.out.println("It went where its supposed to");
            showAlert();
        }


    }


    public void changePlayerTurnText() {
        playerTurnTextView.setText(game.isWhitesMove() ? WHITE_TURN : BLACK_TURN);
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (current.isInCheck() && !current.isInCheckmate()) {
            builder.setTitle(R.string.check_title)
                    .setPositiveButton(R.string.check_ok_dialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
            Log.d("Display Alert", "Check!");
        }
        if (current.isInCheckmate() || current.isInStalemate() || current.isResign() || current.isDraw()) {
           prevBtn.setEnabled(false);


            String title;

            if (current.isInCheckmate())
                title = getResources().getString(R.string.checkmate_title) + " " + (!game.isWhitesMove() ? "White" : "Black") + " wins!";
            else if (game.isStalemate())
                title = getResources().getString(R.string.stalemate_title);
            else if (current.isResign())
                title = getResources().getString(R.string.resign_title) + " " + (!game.isWhitesMove() ? "White" : "Black") + " wins!";
            else
                title = getResources().getString(R.string.draw_title);

            builder.setTitle(title)
                    .setPositiveButton(R.string.check_ok_dialog, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();

        }

    }

}

