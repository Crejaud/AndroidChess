package jsutula.crejaud.androidchess.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

import jsutula.crejaud.androidchess.R;
import jsutula.crejaud.androidchess.model.BoardGridView;

public class ChessActivity extends AppCompatActivity {

    GridView chessboardGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_chess);

        chessboardGrid = (GridView) findViewById(R.id.chessboardGrid);

        BoardGridView newGame = new BoardGridView();



    }
}
