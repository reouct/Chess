package websocket;

import com.google.gson.Gson;
import websocket.commands.LeaveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade {

    CommandHandler commandHandler;
    Session session;

    public WebSocketFacade(int port, CommandHandler commandHandler) {
        try {
            String url = "ws://localhost:"+port;
            URI socketURI = new URI(url + "/connect");
            this.commandHandler = commandHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    switch (notification.getServerMessageType()) {
                        case NOTIFICATION -> notification = new Gson().fromJson(message, NotificationMessage.class);
                        case LOAD_GAME -> notification = new Gson().fromJson(message, LoadGameMessage.class);
                        case ERROR -> notification = new Gson().fromJson(message, ErrorMessage.class);
                    }
                    commandHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public WebSocketFacade(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void leave(String auth, Integer gameID) throws IOException {
        try {
            UserGameCommand command = new LeaveCommand(auth, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(command));
            this.session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
