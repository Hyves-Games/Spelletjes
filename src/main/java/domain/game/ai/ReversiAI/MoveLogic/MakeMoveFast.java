package domain.game.ai.ReversiAI.MoveLogic;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.Masks.BitMasks;

import static domain.game.ai.ReversiAI.Constants.Constants.boardWidth;
import static domain.game.ai.ReversiAI.Constants.Constants.halfBoardWidth;

public class MakeMoveFast {
    static byte[] directionsX = {-1, 0, 1, -1, 1, -1, 0, 1};
    static byte[] directionsY = {-1, -1, -1, 0, 0, 1, 1, 1};

    static long[] directionMasks = {
            0x7F7F7F7F7F7F7F7FL, /* Right. */
            0x007F7F7F7F7F7F7FL, /* Down-right. */
            0xFFFFFFFFFFFFFFFFL, /* Down. */
            0x00FEFEFEFEFEFEFEL, /* Down-left. */
            0xFEFEFEFEFEFEFEFEL, /* Left. */
            0xFEFEFEFEFEFEFE00L, /* Up-left. */
            0xFFFFFFFFFFFFFFFFL, /* Up. */
            0x7F7F7F7F7F7F7F00L  /* Up-right. */
    };

    static byte[] leftShifts = {
            0, /* Right. */
            0, /* Down-right. */
            0, /* Down. */
            0, /* Down-left. */
            1, /* Left. */
            9, /* Up-left. */
            8, /* Up. */
            7  /* Up-right. */
    };

    static byte[] rightShifts = {
            1, /* Right. */
            9, /* Down-right. */
            8, /* Down. */
            7, /* Down-left. */
            0, /* Left. */
            0, /* Up-left. */
            0, /* Up. */
            0  /* Up-right. */
    };

    private static long shift(long pieces, byte dir) {
        if (dir < halfBoardWidth) {
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

        for (byte dir = 0; dir < boardWidth; dir++) {
            /* Find opponent disk adjacent to the new disk. */
            x = shift(new_disk, dir) & oppositeColoredPieces;

            /* Add any adjacent opponent disk to that one, and so on. */
            for (int i = 0; i < 5; i++) {
                x |= shift(x, dir) & oppositeColoredPieces;
            }

            /* Determine whether the disks were captured. */
            bounding_disk = shift(x, dir) & sameColoredPieces;

            //captured_disks |= (bounding_disk ? x : 0);
            captured_disks |= (bounding_disk != 0 ? x : 0);
        }

        sameColoredPieces ^= captured_disks;
        oppositeColoredPieces ^= captured_disks;

        playerWhitePieces = isWhiteTurn ? sameColoredPieces : oppositeColoredPieces;
        playerBlackPieces = isWhiteTurn ? oppositeColoredPieces : sameColoredPieces;
        BoardPosition resolvedPosition = new BoardPosition(playerWhitePieces, playerBlackPieces, isWhiteTurn);

        return resolvedPosition;
    }
}
