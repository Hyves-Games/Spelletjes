package Domain.Game.Model;

import client.Application;

import Support.AIs.TicTacToe.AI;
import Support.Abstracts.AbstractGameBoard;
import Support.Enums.SceneEnum;

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
