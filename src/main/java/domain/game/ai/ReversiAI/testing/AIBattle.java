package domain.game.ai.ReversiAI.testing;

import domain.game.ai.ReversiAI.AIs.RandomAI;
import domain.game.ai.ReversiAI.AIs.GreedyAI;
import domain.game.ai.ReversiAI.Helpers.MakeMoveBeta;
import domain.game.ai.ReversiAI.Helpers.MoveFinder;
import domain.game.ai.ReversiAI.Helpers.MakeMove;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;

import static ReversiAI.Constants.Constants.boardSquareCount;

public class AIBattle {
    public static void main(String[] args) {
        // Set up board, default position
        boolean[] playerWhitePieces = new boolean[boardSquareCount];
        boolean[] playerBlackPieces = new boolean[boardSquareCount];
        playerWhitePieces[27] = true; playerWhitePieces[36] = true;
        playerBlackPieces[28] = true; playerBlackPieces[35] = true;

        BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces, true);

        boolean isWhiteTurn = false;
        boolean wasPass = false;
        while (true) {
            int[] moves = MoveFinder.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);
            if (moves.length == 0) {
                if (wasPass == true) {
                    // No more moves, game over
                    System.out.println("Game over!");
                    break;
                } else {
                    // No moves, pass
                    System.out.println((isWhiteTurn ? "White" : "Black") + " cannot make a move");
                    wasPass = true;
                }
            } else {
                // A move can be played
                wasPass = false;
                if (isWhiteTurn) {
                    // WHITE
                    int bestMove = RandomAI.getBestMove(playerWhitePieces, playerBlackPieces, true);
                    MakeMove.makeMove(playerWhitePieces, playerBlackPieces, true, bestMove);
                } else {
                    // BLACK
                    int bestMove = GreedyAI.getBestMove(playerWhitePieces, playerBlackPieces, false);
                    MakeMove.makeMove(playerWhitePieces, playerBlackPieces, false, bestMove);
                }
                BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces, true);
            }
            isWhiteTurn = !isWhiteTurn;
        }
        //System.out.println("\nEnd state board:");
        //BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces, true);
    }
}
