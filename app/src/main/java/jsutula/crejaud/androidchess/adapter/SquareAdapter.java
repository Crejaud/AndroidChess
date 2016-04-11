package jsutula.crejaud.androidchess.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import jsutula.crejaud.androidchess.model.Square;

public class SquareAdapter extends BaseAdapter {

    private Context context;
    private Square[][] board;
    private int rankPosition, filePosition;

    public SquareAdapter(Context context, Square[][] board) {
        this.context = context;
        this.board = board;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
        return 0;
    }

    @Override
    public ImageView getView(int position, View view, ViewGroup parent) {
        filePosition = position % 8;
        rankPosition = (position - filePosition)/8;

        return board[filePosition][rankPosition];
    }
}
