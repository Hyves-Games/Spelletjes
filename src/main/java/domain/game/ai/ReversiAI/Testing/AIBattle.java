package domain.game.ai.ReversiAI.Testing;

import domain.game.ai.ReversiAI.AIs.*;
import domain.game.ai.ReversiAI.Converters.LongToBoardIntArray;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinder;
import domain.game.ai.ReversiAI.MoveLogic.MoveFinderFast;
import domain.game.ai.ReversiAI.Helpers.PieceCounter;
import domain.game.ai.ReversiAI.Interfaces.AI;
import domain.game.ai.ReversiAI.Board.*;
import domain.log.model.GameLog;
import domain.log.model.GameSetLog;
import domain.player.model.Player;
import domain.player.query.PlayerQuery;
import domain.simulation.model.Simulation;
import domain.simulation.model.SimulationRound;
import support.enums.*;

import java.util.Arrays;

public class AIBattle {
    public static void main(String[] args) throws Exception {
        AIBattle sim = new AIBattle(
                new PlayerQuery().findOneOrCreate(new ATHENA(3)),
                new PlayerQuery().findOneOrCreate(new ATHENA(5))
        );

        sim.run();
    }

    public AIBattle(Player<?> player, Player<?> opponent) {
        this.player = player;
        this.opponent = opponent;
    }

    private final int totalRounds = 100;
    private int lastFilledCount = 0;
    private final int barLength = 40;
    private int roundCount = 1;

    private int AIOneWinCount = 0;
    private int AITwoWinCount = 0;

    private Player<?> player;
    private Player<?> opponent;

    private final SimulationRound simulationRound = new SimulationRound(totalRounds, player, opponent).save();

    private void createSimulation(GameLog gameLog) {
        Simulation simulation = new Simulation();
        simulation.setSimulationRound(simulationRound);
        simulation.setGameLog(gameLog);
        simulation.save();
    }

    private void startSimulation(Player<?> whiteAI, Player<?> blackAI, GameLog log) throws Exception {
        // Set up board, default position
        long playerWhitePieces = 0b0000000000000000000000000001000000001000000000000000000000000000L;
        long playerBlackPieces = 0b0000000000000000000000000000100000010000000000000000000000000000L;

        // Play a round
        boolean isWhiteTurn = false;
        boolean wasPass = false;
        int randomMovesLeft = 4;
        while (true) {
            int[] moves = MoveFinderFast.findAvailableMoves(playerWhitePieces, playerBlackPieces, isWhiteTurn);

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
                AI selectedAI = isWhiteTurn ? whiteAI.getAI() : blackAI.getAI();
                if (randomMovesLeft > 0) {
                    randomMovesLeft += -1;
                    selectedAI = new RandomAI();
                }

                wasPass = false;
                int bestMove = selectedAI.getBestMove(playerWhitePieces, playerBlackPieces, isWhiteTurn);

                BoardPosition newBoard = MakeMove.makeMove(playerWhitePieces, playerBlackPieces, isWhiteTurn, bestMove);

                // do game set log
                GameSetLog logMove = new GameSetLog();
                logMove.setBoard(LongToBoardIntArray.convert(newBoard.playerWhitePieces, newBoard.playerBlackPieces, 1, -1));
                logMove.setGameLog(log);
                if (isWhiteTurn) {
                    logMove.setPlayer(whiteAI);
                } else {
                    logMove.setPlayer(blackAI);
                }

                logMove.save();

                if ((newBoard.playerWhitePieces == playerWhitePieces) || (newBoard.playerBlackPieces == playerBlackPieces)) {
                    System.out.println("\nBoard before:");
                    BoardPrinter.printBoard(playerWhitePieces, playerBlackPieces);
                    System.out.println("white board, black board: " + playerWhitePieces + ", " + playerBlackPieces);
                    throw new Exception("Board change incorrect! game: " + roundCount + ", move: " + bestMove + ", white's turn: " + isWhiteTurn + ", available moves: " + Arrays.toString(moves));
                }
                playerWhitePieces = newBoard.playerWhitePieces;
                playerBlackPieces = newBoard.playerBlackPieces;
            }

            isWhiteTurn = !isWhiteTurn;
        }

        // Determine winner and add to count
        if (PieceCounter.countPieces(playerWhitePieces) > PieceCounter.countPieces(playerBlackPieces)) {
            log.setState(GameEndStateEnum.WIN);

            // White wins
            if (roundCount % 2 == 1) {
                AIOneWinCount++;
            } else {
                AITwoWinCount++;
            }
        } else if (PieceCounter.countPieces(playerWhitePieces) < PieceCounter.countPieces(playerBlackPieces)) {
            log.setState(GameEndStateEnum.LOSS);

            // Black wins
            if (roundCount % 2 == 1) {
                AITwoWinCount++;
            } else {
                AIOneWinCount++;
            }
        } else {
            // Draw
            log.setState(GameEndStateEnum.DRAW);
        }

        // Update console
        int filledCount = (roundCount * barLength) / totalRounds;
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
        roundCount++;
    }

    public void run() throws Exception {

        // Progress bar
        System.out.println("Running " + totalRounds + " games...");
        System.out.print("[");
        for (int b = 0; b < barLength; b++) {
            System.out.print("░");
        }
        System.out.print("]");

        long start = System.currentTimeMillis();
        for (int i = 1; i <= totalRounds; i++) {
            GameLog log = new GameLog();
            log.setGame(GameEnum.REVERSI);
            log.setGameMode(GameModeEnum.AVA);


            if (roundCount % 2 == 1) {
                startSimulation(player, opponent, log);

                log.setPlayer(player);
                log.setOpponent(opponent);
            } else {
                startSimulation(opponent, player, log);

                log.setPlayer(opponent);
                log.setOpponent(player);
            }

            log.save();

            createSimulation(log);
        }

        // Print results
        System.out.println("\n" + player.getUsername() + " won " + AIOneWinCount + " games.\n" + opponent.getUsername() + " won " + AITwoWinCount + " games.");
        System.out.println((System.currentTimeMillis() - start) + " ms duration");
    }
}
