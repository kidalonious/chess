package server.requests;
import spark.*;

public class authRequest extends baseRequest {
    public static Request convertToReq(String string) {
        return serializer.fromJson(string, Request.class);
    }
}
