package support.ais.Reversi.Testing;

import support.ais.Reversi.ATHENA;
import support.ais.Reversi.Helpers.BoardPrinter;
import support.ais.Reversi.Helpers.PieceCounter;
import support.ais.Reversi.MoveLogic.MakeMove;
import support.ais.Reversi.MoveLogic.MoveFinderFast;
import support.ais.Reversi.Interfaces.Strategy;
import support.ais.Reversi.Board.*;
import support.ais.Reversi.RandomAI;

import java.util.Arrays;

// No logging support, no database connection

public class AIBattleClassic {
    public static void main(String[] args) throws Exception {
        //////////////////////////////
        int GameCount = 1; // Amount of games to be played
        int RandomInitialMoveCount = 0; // Amount of random moves played before AIs take over. Avoids repetitive behaviour when comparing deterministic AIs at fixed depths.
        //AI AIOne = new MiniMaxABAI(MiniMaxStragegyEnum.DYNAMIC);
        Strategy AIOne = new RandomAI();
        Strategy AITwo = new ATHENA();
        //////////////////////////////

        int AIOneWinCount = 0;
        int AITwoWinCount = 0;

        // Progress bar
        int barLength = 40;
        int lastFilledCount = 0;
        System.out.println("Running " + GameCount + " games...");
        System.out.print("[");
        for (int b = 0; b < barLength; b++) {
            System.out.print("░");
        }
        System.out.print("]");

        // Run the games
        Strategy WhiteAI = AIOne;
        Strategy BlackAI = AITwo;
        Strategy RandomAI = new RandomAI();

        long start = System.currentTimeMillis();
        for (int i = 1; i <= GameCount; i++) {
            // Set up board, default position
            long playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
            long playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;

            // Play a round
            boolean isWhiteTurn = false;
            boolean wasPass = false;
            int randomMovesLeft = RandomInitialMoveCount;
            while (true) {
                int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

                // Print board for debugging
                BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);

                // (SLOW) test accuracy of fast bitwise move-finder compared to slower and accurate iterative move-finder
//                int[] accurateResult = MoveFinder.findAvailableMoves(LongToBoolArray.convert(playerWhitePieces), LongToBoolArray.convert(playerBlackPieces), isWhiteTurn);
//                if (!Arrays.equals(accurateResult, moves)) {
//                    System.out.println("fast: " + Arrays.toString(moves) + "accurate: " + Arrays.toString(accurateResult));
//                    throw new Exception("FAST MOVE FINDER WRONG RESULT");
//                };

                if (moves.length == 0) {
                    if (wasPass) {
                        // No more moves, game over
                        break;
                    } else {
                        // No moves, pass
                        wasPass = true;
                    }
                } else {
                    // A move can be played
                    Strategy selectedAI = isWhiteTurn ? WhiteAI : BlackAI;
                    if (randomMovesLeft > 0) {
                        randomMovesLeft += -1;
                        selectedAI = RandomAI;
                    }

                    wasPass = false;
                    int bestMove = selectedAI.getBestMove(playerWhitePieces, playerBlackPieces, isWhiteTurn);

                    BoardPosition newBoard = MakeMove.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, bestMove);

                    if ((newBoard.playerWhitePieces == playerWhitePieces) || (newBoard.playerBlackPieces == playerBlackPieces)) {
                        System.out.println("\nBoard before:");
                        BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);
                        System.out.println("white board, black board: " + playerWhitePieces + ", " + playerBlackPieces);
                        throw new Exception("Board change incorrect! game: " + i + ", move: " + bestMove + ", white's turn: " + isWhiteTurn + ", available moves: " + Arrays.toString(moves));
                    }
                    playerWhitePieces = newBoard.playerWhitePieces;
                    playerBlackPieces = newBoard.playerBlackPieces;
                }
                isWhiteTurn = !isWhiteTurn;
            }

            // Determine winner and add to count
            if (PieceCounter.countPieces(playerWhitePieces) > PieceCounter.countPieces(playerBlackPieces)) {
                // White wins
                if (WhiteAI == AIOne) {
                    AIOneWinCount++;
                }
                if (WhiteAI == AITwo) {
                    AITwoWinCount++;
                }
            } else if (PieceCounter.countPieces(playerWhitePieces) < PieceCounter.countPieces(playerBlackPieces)) {
                // Black wins
                if (BlackAI == AIOne) {
                    AIOneWinCount++;
                }
                if (BlackAI == AITwo) {
                    AITwoWinCount++;
                }
            }

            // Assign AI to play the other color for a next game;
            Strategy temp = WhiteAI;
            WhiteAI = BlackAI;
            BlackAI = temp;

            // Update console
            int filledCount = (i * barLength) / GameCount;
            if (filledCount > lastFilledCount) {
                for (int e = 0; e < barLength + 1; e++) {
                    System.out.print("\b");
                }
                for (int b = 0; b < barLength; b++) {

                    if (b < filledCount) {
                        System.out.print("▓");
                    } else {
                        System.out.print("░");
                    }
                }
                System.out.print("]");
            }
            lastFilledCount = filledCount;
        }

        // Print results
        System.out.println("\n" + AIOne.getName() + " won " + AIOneWinCount + " games.\n" + AITwo.getName() + " won " + AITwoWinCount + " games.");
        System.out.println((System.currentTimeMillis() - start) + " ms duration");
        System.out.println(((ATHENA) AITwo).nodesExplored + " nodes explored");
    }
}
