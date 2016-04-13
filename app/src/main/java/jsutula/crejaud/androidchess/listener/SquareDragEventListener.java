package jsutula.crejaud.androidchess.listener;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import jsutula.crejaud.androidchess.activity.ChessActivity;
import jsutula.crejaud.androidchess.model.Game;
import jsutula.crejaud.androidchess.model.Piece;
import jsutula.crejaud.androidchess.model.Square;

/**
 * The class is the listener for dragging pieces around.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class SquareDragEventListener implements View.OnDragListener{

    private ChessActivity chessActivity;
    private Game game;
    private Square[][] board;
    private GridView gridView;
    private Context context;
    private int barHeight;
    private int initFile, initRank, finalFile, finalRank;

    public SquareDragEventListener(Game game, GridView gv, int barHeight, Context ctx) {
        this.game = game;
        this.board = game.getBoard();
        this.gridView = gv;
        this.barHeight = barHeight;
        this.context = ctx;
        this.chessActivity = (ChessActivity) context;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Piece p;
        String s;
        int x,y;

        switch(event.getAction())
        {
            case DragEvent.ACTION_DRAG_STARTED:

                x = (int) event.getX();
                y = (int) event.getY();

                y -= barHeight;

                Log.d("Height of titlebar", barHeight +"");
                Log.d("START POSITION", x + " " + y);

                initFile = gridView.pointToPosition(x, y) % 8;
                initRank = Math.abs((gridView.pointToPosition(x, y) - initFile)/8 - 7);

                Log.d("START POSITION",initFile + " " + initRank);

                if (!board[initFile][initRank].hasPiece()) {
                    Log.d("INVALID", "EMPTY");
                    return false;
                }

                // Do nothing
                break;

            case DragEvent.ACTION_DROP:
                Log.d("TEST", "ACTION_DROP event");

                finalFile = gridView.getPositionForView(v) % 8;
                finalRank = Math.abs((gridView.getPositionForView(v) - finalFile)/8 - 7);

                Log.d("END POS", gridView.getPositionForView(v) + "");
                Log.d("START POSITION",initFile + " " + initRank);
                Log.d("END POSITION",finalFile + " " + finalRank);

                if (initFile == finalFile && initRank == finalRank)
                    return true;

                // we now attempt to move the piece
                chessActivity.move(initFile, initRank, finalFile, finalRank);

                // Do nothing
                break;
            default: break;
        }
        return true;
    }
}
