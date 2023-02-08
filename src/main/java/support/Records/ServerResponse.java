package Support.Records;

import com.google.gson.JsonObject;
import Support.Enums.ServerResponseEnum;

public record ServerResponse(JsonObject data, ServerResponseEnum type) {
    @Override
    public ServerResponseEnum type() {
        return this.type != null ? this.type : ServerResponseEnum.NONE;
    }
}

