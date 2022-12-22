package domain.game.ai.ReversiAI.Converters;

import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMaskSingleBit;

public class LongToMoves {
    public static int[] convert(long l) {
        int bci = Long.bitCount(l);
        int[] moves = new int[bci];
        byte moveCount = 0;
        byte bc = (byte) bci;
        for (int i = Long.numberOfLeadingZeros(l); moveCount < bc; i++) {
            if ((l & bitMaskSingleBit[i]) != 0) {
                moves[moveCount] = i;
                moveCount++;
            }
        }
        return moves;
    }
}
