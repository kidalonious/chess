package server.requests;


import com.google.gson.Gson;
import spark.*;

public class baseRequest {
    static Gson serializer = new Gson();

    public static String convertToString(spark.Request req) {
        return req.body();
    }

}
