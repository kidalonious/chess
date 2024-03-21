package ui;

public class Repl {
    static String authToken;
    static States state = States.LOGGEDOUT;
    public static String getAuth() {
        return authToken;
    }

    public static void setAuth(String newAuth) {
        authToken = newAuth;
    }

    public void run() {
        System.out.println("Welcome to Chess!");
    }
}
