package domain.player.actions;

import support.abstracts.AbstractAction;
import support.enums.SceneEnum;

public class ChallengeCancelledAction extends AbstractAction {
    public ChallengeCancelledAction() {
        this.handler();
    }

    protected void handler() {
        SceneEnum.LOBBY.switchTo();
    }
}
