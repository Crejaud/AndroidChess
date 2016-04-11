package jsutula.crejaud.androidchess.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.adapter.SquareAdapter;
import jsutula.crejaud.androidchess.model.Board;

public class ChessActivity extends AppCompatActivity {

    GridView chessboardGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chess);

        Board newBoard = new Board(this);

        chessboardGrid = (GridView) findViewById(R.id.chessboardGrid);

        SquareAdapter adapter = new SquareAdapter(this, newBoard.getBoard());

        chessboardGrid.setAdapter(adapter);

    }

    /**
     * Undo the previous move.
     * Can only undo 1 move in the past.
     * Once undo is pressed, it becomes deactivated until someone else moves.
     * @param view - undo button view
     */
    public void undo(View view) {

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
}
