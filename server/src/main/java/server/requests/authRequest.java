package server.requests;
import spark.*;

public class authRequest extends baseRequest {
    public static Request convertToReq(String string) {
        Request request = serializer.fromJson(string, Request.class);
        return request;
    }
}
