package support.abstracts;

import com.google.gson.JsonObject;
import support.enums.ServerResponseEnum;

public class AbstractServerResponse {
    private JsonObject data;
    private ServerResponseEnum type;

    public AbstractServerResponse(JsonObject data, ServerResponseEnum type) {
        this.data = data;
        this.type = type;
    }

    public JsonObject getData() {
        return this.data;
    }
    public ServerResponseEnum getType() {
        return this.type != null ? this.type : ServerResponseEnum.NONE;
    }
}
