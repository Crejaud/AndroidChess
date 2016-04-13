package jsutula.crejaud.androidchess.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
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
public class SquareAdapter extends BaseAdapter {

    private Context context;
    private Square[][] board;
    private Game game;
    //private int rank, file;
    android.widget.GridView.LayoutParams layoutParams;

    public SquareAdapter(Context context, Game game) {
        this.context = context;
        this.board = game.getBoard();
        this.game = game;
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
    public Square getView(final int position, View view, ViewGroup parent) {
        final int file = position % 8;
        final int rank = Math.abs((position - file)/8 - 7);

        GridView gv = (GridView) parent;

        final Square img = board[file][rank];
        img.setTag(file + " " + rank + "");
        final String msg = "TESTING";

        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(board[file][rank]);

                v.startDrag(dragData,myShadow,board[file][rank],0);
                return true;
            }
        });

        int barHeight = ((Activity) context).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();

        board[file][rank].setOnDragListener(new SquareDragEventListener(game, gv, barHeight, context));

//        img.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    ClipData data = ClipData.newPlainText("", "");
//                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(img);
//
//                    img.startDrag(data, shadowBuilder, img, 0);
//                    img.setVisibility(View.INVISIBLE);
//                    return true;
//                }
//                else
//                {
//                    return false;
//                }
//            }
//        });

//        board[file][rank].setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(context, "File: " + file + "; Rank: " + rank, Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });

        return board[file][rank];
    }
}
