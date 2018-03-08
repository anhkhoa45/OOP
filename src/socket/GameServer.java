package socket;

import com.google.gson.JsonObject;
import model.Game;
import model.Player;

import javax.inject.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;

@ServerEndpoint(value="/game-server/{user_id}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
@Singleton
public class GameServer {
    private static final int ACTION_CREATE_GAME = 0;
    private static final int ACTION_JOIN_GAME = 1;
    private static final int ACTION_ANSWER = 2;
    private static final int ACTION_GET = 2;

    private static HashMap<String, Player> players = new HashMap<String, Player>();
    private static HashMap<Integer, Game> games = new HashMap<Integer, Game>();

    /**
     * Callback hook for Connection open events.
     *
     * This method will be invoked when a client requests for a
     * WebSocket connection.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession, @PathParam("user_id") int userId ) {
        System.out.println("New request received. Id: " + userSession.getId());
        Player player = new Player(userId, userSession);
        players.put(userSession.getId(), player);
    }

    /**
     * Callback hook for Connection close events.
     *
     * This method will be invoked when a client closes a WebSocket
     * connection.
     *
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        System.out.println("Connection closed. Id: " + userSession.getId());
        Player player = players.get(userSession.getId());
        player.leaveGame();
        players.remove(userSession.getId());
    }

    /**
     * Callback hook for Message Events.
     *
     * This method will be invoked when a client send a message.
     *
     * @param message The text message
     * @param userSession The session of the client
     */
    @OnMessage
    public void onMessage(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        Message response = new Message();
        JsonObject content = new JsonObject();

        switch (message.getAction()){
            case ACTION_CREATE_GAME:
                Game game = player.createGame();
                GameServer.games.put(game.getId(), game);
                content.addProperty("game_id", game.getId());
                response.setAction(ACTION_CREATE_GAME);
                response.setStatus(200);
                response.setContent(content);
                System.out.println("Game created");
                break;
            case ACTION_JOIN_GAME:
                break;
            case ACTION_ANSWER:
                String answer = message.getContent().get("answer").getAsString();
                int score = player.answerQuestion(answer);
                content.addProperty("score", score);
                response.setAction(ACTION_ANSWER);
                response.setStatus(200);
                response.setContent(content);
                break;
            default:

        }
//        System.out.println(response.getAction());
        userSession.getAsyncRemote().sendObject(response);
    }
}