package ReversiAI.Helpers;

import java.util.Arrays;

import static ReversiAI.Constants.Constants.*;

public class MoveFinder {
    static int[] directionsX = {-1, 0, 1, -1, 1, -1, 0, 1};
    static int[] directionsY = {-1, -1, -1, 0, 0, 1, 1, 1};

    public static int[] findAvailableMoves(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn) {
        int[] moves = new int[maxMovesMemoryLimit]; // @TODO: Consider using bSQ/2 as upper bound. (~1% performance increase)
        int movesCount = 0;

        boolean[] oppositeColoredPieces = isWhiteTurn ? playerBlackPieces : playerWhitePieces;
        boolean[] sameColoredPieces = isWhiteTurn ? playerWhitePieces : playerBlackPieces;

        for (int y = 0; y < boardWidth; y++) {
            // For each row
            for (int x = 0; x < boardWidth; x++) {
                // For each potential tile move
                int centerPositionIndex = (y * boardWidth + x);
                if (sameColoredPieces[centerPositionIndex] || oppositeColoredPieces[centerPositionIndex]) {
                    // Square is already occupied, invalid move.
                    continue;
                }
                boolean isValidMove = false;
                for (int d = 0; (d < directionsX.length); d++) {
                    // For each directional line
                    if (isValidMove) { // @TODO: put this in for-loop condition.
                        break;
                    }

                    int exploreX = x;
                    int exploreY = y;

                    boolean oppositeColorNeighbourFound = false;
                    while (true) {
                        // For each position in directional line
                        exploreX += directionsX[d];
                        exploreY += directionsY[d];
                        int exploreIndex = exploreY * boardWidth + exploreX;
                        if (exploreX < 0 || exploreY < 0 || exploreX >= boardWidth || exploreY >= boardWidth) {
                            // Out of bounds for this direction
                            break;
                        }
                        if (!sameColoredPieces[exploreIndex] && !oppositeColoredPieces[exploreIndex]) {
                            // Empty square
                            break;
                        } else if (oppositeColoredPieces[exploreIndex]) {
                            // Valid neighbour found
                            oppositeColorNeighbourFound = true;
                        } else if (!oppositeColorNeighbourFound) { // } else if (!oppositeColorNeighbourFound && sameColoredPieces[exploreIndex]) {
                            // Not a valid direction
                            break;
                        } else { // } else if (oppositeColorNeighbourFound && sameColoredPieces[exploreIndex]) {
                            // Valid move found
                            moves[movesCount] = centerPositionIndex;
                            movesCount += 1;
                            isValidMove = true;
                            break;
                        }
                    }
                }
            }
        }
        return Arrays.copyOfRange(moves, 0, movesCount); // @TODO: Check if this is faster or not than a manual copy
    }
}
