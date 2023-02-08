package support.AIs.Reversi.Testing;

import static support.AIs.Reversi.Constants.Constants.*;

import support.AIs.Reversi.Converters.LongToBoolArray;
import support.AIs.Reversi.Converters.LongToMoves;
import support.AIs.Reversi.Helpers.*;
import support.AIs.Reversi.MoveLogic.*;

import java.util.Arrays;
import java.util.BitSet;

public class Experimenting {
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

        //long test5 = 2314885805157384192L;
        long test5 = 0b0010000000100000001000000110000000000000000000000000000000000000L;
        //long test6 = -9205357509026249728L;
        long test6 = 0b1000000001000000000000000001111000011100000001000000010000000000L;

        //Hashtable h = new Hashtable(40000000);
        //int[] tempMoves = new int[64];

        System.out.println("start");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            //MoveFinder.findAvailableMoves(test1, test2, true); // 100M: 128000 ms (unoptimised)

            //MoveFinderFast.findAvailableMoves(test1, test2, true); // 100M: 13100 ms
            //BoolArrayToLong.convert(test1); // 100M: 1900 ms

            //MoveFinderFast.findAvailableMoves(test3, test4, false); // 100M: 8700 ms
            //LongToBoolArray.convert(test4); // 100M: 2500 ms
            LongToMoves.convert(test4); // 100M : 2750 ms

            //MoveFinderExperimental.findAvailableMoves(test5, test6, false); // 100M: 7100 ms
            //MoveFinderExperimental.findAvailableMoves(test5, test6, false, true); // 100M: 5000 ms
            //MoveFinderFast.findAvailableMoves(test3, test4, false, true); // 100M: 4800 ms

            //LongToMoves.convert(test3); // 3500 ms

            //MakeMove.makeMove(test1, test2, true, 43); // 100M: 3100 ms
            //MakeMove.makeMove(test3, test4, true, 13); // 100M: 14750 ms
            //MakeMoveFast.makeMove(test3, test4, false, 13); // 100M: 4550 ms

            //StateHasher.Hash(test1, test2, true); // 100M: 5950 ms
            //StateHasher.Hash(test3, test3, true); // 100M: 7 ms
            //h.put(i, test3); // 100M: 2800 ms?
        }
        System.out.println((System.currentTimeMillis() - start) + " ms duration");

        BoardPrinter.printBoard(test5, test6);
        //BoardPosition resolved = MakeMoveFast.makeMove(test3, test4, false, 13);
        //BoardPrinter.printBoard(resolved.playerWhitePieces, resolved.playerBlackPieces);

        //int[] availableMoves2 = MoveFinder.findAvailableMoves(test1, test2, true);
        //int[] availableMoves = MoveFinderFast.findAvailableMoves(test1, test2, true);

        //long playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
        //long playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;
        //int[] availableMoves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, true);

        System.out.println("available moves experimental: " + MoveFinderExperimental.findAvailableMoves(test5, test6, false, true));
        System.out.println("available moves fast: " + Arrays.toString(MoveFinderFast.findAvailableMoves(test5, test6, false)));
        System.out.println("available moves fast: " + MoveFinderFast.findAvailableMoves(test5, test6, false, true));
        System.out.println("available moves slow: " + Arrays.toString(MoveFinder.findAvailableMoves(LongToBoolArray.convert(test5), LongToBoolArray.convert(test6), false)));

        //System.out.println("bitset: " + b);

        //BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces, availableMoves);
        //BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);
        //BoardPrinter.printBoard(test3, test4, MoveFinderFast.findAvailableMoves(test3, test4, true));
    }
}
