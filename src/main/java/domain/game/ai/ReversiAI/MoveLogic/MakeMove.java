/*
@TODO: Make function that accepts long
 */

package domain.game.ai.ReversiAI.MoveLogic;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class MakeMove {
    static byte[] directionsX = {-1, 0, 1, -1, 1, -1, 0, 1};
    static byte[] directionsY = {-1, -1, -1, 0, 0, 1, 1, 1};

    public static void makeMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn, int moveIndex){
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

    public static BoardPosition makeMove(long playerWhitePieces, long playerBlackPieces, boolean isWhiteTurn, int moveIndex) {
        // CHECK IF BROKEN

        boolean[] whiteBoolean = LongToBoolArray.convert(playerWhitePieces);
        boolean[] blackBoolean = LongToBoolArray.convert(playerBlackPieces);

        makeMove(whiteBoolean, blackBoolean, isWhiteTurn, moveIndex);
        return new BoardPosition(BoolArrayToLong.convert(whiteBoolean), BoolArrayToLong.convert(blackBoolean), isWhiteTurn);
    }
}
