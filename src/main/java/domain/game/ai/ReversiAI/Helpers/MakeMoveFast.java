/*
@TODO: Make function that accepts long
 */

package domain.game.ai.ReversiAI.Helpers;

import domain.game.ai.ReversiAI.Board.BoardPosition;

import static domain.game.ai.ReversiAI.Constants.Constants.boardSquareCount;
import static domain.game.ai.ReversiAI.Constants.Constants.boardWidth;
import static domain.game.ai.ReversiAI.Masks.BitMasks.bitMask;

public class MakeMoveFast {
    static byte[] directionsX = {-1, 0, 1, -1, 1, -1, 0, 1};
    static byte[] directionsY = {-1, -1, -1, 0, 0, 1, 1, 1};

    public static void makeMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn, int moveIndex) {
        boolean[] oppositeColoredPieces = isWhiteTurn ? playerBlackPieces : playerWhitePieces;
        boolean[] sameColoredPieces = isWhiteTurn ? playerWhitePieces : playerBlackPieces;

        byte x = (byte) (moveIndex % boardWidth);
        byte y = (byte) (moveIndex / boardWidth);
        for (int d = 0; (d < directionsX.length); d++) {
            // For each directional line

            byte exploreX = x;
            byte exploreY = y;

            boolean oppositeColorNeighbourFound = false;
            int length = 0;
            while (true) {
                // For each position in directional line

                exploreX += directionsX[d];
                exploreY += directionsY[d];
                int exploreIndex = exploreY * boardWidth + exploreX;
                if (exploreX < 0 || exploreY < 0 || exploreX >= boardWidth || exploreY >= boardWidth) {
                    // Out of bounds for this direction
                    break;
                }

                length++; // Used to traverse back while flipping
                if (!sameColoredPieces[exploreIndex] && !oppositeColoredPieces[exploreIndex]) {
                    // Empty square
                    break;
                } else if (oppositeColoredPieces[exploreIndex]) {
                    // Valid opposite colored neighbour found
                    oppositeColorNeighbourFound = true;
                } else if (!oppositeColorNeighbourFound) { // } else if (!oppositeColorNeighbourFound && sameColoredPieces[exploreIndex]) {
                    // Not a valid direction
                    break;
                } else { // } else if (oppositeColorNeighbourFound && sameColoredPieces[exploreIndex]) {
                    // Valid direction
                    for (int i = 0; i <= length; i++) {
                        // 'Flip' over pieces (and add starting piece)
                        int i1 = (exploreY - i * directionsY[d]) * boardWidth + (exploreX - i * directionsX[d]);
                        sameColoredPieces[i1] = true;
                        oppositeColoredPieces[i1] = false;
                    }
                    break;
                }
            }
        }
    }

    public static void makeMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int moveIndex) {
        boolean[] whiteBoolean = new boolean[boardSquareCount];
        boolean[] blackBoolean = new boolean[boardSquareCount];
        for (byte i = 0; i < 64; i++) {
            if ((bitMask[i] & playerWhitePieces) > 0) {
                whiteBoolean[i] = true;
            } else if((bitMask[i] & playerBlackPieces) > 0) {
                blackBoolean[i] = true;
            }
        }

        makeMove(whiteBoolean, blackBoolean, isWhiteTurn, moveIndex);
    }
}
