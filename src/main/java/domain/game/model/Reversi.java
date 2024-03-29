package domain.game.model;

import client.Application;
import support.ais.Reversi.Tournament;
import support.ais.Reversi.Board.BoardPosition;
import support.ais.Reversi.Converters.IntArrayToLong;
import support.ais.Reversi.Converters.LongToBoolArray;
import support.ais.Reversi.MoveLogic.MakeMove;
import domain.player.model.Player;
import org.jetbrains.annotations.NotNull;
import support.abstracts.AbstractGameBoard;
import support.enums.SceneEnum;

public class Reversi extends AbstractGameBoard<Reversi> {
    @Override
    public Integer getSizeX() {
        return 8;
    }

    @Override
    public Integer getSizeY() {
        return 8;
    }

    @Override
    public String getKey() {
        return "reversi";
    }

    @Override
    public String getName() {
        return "Reversi";
    }

    @Override
    public SceneEnum getScene() {
        return SceneEnum.REVERSI;
    }

    @Override
    public String getIconPath() {
        return Application.class.getResource("assets/icons/reversi.png").toString();
    }

    @Override
    public void start(@NotNull Player<?> player, @NotNull Player<?> opponent) {
        super.start(player, opponent);

        this.board.set(27, this.isStarter() ? -1 : 1);
        this.board.set(28, this.isStarter() ? 1 : -1);
        this.board.set(35, this.isStarter() ? 1 : -1);
        this.board.set(36, this.isStarter() ? -1 : 1);
    }

    @Override
    protected void runLogic(Integer index, Integer value) {
        BoardPosition board = MakeMove.makeMove(this.getBoard(), value == 1, index, this.getEndState());

        for (int i = 0; i < 64; i++) {
            if (LongToBoolArray.convert(board.playerWhitePieces)[i]) {
                this.board.set(i, 1);
            } else if (LongToBoolArray.convert(board.playerBlackPieces)[i]) {
                this.board.set(i, -1);
            }
        }
    }

    @Override
    public void runAI() {
        long playerPieces = IntArrayToLong.convert(this.getBoard(), 1);
        long opponentPieces = IntArrayToLong.convert(this.getBoard(), -1);
        if (this.isPlayerTurn()) {
            Integer index = new Tournament().getBestMove(playerPieces, opponentPieces, isPlayerTurn());
            this.doMove(index);
        }
    }
}
