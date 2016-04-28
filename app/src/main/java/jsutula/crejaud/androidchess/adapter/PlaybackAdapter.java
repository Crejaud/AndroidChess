package jsutula.crejaud.androidchess.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import jsutula.crejaud.androidchess.activity.ChessActivity;
import jsutula.crejaud.androidchess.activity.PlaybackActivity;
import jsutula.crejaud.androidchess.listener.SquareDragEventListener;
import jsutula.crejaud.androidchess.model.Game;
import jsutula.crejaud.androidchess.model.Piece;
import jsutula.crejaud.androidchess.model.Square;

/**
 * This adapter is for displaying the squares on the chess board gridview.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class PlaybackAdapter extends BaseAdapter {

    private PlaybackActivity playbackActivity;
    private Context context;
    private Square[][] board;
    private Game game;
    private int itemSelected;

    android.widget.GridView.LayoutParams layoutParams;

    public PlaybackAdapter(Context context, Game game) {
        this.context = context;
        this.board = game.getBoard();
        this.game = game;
        itemSelected = -1;
        playbackActivity = (PlaybackActivity) context;
    }

    @Override
    public Object getItem(int position) {
        int file = position % 8;
        int rank = Math.abs((position - file)/8 - 7);
        return getItem(file, rank);
    }

    @Override
    public int getCount() {
        return 64;
    }

    public int getRowCount() {
        return 8;
    }

    public int getColumnCount() {
        return 8;
    }


    public Object getItem(int file, int rank) {
        return board[file][rank];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Square getView(final int position, View view, final ViewGroup parent) {
        final int file = position % 8;
        final int rank = Math.abs((position - file)/8 - 7);

        final Square img = board[file][rank];
        img.setTag(file + " " + rank);



        int barHeight = ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();


        return board[file][rank];
    }
}