package socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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
        //Player player = new Player(userId, userSession);
        //players.put(userSession.getId(), player);
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
        //player.leaveGame();
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
        Message response = new Message();

        switch (message.getAction()){
            case CREATE_GAME:
                response = this.onCreateGame(message, userSession);
                System.out.println("ACTION_CREATE_GAME");
                break;
            case JOIN_GAME:
                System.out.println("ACTION_JOIN_GAME");
                break;
            case ANSWER_QUESTION:
                response = this.onAnswer(message, userSession);
                System.out.println("ACTION_ANSWER");
                break;
            case GET_LIST_GAMES:
                response = onGetListGame(message, userSession);
                System.out.println("ACTION_GET_LIST_GAMES");
                break;
            case SET_GAME_MODE:
                response = onSetGameMode(message, userSession);
                System.out.println("ACTION_SET_GAME_MODE");
                break;
            default:

        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private Message onCreateGame(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        Game game = new Game(player);
        GameServer.games.put(game.getId(), game);
        content.addProperty("game_id", game.getId());
        response.setAction(GameAction.CREATE_GAME);
        response.setStatus(200);
        response.setContent(content);

        return response;
    }

    private Message onAnswer(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        String answer = message.getContent().get("answer").getAsString();
        int score = player.answerQuestion(answer);

        content.addProperty("score", score);
        response.setAction(GameAction.ANSWER_QUESTION);
        response.setStatus(200);
        response.setContent(content);

        return response;
    }

    private Message onGetListGame(Message message, Session userSession) {
        Message response = new Message();
        Gson gson = new Gson();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.GET_LIST_GAMES);

        try {
            content = gson.toJsonTree(games).getAsJsonObject();
            response.setContent(content);
            response.setStatus(200);
            return response;
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
            return response;
        }
    }

    private Message onSetGameMode(Message message, Session userSession) {
        Gson gson = new Gson();
        Message response = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.SET_GAME_MODE);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            int mode = message.getContent().get("mode").getAsInt();
            Game game = games.get(gameId);
            game.setMode(mode);

            content.add("game", gson.toJsonTree(game));
            response.setContent(content);
            response.setStatus(200);
            return response;
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
            return response;
        }
    }
}