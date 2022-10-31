package support.abstracts;

import com.google.gson.JsonObject;
import support.helpers.ServerResponseEnum;

public class AbstractServerResponse {
    private JsonObject data;
    private ServerResponseEnum response;

    public AbstractServerResponse(JsonObject data, ServerResponseEnum response) {
        this.data = data;
        this.response = response;
    }

    public JsonObject getData() {
        return this.data;
    }
    public ServerResponseEnum getResponse() {
        return this.response != null ? this.response : ServerResponseEnum.NONE;
    }
}
