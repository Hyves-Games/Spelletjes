package domain.game.ai.ReversiAI.Helpers;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class PieceCounter {
    public static int[] countPieces(boolean[] playerWhitePieces, boolean[] playerBlackPieces) {
        int pieceCountWhite = 0;
        int pieceCountBlack = 0;
        for (int i = 0; i < boardSquareCount; i++) {
            if (playerWhitePieces[i]) {
                pieceCountWhite ++;
            }
            if (playerBlackPieces[i]) {
                pieceCountBlack ++;
            }
        }
        int[] results = new int[2];
        results[0] = pieceCountWhite;
        results[1] = pieceCountBlack;
        return results;
    }
}
