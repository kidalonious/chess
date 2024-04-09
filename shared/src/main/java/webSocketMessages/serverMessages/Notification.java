package webSocketMessages.serverMessages;

import com.google.gson.Gson;

public record Notification(String message) {

    public String toString() {
        return new Gson().toJson(this);
    }
}
