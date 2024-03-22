package ui;

public class BaseClient {
    public BaseClient() {

    }

    public BaseClient(String serverURL) {
        var server = new ServerFacade(serverURL);
    }
}
