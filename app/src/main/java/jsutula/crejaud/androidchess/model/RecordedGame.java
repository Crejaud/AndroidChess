package jsutula.crejaud.androidchess.model;

import android.support.v4.util.Pair;

import java.util.Map;

/**
 * This class holds the necessary information to playback a game of chess.
 *
 * Has a String called inputs which holds a long stream of characters 0 through 7.
 * Each 4 characters is 1 move in the form: FileRankFileRank
 * Example of 1 move: 0102
 * 0102 means move the piece at File:0 Rank:1 to File:0 Rank:2.
 * An example of inputs: 0102432353213435       Note these may not be legit moves.
 * Note that inputs.length % 4 is always 0.
 * All moves in inputs are valid, because they come from a legit game of Chess.
 *
 * Has a Map called pieceGraveyardMap. This is holds the dead pieces along the whole game.
 * The key of this map is the move number. So 1, indicates the first move. 2 indicates the second move, and so forth.
 * The value is a Pair object of a Piece and an Integer.
 * The Piece is the piece that gets removed from the board at the specified move number (key).
 * The Integer is the position of the piece when it got removed from the board.
 *
 * This map is necessary for going backwards in a playback.
 *
 * The inputs is necessary for going forwards and backwards in a playback.
 *
 * @author Corentin Rejaud
 * @author Julia Sutula
 */
public class RecordedGame {

    private String inputs;
    private Map<Integer, Pair<Piece, Integer>> pieceGraveyardMap;

    public RecordedGame(String inputs, Map<Integer, Pair<Piece, Integer>> pieceGraveyardMap) {
        this.inputs = inputs;
        this.pieceGraveyardMap = pieceGraveyardMap;
    }

    public String getInputs() {
        return inputs;
    }

    public Map<Integer,Pair<Piece, Integer>> getPieceGraveyardMap() {
        return pieceGraveyardMap;
    }

}
