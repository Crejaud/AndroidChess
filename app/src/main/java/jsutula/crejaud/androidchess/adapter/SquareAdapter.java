package jsutula.crejaud.androidchess.adapter;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import jsutula.crejaud.androidchess.model.Square;

public class SquareAdapter extends BaseAdapter {

    private Context context;
    private Square[][] board;
    //private int rank, file;

    public SquareAdapter(Context context, Square[][] board) {
        this.context = context;
        this.board = board;
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

        board[file][rank].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context, "File: " + file + "; Rank: " + rank, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        return board[file][rank];
    }
}
