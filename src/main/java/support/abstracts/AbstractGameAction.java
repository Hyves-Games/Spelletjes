package Support.Abstracts;

import Domain.Player.Model.Player;
import Support.Helpers.Auth;

public abstract class AbstractGameAction extends AbstractAction {
    protected Player<?> player = Auth.getPlayer();
}
