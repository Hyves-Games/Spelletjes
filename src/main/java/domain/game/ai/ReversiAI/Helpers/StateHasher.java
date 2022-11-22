/*
Needs heavy optimisation for performance.
 */

package ReversiAI.Helpers;

import static ReversiAI.Constants.Constants.boardSquareCount;

public class StateHasher {
    private static long boolArrayToLong(boolean[] a) {
        long n = 0;
        for (boolean b : a)
            n = (n << 1) | (b ? 1 : 0);
        return n;
    }

    public static long Hash(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        //return boolArrayToLong(playerWhitePieces) + (isWhiteTurn ? "1" : "0") + boolArrayToLong(playerBlackPieces);
        return boolArrayToLong(playerWhitePieces) + boolArrayToLong(playerBlackPieces) + (isWhiteTurn ? 1 : 0); // @TODO: Evaluate if hashes are unique.
    }
    public static long Hash(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        // NOT EQUAVALENT WITH OTHER METHOD
        //return boolArrayToLong(playerWhitePieces) + (isWhiteTurn ? "1" : "0") + boolArrayToLong(playerBlackPieces);
        //return playerWhitePieces + playerBlackPieces + (isWhiteTurn ? 1 : 0); // @TODO: Evaluate if hashes are unique.
        return playerWhitePieces * playerBlackPieces + (isWhiteTurn ? 1 : 0); // @TODO: Evaluate if hashes are unique.
    }
}
