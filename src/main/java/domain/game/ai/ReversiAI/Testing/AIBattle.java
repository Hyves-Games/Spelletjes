package domain.game.ai.ReversiAI.Testing;

import domain.game.ai.ReversiAI.AIs.*;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinder;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Helpers.PieceCounter;
import domain.game.ai.ReversiAI.SuperClasses.AI;
import domain.game.ai.ReversiAI.Board.*;

import java.util.Arrays;

public class AIBattle {
    public static void main(String[] args) throws Exception {
        //////////////////////////////
        int GameCount = 1000;
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
            long playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
            long playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;

            // Play a round
            boolean isWhiteTurn = false;
            boolean wasPass = false;
            while (true) {
                int[] moves = MoveFinder.findAvailableMoves(LongToBoolArray.convert(playerWhitePieces), LongToBoolArray.convert(playerBlackPieces), isWhiteTurn);
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

                    BoardPosition newBoard = MakeMove.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, bestMove);

                    if ((newBoard.playerWhitePieces == playerWhitePieces) || (newBoard.playerBlackPieces == playerBlackPieces)) {
                        System.out.println("\nBoard before:");
                        BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);
                        System.out.println("Board after:");
                        BoardPrinter.printBoard(newBoard.playerWhitePieces, newBoard.playerBlackPieces);
                        System.out.println(Arrays.toString(LongToBoolArray.convert(playerWhitePieces)));
                        System.out.println(Arrays.toString(LongToBoolArray.convert(playerBlackPieces)));
                        System.out.println("fast move solver: " + MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn, true));
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
