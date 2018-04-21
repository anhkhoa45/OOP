package socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.*;

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
        switch (message.getAction()){
            case CREATE_GAME:
                onCreateGame(message, userSession);
                System.out.println("ACTION_CREATE_GAME");
                break;
            case JOIN_GAME:
                onJoinGame(message, userSession);
                System.out.println("ACTION_JOIN_GAME");
                break;
            case ANSWER_QUESTION:
                onAnswer(message, userSession);
                System.out.println("ACTION_ANSWER");
                break;
            case GET_LIST_GAMES:
                onGetListGame(message, userSession);
                System.out.println("ACTION_GET_LIST_GAMES");
                break;
            case SET_GAME_MODE:
                onSetGameMode(message, userSession);
                System.out.println("ACTION_SET_GAME_MODE");
                break;
            case SET_GAME_CHARACTER:
                onSetGameCharacter(message, userSession);
                System.out.println("ACTION_SET_GAME_CHARACTER");
                break;
            case LEAVE_GAME:
                onLeaveGame(message, userSession);
                System.out.println("ACTION_LEAVE_GAME");
                break;
            default:

        }
    }

    private void onJoinGame(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        Gson gson = new Gson();
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.JOIN_GAME);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            game.setGuest(player);

            content.add("game", gson.toJsonTree(game));
            response.setStatus(200);
            response.setContent(content);
        } catch (Exception e){
            e.printStackTrace();
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onCreateGame(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.CREATE_GAME);

        Game game = new Game(player);
        GameServer.games.put(game.getId(), game);
        game.setTimeStarted(System.nanoTime());
        content.addProperty("game_id", game.getId());
        response.setStatus(200);
        response.setContent(content);

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onAnswer(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.ANSWER_QUESTION);

        String answer = message.getContent().get("answer").getAsString();
        int score = player.answerQuestion(answer);

        content.addProperty("score", score);
        response.setStatus(200);
        response.setContent(content);

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onGetListGame(Message message, Session userSession) {
        Gson gson = new Gson();
        Message response = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.GET_LIST_GAMES);

        try {
            content = gson.toJsonTree(games).getAsJsonObject();
            response.setContent(content);
            response.setStatus(200);
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onSetGameMode(Message message, Session userSession) {
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
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onSetGameCharacter(Message message, Session userSession) {
        Gson gson = new Gson();
        Player player = players.get(userSession.getId());
        Message response = new Message();
        Message rivalMessage = new Message();
        JsonObject content = new JsonObject();
        JsonObject rivalMessageContent = new JsonObject();

        response.setAction(GameAction.SET_GAME_CHARACTER);
        rivalMessage.setAction(GameAction.SET_RIVAL_CHARACTER);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            int characterType = message.getContent().get("character").getAsInt();
            Game game = games.get(gameId);
            AttackPlayer attackPlayer;

            switch (characterType) {
                case GameCharacter.KNIGHT:
                    attackPlayer = new KnightPlayer(player);
                    break;
                case GameCharacter.MEDUSA:
                    attackPlayer = new MedusaPlayer(player);
                    break;
                case GameCharacter.HOT_GIRL:
                    attackPlayer = new HotgirlPlayer(player);
                    break;
                case GameCharacter.DRACULA:
                    attackPlayer = new DracularPlayer(player);
                    break;
                default:
                    throw new Exception("Character type not found");
            }

            content.addProperty("character_type", characterType);
            content.add("character", gson.toJsonTree(attackPlayer));
            response.setContent(content);
            response.setStatus(200);

            rivalMessageContent.addProperty("character_type", characterType);
            rivalMessageContent.add("character", gson.toJsonTree(attackPlayer));
            rivalMessage.setContent(content);
            rivalMessage.setStatus(200);

            if(game.checkMaster(player)){
                game.setMaster(attackPlayer);
                game.getGuest().getSession().getAsyncRemote().sendObject(rivalMessage);
            } else {
                game.setGuest(attackPlayer);
                game.getMaster().getSession().getAsyncRemote().sendObject(rivalMessage);
            }


        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onLeaveGame(Message message, Session userSession) {
        Player player = players.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.LEAVE_GAME);

        try {
            players.remove(userSession.getId());
            int gameId = message.getContent().get("game_id").getAsInt();

            for (Game g : games.values()) {
                if (g.checkMaster(player)) {
                    if (!g.start()) {
                        games.remove(g.getId());
                        players.remove(g.getGuest());
                    }
                    break;
                }
            }
            content.addProperty("game_id", gameId);
            content.addProperty("player_id", player.getId());
            response.setStatus(200);
            response.setContent(content);
        } catch (Exception e){
            content.addProperty("message", e.getMessage());
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private boolean isGameOver(Message message) {
        //neu da start thanh cong nhung 2 nguoi k choi hoac bang diem nhau qua lau thi sau 3p server phai tu goi ham nay

        int gameId = message.getContent().get("game_id").getAsInt();
        Game game = games.get(gameId);
        return game.isGameOver();
    }

}