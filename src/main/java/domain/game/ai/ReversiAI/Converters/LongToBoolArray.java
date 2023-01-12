package domain.game.ai.ReversiAI.Converters;

import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMask;

public class LongToBoolArray {
    public static boolean[] convert(long playerPieces) {
        boolean[] piecesBoolean = new boolean[64];
        for (byte i = 0; i < 64; i++) {
            if ((bitMask[i] & playerPieces) != 0) {
                piecesBoolean[i] = true;
            }
        }
        return piecesBoolean;
    }
}
