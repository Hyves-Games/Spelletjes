package support.records;

import com.google.gson.JsonObject;
import support.enums.ServerResponseEnum;

public record ServerResponse(JsonObject data, ServerResponseEnum type) {
    @Override
    public ServerResponseEnum type() {
        return this.type != null ? this.type : ServerResponseEnum.NONE;
    }
}

