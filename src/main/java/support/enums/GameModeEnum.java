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

    public void create(Boolean createAI) {
        Game game = Auth.getLastGame().create();

        game.setAuthPlayer();

        if (createAI && this.equals(PVA)) {
            game.setAIPlayer();
        }

        this.scene.switchTo();
    }

    public void create() {
        this.create(true);
    }
}
