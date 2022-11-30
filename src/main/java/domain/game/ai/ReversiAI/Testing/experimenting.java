package domain.game.ai.ReversiAI.Testing;

import static domain.game.ai.ReversiAI.Constants.Constants.*;
import domain.game.ai.ReversiAI.Helpers.*;
import domain.game.ai.ReversiAI.Heuristics.GreedyEvaluation;
import domain.game.ai.ReversiAI.Board.*;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Hashtable;

public class experimenting {
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

        //Hashtable h = new Hashtable(40000000);
        for (int i = 0; i < 100000000; i++) {
            //GreedyEvaluation.evaluate(test1, test2, true); // 100M: 4301 ms (boolean array)
            //GreedyEvaluation.evaluate(test3, test3, true); // 100M: 4200 ms (long type)

            //MoveFinder.findAvailableMoves(test1, test2, true); // 100M: 128000 ms (unoptimised)
            //MoveFinderFast.findAvailableMoves(test1, test2, true); // 100M: 14700 ms (optimised) / 17900 ms
            //MoveFinderFast.findAvailableMoves(test3, test3, true); // 100M: 8400 ms (board mostly empty) / 5500 ms without conversion step

            //MakeMove.makeMove(test1,test2, true, 42); // 100M: 2900 ms

            //StateHasher.Hash(test1, test2, true); // 100M: 5940 ms
            //StateHasher.Hash(test3, test3, true); // 100M: 7 ms
            //h.put(i, test3); // 100M: 7100ms when combined with fast hasher function and greedy evaluation (net: 100M in ~2800 ms)

            //long hash = StateHasher.Hash(test3, test3, true);
            //h.put(hash, GreedyEvaluation.evaluate(test1, test2));

        }

        System.out.println((System.currentTimeMillis() - start) + " ms duration");

        int[] availableMoves = MoveFinder.findAvailableMoves(test1, test2, true);
        int[] availableMoves2 = MoveFinderFast.findAvailableMoves(test1, test2, true);

        boolean[] highlightMoves = new boolean[boardSquareCount];
        for (int availableMove : availableMoves2) {
            highlightMoves[availableMove] = true;
        }
        
        //BoardPrinter.printBoard(test1, test2, highlightMoves, true);
    }
}
