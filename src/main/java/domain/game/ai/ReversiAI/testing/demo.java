package domain.game.ai.ReversiAI.testing;

import static ReversiAI.Constants.Constants.*;
import domain.game.ai.ReversiAI.Helpers.*;
import domain.game.ai.ReversiAI.Heuristics.*;

import java.util.Hashtable;

public class demo {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println("start");

        // set up test board
        boolean[] test1 = new boolean[boardSquareCount];
        test1[27] = true;
        test1[36] = true;
        test1[18] = true; test1[19] = true; test1[43] = true; test1[44] = true;
        boolean[] test2 = new boolean[boardSquareCount];
        test2[35] = true;
        test2[28] = true;
        test2[26] = true; test2[29] = true; test2[30] = true; test2[34] = true;

        long test3 = 81644180471808L;

        Hashtable h = new Hashtable(10000000);
        for (int i = 0; i < 100000000; i++) {
            //GreedyEvaluation.evaluate(test1, test2); // 100M: 4301 ms
            //MoveFinder.findAvailableMoves(test1, test2, true); // 100M: 124000 ms (unoptimised)
            //MakeMoveBeta.makeMove(test1,test2, true, 42); // 100M: 26000 ms (and wrong answer)

            //MoveFinderFast.findAvailableMoves(test1, test2, true); // 100M: 18400 ms
            //MakeMove.makeMove(test1,test2, true, 42); // 100M: 2900 ms
            //StateHasher.Hash(test1, test2, true); // 100M: 5940 ms
            //StateHasher.Hash(test3, test3, true); // 100M: 7 ms
            //h.put(i, test3); // 100M:ch 13000 ms (10M in 1300 ms); eats memory/heap space errors when iterations 10M -> 100M.
        }

        System.out.println((System.currentTimeMillis() - start) + " ms duration");

        int[] availableMoves = MoveFinder.findAvailableMoves(test1, test2, false);
        boolean[] highlightMoves = new boolean[boardSquareCount];
        for (int availableMove : availableMoves) {
            highlightMoves[availableMove] = true;
        }

        BoardPrinter.printBoard(test1, test2, highlightMoves, true);
    }
}
