package Domain.Player.Actions;

import Support.Abstracts.AbstractAction;
import Support.Enums.SceneEnum;

public class ChallengeCancelledAction extends AbstractAction {
    public ChallengeCancelledAction() {
        this.handler();
    }

    protected void handler() {
        SceneEnum.LOBBY.switchTo();
    }
}
