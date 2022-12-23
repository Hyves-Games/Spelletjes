package domain.game.ai.ReversiAI.Testing;

import static domain.game.ai.ReversiAI.Constants.Constants.*;

import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Converters.BoolArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.Converters.LongToMoves;
import domain.game.ai.ReversiAI.Helpers.*;
import domain.game.ai.ReversiAI.Masks.BitMasks;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MakeMoveFast;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinder;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;

import java.util.Arrays;
import java.util.BitSet;

public class experimenting {
    public static void main(String[] args) {
        // set up test board
        boolean[] test1 = new boolean[boardSquareCount];
        test1[27] = true;
        test1[36] = true;
        test1[18] = true; test1[19] = true; test1[43] = true; test1[44] = true;
        boolean[] test2 = new boolean[boardSquareCount];
        test2[35] = true;
        test2[28] = true;
        test2[26] = true; test2[29] = true; test2[30] = true; test2[34] = true;

        BitSet b = new BitSet(64);
        b.set(27); b.set(36); b.set(18); b.set(19); b.set(43); b.set(44);

        long test3 = 0b0000000000000000001011000000000000100010000110000100000000000000L;
        long test4 = 0b0000000000000000000100000001100000011100001001000000000000000000L;

        //Hashtable h = new Hashtable(40000000);
        //int[] tempMoves = new int[64];

        System.out.println("start");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            //MoveFinder.findAvailableMoves(test1, test2, true); // 100M: 128000 ms (unoptimised)

            //MoveFinderFast.findAvailableMoves(test1, test2, true); // 100M: 13200/14400 ms (optimised) / 17900 ms
            //MoveFinderFast.findAvailableMoves(test3, test4, false); // 100M: 8000 ms

            //MakeMove.makeMove(test1, test2, true, 43); // 100M: 2800 ms
            //MakeMove.makeMove(test3, test4, true, 13); // 100M: 11000 ms
            //MakeMoveFast.makeMove(test3, test4, false, 13); // 100M: 4600 ms

            BoolArrayToLong.convert(test1); // 100M: 1850 ms

            //StateHasher.Hash(test1, test2, true); // 100M: 5940 ms
            //StateHasher.Hash(test3, test3, true); // 100M: 7 ms
            //h.put(i, test3); // 100M: 7100ms when combined with fast hasher function and greedy evaluation (net: 100M in ~2800 ms)

            //long hash = StateHasher.Hash(test3, test3, true);
            //h.put(hash, GreedyEvaluation.evaluate(test1, test2));

            //LongToMoves.convert(test3); // 3100 ms

            //for (int ii = 0; ii < 64; ii++) {
                //long l = test3 & BitMasks.bitMaskSingleBit[ii];
                //int c = Long.bitCount(l);
            //}
        }
        System.out.println((System.currentTimeMillis() - start) + " ms duration");

        //BoardPrinter.printBoard(test3, test4);
        //BoardPosition resolved = MakeMoveFast.makeMove(test3, test4, false, 13);
        //BoardPrinter.printBoard(resolved.playerWhitePieces, resolved.playerBlackPieces);

        //System.out.println("cheese: " + Arrays.toString(LongToBoolArray.convert(0b1100000100000000000000000000000000000000000000000000000010000001L)));



        //int[] availableMoves2 = MoveFinder.findAvailableMoves(test1, test2, true);
        //int[] availableMoves = MoveFinderFast.findAvailableMoves(test1, test2, true);

        long playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
        long playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;
        int[] availableMoves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, true);

        //System.out.println("bitset: " + b);

        //BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces, availableMoves);
        //BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);
    }
}
