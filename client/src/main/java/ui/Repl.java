package ui;

import java.util.Objects;
import java.util.Scanner;

public class Repl {
    static String authToken;
    static States state = States.LOGGEDOUT;
    public static LoggedInClient loggedInClient;
    public static LoggedOutClient loggedOutClient;

    public Repl(String serverURL) {
        loggedOutClient = new LoggedOutClient(serverURL);
        loggedInClient = new LoggedInClient(serverURL);
        authToken = "";
    }
    public static String getAuth() {
        return authToken;
    }
    public static void setAuth(String newAuth) {
        authToken = newAuth;
    }

    public void run() {
        System.out.println("Welcome to Chess!");
        Scanner scanner = new Scanner(System.in);
        var method = "";
        var userInput = "";
        System.out.println(loggedOutClient.help());
        while (!Objects.equals(userInput, "quit")) {
            if (state == States.LOGGEDOUT) {
                BaseClient.print();
                userInput = scanner.nextLine();
                try {
                    method = loggedOutClient.eval(userInput);
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_GREEN + method + "\n");
                }
                catch (Throwable e) {
                    var message = e.toString();
                    System.out.print(message);
                }
                System.out.println();
            }
            if (state == States.LOGGEDIN) {
                System.out.println(loggedInClient.help());
                BaseClient.print();
                userInput = scanner.nextLine();
                try {
                    method = loggedInClient.eval(userInput);
                    System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + method + "\n");
                }
                catch (Throwable e) {
                    var message = e.toString();
                    System.out.print(message);
                }
                System.out.println();
            }
        }
        System.exit(0);
    }

}
