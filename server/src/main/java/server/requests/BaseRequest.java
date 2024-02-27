package server.requests;


import com.google.gson.Gson;

public class BaseRequest {
    public static Gson gson = new Gson();

    public static String convertToString(spark.Request req) {
        return req.body();
    }

}
