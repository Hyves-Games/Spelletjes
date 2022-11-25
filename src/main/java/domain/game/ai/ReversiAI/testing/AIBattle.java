package domain.game.ai.ReversiAI.testing;

import domain.game.ai.ReversiAI.AIs.*;
import domain.game.ai.ReversiAI.Helpers.MakeMoveBeta;
import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.Helpers.MakeMove;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;
import domain.game.ai.ReversiAI.Helpers.PieceCounter;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

public class AIBattle {
    public static void main(String[] args) {
        //////////////////////////////
        int GAME_COUNT = 10000;
        AI FIRSTAI = new GreedyAI();
        AI SECONDAI = new RandomAI();
        //////////////////////////////

        int whiteWinCount = 0;
        int blackWinCount = 0;

        int barLength = 20;
        int lastFilledCount = 0;
        System.out.println("Running " + GAME_COUNT + " games...");
        System.out.print("[");
        for (int b = 0; b < barLength; b++) {
            System.out.print("░");
        }
        System.out.print("]");

        // Run the games
        for (int i = 1; i <= GAME_COUNT; i++) {
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
                    wasPass = false;
                    if (isWhiteTurn) {
                        // WHITE
                        int bestMove = FIRSTAI.getBestMove(playerWhitePieces, playerBlackPieces, true);
                        MakeMove.makeMove(playerWhitePieces, playerBlackPieces, true, bestMove);
                    } else {
                        // BLACK
                        int bestMove = SECONDAI.getBestMove(playerWhitePieces, playerBlackPieces, false);
                        MakeMove.makeMove(playerWhitePieces, playerBlackPieces, false, bestMove);
                    }
                }
                isWhiteTurn = !isWhiteTurn;
            }

            // Determine winner and add to count
            int[] results = PieceCounter.countPieces(playerWhitePieces, playerBlackPieces);
            if (results[0] > results[1]) {
                whiteWinCount++;
            } else {
                blackWinCount++;
            }

            // Update console
            int filledCount = i * barLength / GAME_COUNT;

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
        System.out.println("\n" + FIRSTAI.getAIName() + " has won " + whiteWinCount + " games.\n" + SECONDAI.getAIName() + " won " + blackWinCount + " games.");
    }
}
