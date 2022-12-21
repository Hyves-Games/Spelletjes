package support.enums;

import domain.game.model.Game;
import support.helpers.Auth;

public enum GameModeEnum {
    PVP(SceneEnum.WAIT_ROOM),
    PVA(SceneEnum.WAIT_ROOM_CHALLENGE);

    private final SceneEnum scene;

    GameModeEnum(SceneEnum scene) {
        this.scene = scene;
    }

    public void create(Boolean createAI, boolean subscribe) {
        Game game = Auth.getLastGame().create();

        game.setAuthPlayer();

        if (createAI && this.equals(PVA)) {
            game.setAIPlayer();
        }

        System.out.println(this);
        if (subscribe && this.equals(PVP)) {
            game.searchGame();
        }

        this.scene.switchTo();
    }

//    public void create() {
//        this.create(false, true);
//    }
}
