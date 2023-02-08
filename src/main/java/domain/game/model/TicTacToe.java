package domain.game.model;

import client.Application;

import support.AIs.TicTacToe.AI;
import support.abstracts.AbstractGameBoard;
import support.enums.SceneEnum;

public class TicTacToe extends AbstractGameBoard<TicTacToe> {
    @Override
    public Integer getSizeX() {
        return 3;
    }

    @Override
    public Integer getSizeY() {
        return 3;
    }

    @Override
    public String getKey() {
        return "tic-tac-toe";
    }

    @Override
    public String getName() {
        return "Tic Tac Toe";
    }

    @Override
    public SceneEnum getScene() {
        return SceneEnum.TIC_TAC_TOE;
    }

    @Override
    public String getIconPath() {
        return Application.class.getResource("assets/icons/tic_tac_toe.png").toString();
    }

    @Override
    public void runAI() {
        if (this.isPlayerTurn()) {
            Integer index = new AI().getBestMoveBestScore(this.getBoard(), true)[0];

            this.doMove(index);
        }
    }
}
