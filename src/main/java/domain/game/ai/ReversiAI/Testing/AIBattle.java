package domain.game.ai.ReversiAI.Testing;

import domain.game.ai.ReversiAI.AIs.*;
import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.Helpers.MakeMove;
import domain.game.ai.ReversiAI.Helpers.PieceCounter;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class AIBattle {
    public static void main(String[] args) {
        //////////////////////////////
        int GameCount = 10000;
        AI AIOne = new RandomAI();
        AI AITwo = new RandomAI();
        //////////////////////////////

        int AIOneWinCount = 0;
        int AITwoWinCount = 0;

        int barLength = 40;
        int lastFilledCount = 0;
        System.out.println("Running " + GameCount + " games...");
        System.out.print("[");
        for (int b = 0; b < barLength; b++) {
            System.out.print("░");
        }
        System.out.print("]");

        // Run the games
        AI WhiteAI = AIOne;
        AI BlackAI = AITwo;
        for (int i = 1; i <= GameCount; i++) {
            // Set up board, default position
            boolean[] playerWhitePieces = new boolean[boardSquareCount];
            boolean[] playerBlackPieces = new boolean[boardSquareCount];
            playerWhitePieces[27] = true;
            playerWhitePieces[36] = true;
            playerBlackPieces[28] = true;
            playerBlackPieces[35] = true;

            // Play a round
            boolean isWhiteTurn = false;
            boolean wasPass = false;
            while (true) {
                int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
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
                    AI selectedAI = isWhiteTurn ? WhiteAI : BlackAI;
                    wasPass = false;
                    int bestMove = selectedAI.getBestMove(playerWhitePieces, playerBlackPieces, isWhiteTurn);
                    MakeMove.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, bestMove);
                }
                isWhiteTurn = !isWhiteTurn;
            }

            // Determine winner and add to count
            int[] results = PieceCounter.countPieces(playerWhitePieces, playerBlackPieces);
            if (results[0] > results[1]) {
                // White wins
                if (WhiteAI == AIOne) {
                    AIOneWinCount++;
                }
                if (WhiteAI == AITwo) {
                    AITwoWinCount++;
                }
            } else {
                // Black wins
                if (BlackAI == AIOne) {
                    AIOneWinCount++;
                }
                if (BlackAI == AITwo) {
                    AITwoWinCount++;
                }
            }

            // Assign AI to play the other color for a next game;
            AI temp = WhiteAI;
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
        System.out.println("\n" + AIOne.getAIName() + " won " + AIOneWinCount + " games.\n" + AITwo.getAIName() + " won " + AITwoWinCount + " games.");
    }
}
