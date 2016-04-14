package jsutula.crejaud.androidchess.model;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.util.AttributeSet;
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
public class Square extends ImageView implements Cloneable {

    private Piece occupyingPiece;
    private boolean isWhite;

    public Square(Context c, boolean isWhite) {
        this(c, null, isWhite);
    }

    public Square(Context c, Piece piece, boolean isWhite) {
        super(c);
        setPiece(piece);
        this.isWhite = !isWhite;

        int colorID = isWhite ? R.color.whiteSquare : R.color.blackSquare;
        setBackgroundColor(getResources().getColor(colorID));
    }

    public Square(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public Square(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Sets the piece occupying the square with the parameter
     * @param piece - the new piece occupying the square
     */
    public void setPiece(Piece piece) {
        this.occupyingPiece = piece;

        if (occupyingPiece == null)
            setImageDrawable(null);
        else {
            setImageResource(occupyingPiece.getDrawable());
            setScaleType(ScaleType.CENTER_INSIDE);
        }
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
    public boolean onTouchEvent(MotionEvent event) {
        //if (occupyingPiece != null)
        //    Toast.makeText(getContext(), "This is: " + occupyingPiece + ", color: " + occupyingPiece.isWhite(), Toast.LENGTH_SHORT).show();
        //else
        //    Toast.makeText(getContext(), "This is: empty", Toast.LENGTH_SHORT).show();
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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
