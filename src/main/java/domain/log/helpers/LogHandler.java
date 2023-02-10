package domain.log.helpers;

import domain.log.model.GameLog;
import domain.log.model.GameSetLog;
import domain.player.model.Player;
import support.enums.GameEndStateEnum;

import java.util.ArrayList;

public class LogHandler {
    private static GameLog log;

    public static GameLog getLog() {
        return log;
    }

    public static void setLog(GameLog log) {
        LogHandler.log = log;
    }

    public static void updateBoard(ArrayList<Integer> board) {
        log.setBoard(board);

        log.save();
    }

    public static void updateGameState(GameEndStateEnum state) {
        log.setState(state);

        log.save();
    }

    public static void makeMove(Player<?> player, ArrayList<Integer> board) {
        GameSetLog log = new GameSetLog();

        log.setGameLog(LogHandler.log);
        log.setPlayer(player);
        log.setBoard(board);

        log.save();
    }
}
