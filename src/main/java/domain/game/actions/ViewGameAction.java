package domain.game.actions;

import com.google.gson.JsonObject;
import support.abstracts.AbstractAction;
import support.enums.SceneEnum;
import support.helpers.SceneSwitcher;

public class ViewGameAction extends AbstractAction {
    private final JsonObject data;

    public ViewGameAction(JsonObject data) throws Exception {
        this.data = data;

        this.handler();
    }

    @Override
    protected void handler() throws Exception {
        System.out.println(this.data.toString());

        SceneSwitcher.getInstance().change(SceneEnum.TIC_TAC_TOE);
    }
}
