package jsutula.crejaud.androidchess.model;

import android.content.Context;

import jsutula.crejaud.androidchess.R;

/**
 * King class, which extends the LongMovementPiece abstract class
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class King extends LongMovementPiece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public boolean isValidMove(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        if (initFile == finalFile && Math.abs(initRank - finalRank) == 1
                && (board[finalFile][finalRank].getPiece() == null || board[finalFile][finalRank].getPiece().isWhite() != isWhite()))
            return true;
        if (initRank == finalRank && Math.abs(initFile - finalFile) == 1
                && (board[finalFile][finalRank].getPiece() == null || board[finalFile][finalRank].getPiece().isWhite() != isWhite()))
            return true;
        if( Math.abs(initFile - finalFile) == 1 && Math.abs(initRank-finalRank) ==1
                && (board[finalFile][finalRank].getPiece() == null || board[finalFile][finalRank].getPiece().isWhite() != isWhite()))
            return true;
        if (!hasMoved()){
            if(finalFile == 6 && (finalRank+1 == 1 || finalRank+1 == 8)
                    && board[7][finalRank].getPiece() instanceof Rook
                    && !board[7][finalRank].getPiece().hasMoved()
                    && board[7][finalRank].getPiece().isWhite() == isWhite()
                    && !hasPiecesInbetween(initFile, initRank, 7, finalRank, board)){
                return true;

            }
            if(finalFile == 2 && (finalRank+1 == 1 || finalRank+1 == 8)
                    && board[0][finalRank].getPiece() instanceof Rook
                    && !board[0][finalRank].getPiece().hasMoved()
                    && board[0][finalRank].getPiece().isWhite() == isWhite()
                    && !hasPiecesInbetween(initFile, initRank, 0, finalRank, board)){
                return true;

            }
        }

        return false;
    }

    @Override
    public RecordedMove move(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        RecordedMove recordedMove = new RecordedMove(hasMoved(), getEnpassant(),
                initFile, initRank, finalFile, finalRank,
                board[finalFile][finalRank].getPiece(), finalFile, finalRank);

        if (!hasMoved() && finalFile == 6 && (finalRank == 0 || finalRank == 7)
                && board[7][finalRank].getPiece() instanceof Rook
                && !board[7][finalRank].getPiece().hasMoved()
                && board[7][finalRank].getPiece().isWhite() == isWhite()
                && !hasPiecesInbetween(initFile, initRank, 7, finalRank, board)){
            castling(initFile, initRank, 7, finalRank, board, recordedMove);

        }
        else if (!hasMoved() && finalFile == 2 && (finalRank == 0 || finalRank == 7)
                && board[0][finalRank].getPiece() instanceof Rook
                && !board[0][finalRank].getPiece().hasMoved()
                && board[0][finalRank].getPiece().isWhite() == isWhite()
                && !hasPiecesInbetween(initFile, initRank, 0, finalRank, board)){
            castling(initFile, initRank, 0, finalRank, board, recordedMove);

        }
        else {
            board[finalFile][finalRank].setPiece(board[initFile][initRank].getPiece());
            board[initFile][initRank].setPiece(null);
        }

        setHasMoved(true); // set hasMoved to true

        return recordedMove;
    }

    /**
     *
     * castling()
     * @param initFile
     * @param initRank
     * @param finalFile
     * @param finalRank
     * @param board
     * @return void
     *
     * Moves pieces according to castling rules
     *
     *
     */
    public void castling(int initFile, int initRank, int finalFile, int finalRank, Square[][] board, RecordedMove recordedMove) {
        Piece king = board[initFile][initRank].getPiece();
        Piece rook = board[finalFile][finalRank].getPiece();

        rook.setHasMoved(true);

        recordedMove.setCastledinitRank(finalRank);
        recordedMove.setCastledFinalRank(finalRank);

        if (finalFile == 7) {
            board[6][finalRank].setPiece(king);
            board[5][finalRank].setPiece(rook);
            board[7][finalRank].setPiece(null); // remove duplicate rook
            board[4][finalRank].setPiece(null); // remove duplicate king
            recordedMove.setCastledInitFile(7);
            recordedMove.setCastledFinalFile(5);
        }
        if (finalFile == 0) {
            board[2][finalRank].setPiece(king);
            board[3][finalRank].setPiece(rook);
            board[0][finalRank].setPiece(null); // remove duplicate rook
            board[4][finalRank].setPiece(null); // remove duplicae king
            recordedMove.setCastledInitFile(0);
            recordedMove.setCastledFinalFile(3);
        }
    }

    @Override
    public int getDrawable() {
        if (isWhite())
            return R.drawable.ic_white_king;
        else
            return R.drawable.ic_black_king;
    }
}
