package domain.game.ai.ReversiAI.Converters;

import domain.game.ai.ReversiAI.Masks.BitMasks;

import static domain.game.ai.ReversiAI.Constants.Constants.boardSquareCount;

public class BoolArrayToLong {
    public static long convert(boolean[] b) {
        long n = 0;
        for (boolean a : b) {
            n = (n << 1) | (a ? 1 : 0);
        }

        //for (int i = 0; i < boardSquareCount; i++) {
        //    if (b[i]) {
        //        n = n | BitMasks.bitMaskSingleBit[i]; // Set the bit
        //    }
        //}
        return n;
    }
}