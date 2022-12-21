/*
Needs heavy optimisation for performance.
 */

package domain.game.ai.ReversiAI.Helpers;

import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class StateHasher {
    public static long Hash(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        //return boolArrayToLong(playerWhitePieces) + (isWhiteTurn ? "1" : "0") + boolArrayToLong(playerBlackPieces);
        return BoolArrayToLong.convert(playerWhitePieces) + BoolArrayToLong.convert(playerBlackPieces) + (isWhiteTurn ? 1 : 0); // @TODO: Evaluate if hashes are unique.
    }
    public static long Hash(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        // NOT EQUIVALENT WITH OTHER METHOD
        return playerWhitePieces * playerBlackPieces + (isWhiteTurn ? 1 : 0); // @TODO: Evaluate if hashes are unique.
    }
}
