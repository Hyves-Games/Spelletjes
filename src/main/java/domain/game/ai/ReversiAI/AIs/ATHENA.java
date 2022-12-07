package domain.game.ai.ReversiAI.AIs;

import domain.game.ai.ReversiAI.Helpers.MakeMoveFast;
import domain.game.ai.ReversiAI.SuperClasses.AI;

import java.util.concurrent.CountDownLatch;

import static domain.game.ai.ReversiAI.Constants.Constants.boardSquareCount;

class SearchCounter {
    private int[] wins = new int[boardSquareCount];
    private int[] losses = new int[boardSquareCount];;

    public synchronized void incrementWins(int move) {
        this.wins[move]++;
    }

    public synchronized void incrementLosses(int move) {
        this.losses[move]++;
    }

    public int getWins(int move) {
        return this.wins[move];
    }

    public int getLosses(int move) {
        return this.losses[move];
    }
}

class Worker extends Thread {
    SearchCounter sc;
    CountDownLatch cdl;

    public Worker(SearchCounter s, CountDownLatch l) {
        sc = s;
        cdl = l;
    }

    @Override public void run() {
        System.out.println("start");
        long start = System.currentTimeMillis();
        // set up test board
        boolean[] test1 = new boolean[boardSquareCount];
        test1[27] = true;
        test1[36] = true;
        test1[18] = true; test1[19] = true; test1[43] = true; test1[44] = true;
        boolean[] test2 = new boolean[boardSquareCount];
        test2[35] = true;
        test2[28] = true;
        test2[26] = true; test2[29] = true; test2[30] = true; test2[34] = true;
        for (int ii = 1; ii < 10000000; ii++) {
            MakeMoveFast.makeMove(test1, test2, true, 42);
            if (ii % 60 == 0) {
                sc.incrementWins(1);
            }
        }
        System.out.println("stop");
        System.out.println((System.currentTimeMillis() - start) + " ms duration thread");
        this.cdl.countDown();
    }
}

public class ATHENA extends AI {
    public static int getBestMove(boolean[] playerWhitePieces, boolean[] playerBlackPieces, boolean isWhiteTurn, int maxTimeSeconds, int headroomMilliSeconds) {
        return 0;
    }

    public String getAIName() {
        return "ATHENA AI (multithreaded)";
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("start m");

        int threadCount = 1;
        SearchCounter sc = new SearchCounter();
        CountDownLatch l = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            Thread t1 = new Worker(sc, l);
            t1.setPriority(9);
            t1.start();
        }

        l.await();

        System.out.println("search counter wins: " + sc.getWins(1));

        System.out.println((System.currentTimeMillis() - start) + " ms duration total");
    }
}
