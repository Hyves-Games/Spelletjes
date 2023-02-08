package support.abstracts;

import domain.player.model.Player;
import support.helpers.Auth;

public abstract class AbstractGameAction extends AbstractAction {
    protected Player<?> player = Auth.getPlayer();
}
