package ui;

import java.util.Scanner;

public class Repl {
    static String authToken;
    static States state = States.LOGGEDOUT;
    public static LoggedInClient loggedInClient;
    public static LoggedOutClient loggedOutClient;
    public static String getAuth() {
        return authToken;
    }

    public static void setAuth(String newAuth) {
        authToken = newAuth;
    }

    public void run() {
        System.out.println("Welcome to Chess!");
        Scanner scanner = new Scanner(System.in);
        var userInput = "";
        while (userInput != "quit") {

        }
    }
}
