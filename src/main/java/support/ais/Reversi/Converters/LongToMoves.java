package support.ais.Reversi.Converters;

import static support.ais.Reversi.Masks.BitMasks.bitMaskSingleBit;

public class LongToMoves {
    public static int[] convert(long l) {
        int[] moves = new int[Long.bitCount(l)];
        byte moveCount = 0;
        byte bc = (byte) Long.bitCount(l);
        for (int i = Long.numberOfLeadingZeros(l); moveCount < bc; i++) {
            if ((l & bitMaskSingleBit[i]) != 0) {
                moves[moveCount] = i;
                moveCount++;
            }
        }

        return moves;
    }
}
