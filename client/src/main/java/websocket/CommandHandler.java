package websocket;

import websocket.messages.ServerMessage;

public interface CommandHandler {
    void notify(ServerMessage notification);
}
