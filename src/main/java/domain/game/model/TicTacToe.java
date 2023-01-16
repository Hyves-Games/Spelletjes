package domain.game.model;

import client.Application;

import domain.game.ai.TicTacToeAI;
import support.abstracts.AbstractGameBoard;
import support.enums.SceneEnum;

public class TicTacToe extends AbstractGameBoard<TicTacToe> {
    public TicTacToe() {this.generate(9);}

    @Override
    public String getKey() {
        return "tic-tac-toe";
    }

    @Override
    public SceneEnum getScene() {
        return SceneEnum.TIC_TAC_TOE;
    }

    @Override
    public String getName() {
        return "Tic Tac Toe";
    }

    @Override
    public String getIconPath() {
        return Application.class.getResource("assets/icons/tic_tac_toe.png").toString();
    }

    @Override
    public void runAI() {
        if (this.isPlayerTurn()) {
            Integer index = new TicTacToeAI().getBestMoveBestScore(this.getBoard(), true)[0];

            this.doMove(index);
        }
    }
}
