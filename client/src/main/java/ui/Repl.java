package ui;

public class Repl {
    static String authToken;
    public static String getAuth() {
        return authToken;
    }

    public static void setAuth(String newAuth) {
        authToken = newAuth;
    }
}
