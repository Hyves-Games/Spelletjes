package domain.game.ai.ReversiAI.MoveLogic;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Masks.BitMasks;

public class MakeMoveFast {
    static long[] directionMasks = {
            0b0111111101111111011111110111111101111111011111110111111101111111L, // Right
            0b0000000001111111011111110111111101111111011111110111111101111111L, // Down-right
            0b0000000011111111111111111111111111111111111111111111111111111111L, // Down
            0b0000000011111110111111101111111011111110111111101111111011111110L, // Down-left
            0b1111111011111110111111101111111011111110111111101111111011111110L, // Left
            0b1111111011111110111111101111111011111110111111101111111000000000L, // Up-left
            0b1111111111111111111111111111111111111111111111111111111100000000L, // Up
            0b0111111101111111011111110111111101111111011111110111111100000000L  // Up-right
    };

    static final byte[] leftShifts = {
            0, // Right
            0, // Down-right
            0, // Down
            0, // Down-left
            1, // Left
            9, // Up-left
            8, // Up
            7  // Up-right
    };

    static final byte[] rightShifts = {
            1, // Right
            9, // Down-right
            8, // Down
            7, // Down-left
            0, // Left
            0, // Up-left
            0, // Up
            0  // Up-right
    };

    static long shift(long pieces, byte dir) {
        if (dir < 4) {
            return (pieces >> rightShifts[dir]) & directionMasks[dir];
        } else {
            return (pieces << leftShifts[dir]) & directionMasks[dir];
        }
    }

    public static BoardPosition makeMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int moveIndex){
        long oppositeColoredPieces = isWhiteTurn ? playerBlackPieces : playerWhitePieces;
        long sameColoredPieces = isWhiteTurn ? playerWhitePieces : playerBlackPieces;

        long x;
        long bounding_disk;
        long new_disk = BitMasks.bitMaskSingleBit[moveIndex];
        long captured_disks = 0;

        sameColoredPieces |= new_disk;

        for (byte dir = 0; dir < 8; dir++) {
            x = shift(new_disk, dir) & oppositeColoredPieces;

            for (int i = 0; i < 5; i++) {
                x |= shift(x, dir) & oppositeColoredPieces;
            }

            bounding_disk = shift(x, dir) & sameColoredPieces;

            captured_disks |= (bounding_disk != 0 ? x : 0);
        }

        sameColoredPieces ^= captured_disks;
        oppositeColoredPieces ^= captured_disks;

        playerWhitePieces = isWhiteTurn ? sameColoredPieces : oppositeColoredPieces;
        playerBlackPieces = isWhiteTurn ? oppositeColoredPieces : sameColoredPieces;

        return new BoardPosition(playerWhitePieces, playerBlackPieces, isWhiteTurn);
    }
}
