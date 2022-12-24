package domain.game.ai.ReversiAI.Converters;

import static domain.game.ai.ReversiAI.Constants.Constants.boardSquareCount;
import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMaskSingleBit;

public class LongToBoolArray {
    public static boolean[] convert(long playerPieces) {
        boolean[] piecesBoolean = new boolean[64];
        byte moveCount = 0;
        for (int i = Long.numberOfLeadingZeros(playerPieces); moveCount < Long.bitCount(playerPieces); i++) {
            if ((bitMaskSingleBit[i] & playerPieces) != 0) {
                piecesBoolean[i] = (bitMaskSingleBit[i] & playerPieces) != 0;
                moveCount++;
            }
        }
        return piecesBoolean;
    }
}
