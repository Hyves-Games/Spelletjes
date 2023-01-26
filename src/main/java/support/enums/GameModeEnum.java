package support.enums;

import domain.game.model.Game;
import domain.log.helpers.LogHandler;
import domain.log.model.GameLog;
import domain.player.model.AI;
import support.helpers.Auth;

public enum GameModeEnum {
    PVP(SceneEnum.WAIT_ROOM),
    PVA(SceneEnum.WAIT_ROOM_CHALLENGE),
    AVA(SceneEnum.WAIT_ROOM_TOURNAMENT);

    private final SceneEnum scene;

    GameModeEnum(SceneEnum scene) {
        this.scene = scene;
    }

    public void create(Boolean createAI, boolean subscribe) {
        Game game = Auth.getLastGame().create();

        GameLog log = new GameLog();

        log.setGame(game.getGameBoard().getGameEnum());
        log.setGameMode(this);
        log.setPlayer(Auth.getPlayer());

        game.setAuthPlayer();

        if (createAI && this.equals(PVA)) {
            AI ai = game.setAIPlayer();

            log.setOpponent(ai);
        }

        if (subscribe && this.equals(PVP)) {
            game.searchGame();

            // IMPLEMENT logger
        }

        log.save();

        LogHandler.setLog(log);

        this.scene.switchTo();
    }

//    public void create() {
//        this.create(false, true);
//    }
}
