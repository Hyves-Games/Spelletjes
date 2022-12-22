package domain.game.ai.ReversiAI.Converters;

import static domain.game.ai.ReversiAI.Constants.Constants.boardSquareCount;
import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMaskSingleBit;

public class LongToBoolArray {
    public static boolean[] convert(long playerPieces) {
        boolean[] piecesBoolean = new boolean[64];
        for (int i = 0; i < boardSquareCount; i++) {
            if ((bitMaskSingleBit[i] & playerPieces) != 0) {
                piecesBoolean[i] = true;
            }
        }
        return piecesBoolean;
    }
}
