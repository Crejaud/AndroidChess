package jsutula.crejaud.androidchess.model;

import android.content.Context;

/**
 * Abstract class LongMovementPiece that extends Piece.
 * 1 new method to calculate whether there's pieces inbetween certain points on the board
 * Applies to King, Queen, Rook, and Bishop.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public abstract class LongMovementPiece extends Piece {

    public LongMovementPiece(boolean isWhite) {
        super(isWhite);
    }


    public boolean hasPiecesInbetween(int initFile, int initRank, int finalFile, int finalRank, Square[][] board) {
        // iteration variables
        int f, r;
        // necessary for diagonal movement
        int fileDif, rankDif;

        // if it's horizontal movement
        if (initRank == finalRank && initFile != finalFile) {
            // movement to the right
            if (initFile < finalFile)
                for (f = initFile+1; f < finalFile; f++)
                    if (board[f][initRank].hasPiece())
                        return true;
            // movement to the left
            if (initFile > finalFile)
                for (f = initFile-1; f > finalFile; f--)
                    if (board[f][initRank].hasPiece())
                        return true;
        }

        // if it's vertical movement
        if (initRank != finalRank && initFile == finalFile) {
            // movement upwards
            if (initRank < finalRank)
                for (r = initRank+1; r < finalRank; r++)
                    if (board[initFile][r].hasPiece())
                        return true;
            // movement downwards
            if (initRank > finalRank)
                for (r = initRank-1; r > finalRank; r--)
                    if (board[initFile][r].hasPiece())
                        return true;
        }

        // if it's diagonal movement
        rankDif = initRank - finalRank;
        fileDif = initFile - finalFile;
        if (Math.abs(rankDif) == Math.abs(fileDif)) {
            // up-right if both quantities are negative
            if (fileDif < 0 && rankDif < 0) {
                r = initRank+1;
                for (f = initFile+1; f < finalFile; f++) {
                    if (board[f][r].hasPiece())
                        return true;
                    r++;
                }
            }
            // up-left if the fileDif is positive and the rankDif is negative
            if (fileDif > 0 && rankDif < 0) {
                r = initRank+1;
                for (f = initFile-1; f > finalFile; f--) {
                    if (board[f][r].hasPiece())
                        return true;
                    r++;
                }
            }
            // down-right if the fileDif is negative and the rankDif is positive
            if (fileDif < 0 && rankDif > 0) {
                r = initRank-1;
                for (f = initFile+1; f < finalFile; f++) {
                    if (board[f][r].hasPiece())
                        return true;
                    r--;
                }
            }
            // down-left if both quantities are positive
            if (fileDif > 0 && rankDif > 0) {
                r = initRank-1;
                for (f = initFile-1; f > finalFile; f--) {
                    if (board[f][r].hasPiece())
                        return true;
                    r--;
                }
            }
        }

        // no pieces in between!
        return false;
    }

}
