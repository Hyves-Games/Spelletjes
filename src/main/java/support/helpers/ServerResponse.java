package support.helpers;

import com.google.gson.JsonObject;
import support.enums.ServerResponseEnum;

public class ServerResponse {
    private JsonObject data;
    private ServerResponseEnum type;

    public ServerResponse(JsonObject data, ServerResponseEnum type) {
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
