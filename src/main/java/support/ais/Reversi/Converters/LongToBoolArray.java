package support.ais.Reversi.Converters;

import static support.ais.Reversi.Masks.BitMasks.bitMaskSingleBit;

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
