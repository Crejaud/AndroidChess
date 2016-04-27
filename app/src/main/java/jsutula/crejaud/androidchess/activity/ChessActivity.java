package jsutula.crejaud.androidchess.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;

import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.adapter.SquareAdapter;
import jsutula.crejaud.androidchess.model.Game;
import jsutula.crejaud.androidchess.model.RecordedGame;
import jsutula.crejaud.androidchess.model.RecordedGamesList;

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
    private Button undoBtn, aiBtn, resignBtn, drawBtn;

    private boolean isResign, isDraw, isDrawConfirmed;

    private RecordedGame recordedGame = null;

    RecordedGamesList games = new RecordedGamesList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


      try
       {
           games = RecordedGamesList.read(this);
       }
       catch(Exception e)
        {
            e.printStackTrace();
        }

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chess);

        playerTurnTextView = (TextView) findViewById(R.id.playerTurnTextView);
        undoBtn = (Button) findViewById(R.id.undoBtn);
        aiBtn = (Button) findViewById(R.id.aiBtn);
        resignBtn = (Button) findViewById(R.id.resignBtn);
        drawBtn = (Button) findViewById(R.id.drawBtn);

        playerTurnTextView.setText(WHITE_TURN);

        undoBtn.setEnabled(false);

        isResign = false;
        isDraw = false;

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
        game.undo(game.getBoard());
        changePlayerTurnText();
        game.createClone();
        undoBtn.setEnabled(false);
    }

    /**
     * Lets the AI choose a random valid move from the set of valid moves.
     * Calls the move method with this move.
     * @param view - ai button view
     */
    public void ai(View view) {
        game.ai();
        changePlayerTurnText();
        undoBtn.setEnabled(true);

        resetDraw();

        if (game.isCheck() || game.isCheckmate() || game.isStalemate())
            showAlert();
    }

    /**
     * The current player resigns and the game is over.
     * @param view - resign button view
     */
    public void resign(View view) {
        isResign = true;
        showAlert();
    }

    /**
     * The current player asks for a draw.
     * The next player can choose to accept the draw or not.
     * @param view - draw button view
     */
    public void draw(View view) {
        if (isDraw) {
            isDrawConfirmed = true;
            showAlert();
        }
        else {
            isDraw = true;
            drawBtn.setText(R.string.draw_confirm);
            //game.changePlayer();
            //changePlayerTurnText();
        }

    }

    public void move(int initFile, int initRank, int finalFile, int finalRank) {
        if (game.isValidMove(initFile, initRank, finalFile, finalRank)) {
            game.move(initFile, initRank, finalFile, finalRank);
            changePlayerTurnText();
            undoBtn.setEnabled(true);

            resetDraw();

            if (game.isCheck() || game.isCheckmate() || game.isStalemate())
                showAlert();
        }
    }

    public void changePlayerTurnText() {
        playerTurnTextView.setText(game.isWhitesMove() ? WHITE_TURN : BLACK_TURN);
    }

    public void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (game.isCheck() && !game.isCheckmate()) {
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
        if (game.isCheckmate() || game.isStalemate() || isResign || isDrawConfirmed) {
            undoBtn.setEnabled(false);
            aiBtn.setEnabled(false);
            drawBtn.setEnabled(false);
            resignBtn.setEnabled(false);

            String title;

            if (game.isCheckmate())
                title = getResources().getString(R.string.checkmate_title) + " " + (!game.isWhitesMove() ? "White" : "Black") + " wins!";
            else if (game.isStalemate())
                title = getResources().getString(R.string.stalemate_title);
            else if (isResign)
                title = getResources().getString(R.string.resign_title) + " " + (!game.isWhitesMove() ? "White" : "Black") + " wins!";
            else
                title = getResources().getString(R.string.draw_title);

            final EditText titleInput = new EditText(this);
            titleInput.setInputType(InputType.TYPE_CLASS_TEXT);
            titleInput.setHint(R.string.dialog_title_hint);

            builder.setTitle(title)
                    .setMessage(R.string.dialog_save_match_message)
                    .setView(titleInput)
                    .setPositiveButton(R.string.dialog_save_match_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            // will override later to override dismiss
                        }
                    })
                    .setNegativeButton(R.string.dialog_cancel_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false);

            final AlertDialog dialog = builder.create();
            dialog.show();
            //Overriding the handler immediately after show to prevent it from dismissing no matter what
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (!titleInput.getText().toString().trim().isEmpty()) {
                        recordedGame = new RecordedGame(titleInput.getText().toString(),
                                Calendar.getInstance(), game.getRecordedMoves());

                        if (!recordedGame.getRecordedMoves().isEmpty()) {
                            recordedGame.getRecordedMoves().get(recordedGame.getRecordedMoves().size()-1).setResign(isResign);
                            recordedGame.getRecordedMoves().get(recordedGame.getRecordedMoves().size()-1).setDraw(isDrawConfirmed);
                        }

                        // save this recordedGame to list of recorded games and write.

                           games.addGameToList(recordedGame);
                        try {
                            RecordedGamesList.write(getApplicationContext(), games);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                }
            });


        }
    }

    public void resetDraw() {
        isDraw = false;
        drawBtn.setText(R.string.draw_btn);
    }
}
