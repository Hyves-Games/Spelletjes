package domain.game.model;

import client.Application;
import domain.game.ai.ReversiAI.Board.BoardPosition;
import domain.game.ai.ReversiAI.Converters.IntArrayToLong;
import domain.game.ai.ReversiAI.Converters.LongToBoolArray;
import domain.game.ai.ReversiAI.Helpers.BoardPrinter;
import domain.game.ai.ReversiAI.MoveLogic.MakeMove;
import domain.player.model.Player;
import org.jetbrains.annotations.NotNull;
import support.abstracts.AbstractGameBoard;
import support.enums.SceneEnum;

public class Reversi extends AbstractGameBoard<Reversi> {
    public Reversi() {
        this.generate(64);
    }

    @Override
    public String getKey() {
        return "reversi";
    }

    @Override
    public SceneEnum getScene() {
        return SceneEnum.REVERSI;
    }

    @Override
    public String getIconPath() {
        return Application.class.getResource("assets/icons/reversi.jpg").toString();
    }

    @Override
    public String getName() {
        return "Reversi";
    }


    @Override
    public void start(@NotNull Player<?> player, @NotNull Player<?> opponent) {
        this.board.set(27, this.getStarter() ? -1 : 1);
        this.board.set(28, this.getStarter() ? 1 : -1);
        this.board.set(35, this.getStarter() ? 1 : -1);
        this.board.set(36, this.getStarter() ? -1 : 1);

        super.start(player, opponent);
    }

    @Override
    protected void runLogic(Integer index, Integer value) {
        long playerPieces = IntArrayToLong.convert(this.getBoard(), 1);
        long opponentPieces = IntArrayToLong.convert(this.getBoard(), -1);

        BoardPosition board = MakeMove.makeMove(playerPieces, opponentPieces, value == 1, index);

        for (int i = 0; i < 64; i++) {
            if (LongToBoolArray.convert(board.playerWhitePieces)[i]) {
                this.board.set(i, 1);
            } else if (LongToBoolArray.convert(board.playerBlackPieces)[i]) {
                this.board.set(i, -1);
            }
        }

//        BoardPrinter.printBoard(board.playerWhitePieces, board.playerBlackPieces);
    }

    @Override
    public void runAI() {
        // Implement AI code
//        if (this.isPlayerTurn()) {
//            Integer index = new ReversiAI().getBestMoveBestScore(this.getBoard(), true)[0];
//
//            this.doMove(index);
//        }
    }
}
