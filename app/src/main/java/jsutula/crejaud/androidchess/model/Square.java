package jsutula.crejaud.androidchess.model;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import jsutula.crejaud.androidchess.R;

/**
 * Square class represents a spot on the board, which has a color and an occupying piece
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class Square extends ImageView {

    private Piece occupyingPiece;
    private boolean isWhite;

    public Square(Context c, boolean isWhite) {
        this(c, null, isWhite);
    }

    public Square(Context c, Piece piece, boolean isWhite) {
        super(c);
        this.occupyingPiece = piece;
        this.isWhite = !isWhite;

        int colorID = isWhite ? R.color.whiteSquare : R.color.blackSquare;
        setBackgroundColor(getResources().getColor(colorID));

        if (occupyingPiece != null) {
            setImageResource(occupyingPiece.getDrawable());
        }
    }

    /**
     * Sets the piece occupying the square with the parameter
     * @param piece - the new piece occupying the square
     */
    public void setPiece(Piece piece) {
        this.occupyingPiece = piece;
    }

    /**
     * occupyingPiece Accessor
     * @return the occupying piece
     */
    public Piece getPiece() {
        return occupyingPiece;
    }

    /**
     * Checks to see if there exists an occupyingPiece on the square
     * @return true if there is an occupying piece, else false
     */
    public boolean hasPiece() {
        return occupyingPiece != null;
    }

    /**
     * isWhite Accessor
     * @return true if square is white, false is square is black
     */
    public boolean isSquareWhite() {
        return isWhite;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Toast.makeText(getContext(), "drawn", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (occupyingPiece != null)
            Toast.makeText(getContext(), "This is: " + occupyingPiece + ", color: " + occupyingPiece.isWhite(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getContext(), "This is: empty", Toast.LENGTH_SHORT).show();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        final int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh)
    {
        super.onSizeChanged(w, w, oldw, oldh);
    }
}
