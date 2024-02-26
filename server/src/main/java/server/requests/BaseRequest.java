package server.requests;


import com.google.gson.Gson;

public class BaseRequest {
    static Gson serializer = new Gson();

    public static String convertToString(spark.Request req) {
        return req.body();
    }

}
