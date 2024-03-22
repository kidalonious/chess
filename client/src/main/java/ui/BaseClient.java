package ui;

public class BaseClient {
    private String username = null;
    ServerFacade server;
    States state = States.LOGGEDOUT;
    private String serverUrl;

    public BaseClient() {

    }

    public BaseClient(String serverURL) {
        server = new ServerFacade(serverURL);
    }
    static void print() {
        System.out.print("\n" + EscapeSequences.RESET_TEXT_COLOR + ">>> " + EscapeSequences.SET_TEXT_COLOR_RED);
    }
}
