package websocket.messages;

public class NotificationMessage extends ServerMessage{
    private String message;

    public NotificationMessage(String message) {
        this.message = message;
        this.serverMessageType = ServerMessageType.NOTIFICATION;
    }

    public String getMessage() {
        return message;
    }
}
