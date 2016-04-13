package jsutula.crejaud.androidchess.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;

import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.adapter.SquareAdapter;
import jsutula.crejaud.androidchess.model.Game;

/**
 * The Chess Activity Screen.
 * The activity for actually playing chess.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class ChessActivity extends AppCompatActivity {

    private final String WHITE_TURN = "White's Turn";
    private final String BLACK_TURN = "Black's Turn";

    private GridView chessboardGrid;
    private Game game;
    private TextView playerTurnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chess);

        playerTurnTextView = (TextView) findViewById(R.id.playerTurnTextView);

        playerTurnTextView.setText(WHITE_TURN);

        game = new Game(this);

        chessboardGrid = (GridView) findViewById(R.id.chessboardGrid);

        SquareAdapter adapter = new SquareAdapter(this, game);

        chessboardGrid.setAdapter(adapter);

    }

    /**
     * Undo the previous move.
     * Can only undo 1 move in the past.
     * Once undo is pressed, it becomes deactivated until someone else moves.
     * @param view - undo button view
     */
    public void undo(View view) {
        game.undo();
    }

    /**
     * Lets the AI choose a random valid move from the set of valid moves.
     * Calls the move method with this move.
     * @param view - ai button view
     */
    public void ai(View view) {

    }

    /**
     * The current player resigns and the game is over.
     * @param view - resign button view
     */
    public void resign(View view) {

    }

    /**
     * The current player asks for a draw.
     * The next player can choose to accept the draw or not.
     * @param view - draw button view
     */
    public void draw(View view) {

    }

    public void move(int initFile, int initRank, int finalFile, int finalRank) {
        if (game.isValidMove(initFile, initRank, finalFile, finalRank)) {
            game.move(initFile, initRank, finalFile, finalRank);
            changePlayerTurnText();
        }
    }

    public void changePlayerTurnText() {
        playerTurnTextView.setText(game.isWhitesMove() ? WHITE_TURN : BLACK_TURN);
    }
}
