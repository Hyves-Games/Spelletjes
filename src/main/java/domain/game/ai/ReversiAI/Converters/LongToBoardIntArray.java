package domain.game.ai.ReversiAI.Converters;

import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMaskSingleBit;

public class LongToBoardIntArray {
    public static int[] convert(long playerAPieces, long playerBPieces, int aPiecesInt, int bPiecesInt) {
        int[] board = new int[64];
        for (int i = 0; i < 64; i++) {
            if ((playerAPieces & bitMaskSingleBit[i]) != 0) {
                board[i] = aPiecesInt;
            } else if ((playerBPieces & bitMaskSingleBit[i]) != 0) {
                board[i] = bPiecesInt;
            }
        }
        return board;
    }
}
