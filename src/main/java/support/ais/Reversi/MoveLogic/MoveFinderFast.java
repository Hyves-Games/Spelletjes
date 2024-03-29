// Method of generating moves by bitwise operations was adapted and modified from Hans Wennborg's C code; https://www.hanshq.net/othello.html#moves

/* Notes:
Moving masks and shifts out of shifting function almost quadrupled performance.
Changing the shifts from long to byte increased performance slightly.
 */

package support.ais.Reversi.MoveLogic;

import support.ais.Reversi.Converters.IntArrayToLong;
import support.ais.Reversi.Converters.LongToBoolArray;
import support.ais.Reversi.Converters.LongToMoves;

public class MoveFinderFast {
    static final long[] directionMasks = {
            0b0111111101111111011111110111111101111111011111110111111101111111L, // Right
            0b0000000001111111011111110111111101111111011111110111111101111111L, // Down-right
            0b0000000011111111111111111111111111111111111111111111111111111111L, // Down
            0b0000000011111110111111101111111011111110111111101111111011111110L, // Down-left
            0b1111111011111110111111101111111011111110111111101111111011111110L, // Left
            0b1111111011111110111111101111111011111110111111101111111000000000L, // Up-left
            0b1111111111111111111111111111111111111111111111111111111100000000L, // Up
            0b0111111101111111011111110111111101111111011111110111111100000000L  // Up-right
    };

    static byte[] leftShifts = {
            0, // Right
            0, // Down-right
            0, // Down
            0, // Down-left
            1, // Left
            9, // Up-left
            8, // Up
            7  // Up-right
    };

    static byte[] rightShifts = {
            1, // Right
            9, // Down-right
            8, // Down
            7, // Down-left
            0, // Left
            0, // Up-left
            0, // Up
            0  // Up-right
    };

    private static long shift(long pieces, byte dir) {
        if (dir < 4) {
            return (pieces >> rightShifts[dir]) & directionMasks[dir];
        } else {
            return (pieces << leftShifts[dir]) & directionMasks[dir];
        }
//        if (dir < 4) {
//            return (pieces & directionMasks[dir]) >> rightShifts[dir] ;
//        } else {
//            return (pieces & directionMasks[dir] << leftShifts[dir]) ;
//        }
    }

    private static long generateMoves(long my_disks, long opp_disks) {
        long empty_cells = ~(my_disks | opp_disks);
        long legal_moves = 0;

        for (byte dir = 0; dir < 8; dir++) {
            long x = shift(my_disks, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            x |= shift(x, dir) & opp_disks;
            legal_moves |= shift(x, dir) & empty_cells;
        }

//        for (byte dir = 0; dir < 8; dir++) {
//            long shift;
//            if (dir < 4) {
//                long x = (my_disks >> rightShifts[dir]) & directionMasks[dir] & opp_disks;
//                for (int i = 0; i < 5; i++) {
//                    x |= (x >> rightShifts[dir]) & directionMasks[dir] & opp_disks;
//                }
//                legal_moves |= (x >> rightShifts[dir]) & directionMasks[dir] & empty_cells;
//            } else {
//                long x = (my_disks << leftShifts[dir]) & directionMasks[dir] & opp_disks;
//                for (int i = 0; i < 5; i++) {
//                    x |= (x << leftShifts[dir]) & directionMasks[dir] & opp_disks;
//                }
//                legal_moves |= (x << leftShifts[dir]) & directionMasks[dir] & empty_cells;
//            }
//        }

        return legal_moves;
    }

    public static int[] findAvailableMoves(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return LongToMoves.convert(generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces));
    }

    public static boolean[] findAvailableMoves(Integer[] board) {
        long whiteLong = IntArrayToLong.convert(board, 1);
        long blackLong = IntArrayToLong.convert(board, -1);

        return LongToBoolArray.convert(generateMoves(whiteLong, blackLong));
    }

    public static int findMoveCount(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn) {
        return Long.bitCount(generateMoves(isWhiteTurn ? playerWhitePieces : playerBlackPieces, isWhiteTurn ? playerBlackPieces : playerWhitePieces));
    }
}
