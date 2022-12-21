package domain.game.model;

import client.Application;
import support.abstracts.AbstractGameBoard;
import support.enums.SceneEnum;

public class Reversi extends AbstractGameBoard<Reversi> {

    public Reversi() {
        // TODO: Starting moves [27: white, 28: black, 35: black, 36: white]
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
    public void runAI() {
        // Implement AI code
//        if (this.isPlayerTurn()) {
//            Integer index = new ReversiAI().getBestMoveBestScore(this.getBoard(), true)[0];
//
//            this.doMove(index);
//        }
    }
}
