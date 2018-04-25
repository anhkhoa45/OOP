//package socket;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.reflect.TypeToken;
//import model.*;
//
//import javax.inject.Singleton;
//import javax.websocket.OnClose;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import static model.Game.*;
//
//@ServerEndpoint(value = "/game-server/{user_id}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
//@Singleton
//public class GameServer {
//    private static HashMap<String, User> users = new HashMap<String, User>();
//    private static HashMap<Integer, Game> games = new HashMap<Integer, Game>();
//    private static List<Topic> topics = new ArrayList<>();
//
//    /**
//     * Callback hook for Connection open events.
//     * <p>
//     * This method will be invoked when a client requests for a
//     * WebSocket connection.
//     *
//     * @param userSession the userSession which is opened.
//     */
//    @OnOpen
//    public void onOpen(Session userSession, @PathParam("user_id") int userId) {
//        System.out.println("New request received. Id: " + userSession.getId());
//        User user = new User(userId, userSession);
//        users.put(userSession.getId(), user);
//    }
//
//    /**
//     * Callback hook for Connection close events.
//     * <p>
//     * This method will be invoked when a client closes a WebSocket
//     * connection.
//     *
//     * @param userSession the userSession which is opened.
//     */
//    @OnClose
//    public void onClose(Session userSession) {
//        System.out.println("Connection closed. Id: " + userSession.getId());
//        User user = users.get(userSession.getId());
//        users.remove(userSession.getId());
//        for (Game g : games.values()) {
//            if (g.checkMaster(user) || g.checkGuest(user)) {
//                leaveGame(g.getId(), userSession);
//                break;
//            }
//        }
//    }
//
//    /**
//     * Callback hook for Message Events.
//     * <p>
//     * This method will be invoked when a client send a message.
//     *
//     * @param message     The text message
//     * @param userSession The session of the client
//     */
//    @OnMessage
//    public void onMessage(Message message, Session userSession) {
//        switch (message.getAction()) {
//            case CREATE_GAME:
//                onCreateGame(message, userSession);
//                System.out.println("ACTION_CREATE_GAME");
//                break;
//            case JOIN_GAME:
//                onJoinGame(message, userSession);
//                System.out.println("ACTION_JOIN_GAME");
//                break;
//            case ANSWER_QUESTION:
//                onAnswer(message, userSession);
//                System.out.println("ACTION_ANSWER");
//                break;
//            case GET_LIST_GAMES:
//                onGetListGame(message, userSession);
//                System.out.println("ACTION_GET_LIST_GAMES");
//                break;
//            case SET_GAME_MODE:
//                onSetGameMode(message, userSession);
//                System.out.println("ACTION_SET_GAME_MODE");
//                break;
//            case SET_GAME_CHARACTER:
//                onSetGameCharacter(message, userSession);
//                System.out.println("ACTION_SET_GAME_CHARACTER");
//                break;
//            case START_GAME:
//                onStartGame(message, userSession);
//                System.out.println("ACTION_START_GAME");
//                break;
//            case GUEST_READY:
//                onReady(message, userSession);
//                System.out.println("ACTION_READY");
//                break;
//            case LEAVE_GAME:
//                onLeaveGame(message, userSession);
//                System.out.println("ACTION_LEAVE_GAME");
//                break;
//            case GET_GAME_STATE:
//                onGetGameState(message, userSession);
//                System.out.println("ACTION_GET_GAME_STATE");
//                break;
//            default:
//
//        }
//    }
//
//    private void onJoinGame(Message message, Session userSession) {
//        User user = users.get(userSession.getId());
//        Gson gson = new Gson();
//        JsonObject content = new JsonObject();
//        Message response = new Message();
//
//        response.setAction(GameAction.JOIN_GAME);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            Game game = games.get(gameId);
//            game.setUserGuest(user);
//
//            content.add("game", gson.toJsonTree(game));
//            response.setStatus(200);
//            response.setContent(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onCreateGame(Message message, Session userSession) {
//        User user = users.get(userSession.getId());
//        JsonObject content = new JsonObject();
//        Message response = new Message();
//
//        response.setAction(GameAction.CREATE_GAME);
//
//        Game game = new Game(user);
//        GameServer.games.put(game.getId(), game);
//        game.setUserMaster(user);
//
//        try {
//            content.add("game", game.getStateAsJson());
//            response.setStatus(200);
//            response.setContent(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//            GameServer.games.remove(game.getId());
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onAnswer(Message message, Session userSession) {
//        User user = users.get(userSession.getId());
//        JsonObject content = new JsonObject();
//        Message response = new Message();
//
//        response.setAction(GameAction.ANSWER_QUESTION);
//
//        try {
//            String answer = message.getContent().get("answer").getAsString();
//            int gameId = message.getContent().get("game_id").getAsInt();
//            Game game = games.get(gameId);
//            Answer a = new Answer(answer);
//            int score = game.getTopic().getWordScore(a.getWord());
//
//            switch (game.getMode()) {
//                case Game.MODE_NORMAL:
//                    a.setScore(score);
//                    if (game.getUserMaster().equals(user))
//                        game.getCharacterMaster().get(a);
//                    break;
//                case Game.MODE_ATTACK:
//                    AttackUser p;
//                    AttackUser opponent;
//                    if (game.getMaster().equals(user)) {
//                        opponent = (AttackUser) game.getGuest();
//                        p = (AttackUser) game.getMaster();
//                    } else {
//                        opponent = (AttackUser) game.getMaster();
//                        p = (AttackUser) game.getGuest();
//                    }
//
//                    if (opponent.checkDuplicateAnswer(a) || p.checkDuplicateAnswer(a)) {
//                        score = 0;
//                    }
//                    a.setScore(score);
//                    p.addAnswer(a);
//                    if (score != 0 || game.getMaster() instanceof MedusaUser || game.getGuest() instanceof MedusaUser) {
//                        p.attack(opponent);
//
//                        if (p.isDead() || opponent.isDead()) {
//                            game.setStatus(GAME_OVER);
//                        }
//                    }
//                    break;
//            }
//
//            response.setStatus(200);
//            response.setContent(content);
//        } catch (Exception e) {
//            e.printStackTrace();
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onGetListGame(Message message, Session userSession) {
//        Gson gson = new Gson();
//        Message response = new Message();
//        JsonObject content = new JsonObject();
//
//        response.setAction(GameAction.GET_LIST_GAMES);
//
//        try {
//            content = gson.toJsonTree(games).getAsJsonObject();
//            response.setContent(content);
//            response.setStatus(200);
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onSetGameMode(Message message, Session userSession) {
//        Gson gson = new Gson();
//        Message response = new Message();
//        JsonObject content = new JsonObject();
//
//        response.setAction(GameAction.SET_GAME_MODE);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            int mode = message.getContent().get("mode").getAsInt();
//            Game game = games.get(gameId);
//            game.setMode(mode);
//
//            content.add("game", gson.toJsonTree(game));
//            response.setContent(content);
//            response.setStatus(200);
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onSetGameCharacter(Message message, Session userSession) {
//        Gson gson = new Gson();
//        User user = users.get(userSession.getId());
//        Message response = new Message();
//        Message rivalMessage = new Message();
//        JsonObject content = new JsonObject();
//        JsonObject rivalMessageContent = new JsonObject();
//
//        response.setAction(GameAction.SET_GAME_CHARACTER);
//        rivalMessage.setAction(GameAction.SET_RIVAL_CHARACTER);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            int characterType = message.getContent().get("character").getAsInt();
//            Game game = games.get(gameId);
//            AttackUser attackUser;
//
//            switch (characterType) {
//                case GameCharacter.KNIGHT:
//                    attackUser = new KnightUser(user);
//                    break;
//                case GameCharacter.MEDUSA:
//                    attackUser = new MedusaUser(user);
//                    break;
//                case GameCharacter.HOT_GIRL:
//                    attackUser = new HotgirlUser(user);
//                    break;
//                case GameCharacter.DRACULA:
//                    attackUser = new DracularUser(user);
//                    break;
//                default:
//                    throw new Exception("Character type not found");
//            }
//
//            content.addProperty("character_type", characterType);
//            content.add("character", gson.toJsonTree(attackUser));
//            response.setContent(content);
//            response.setStatus(200);
//
//            rivalMessageContent.addProperty("character_type", characterType);
//            rivalMessageContent.add("character", gson.toJsonTree(attackUser));
//            rivalMessage.setContent(content);
//            rivalMessage.setStatus(200);
//
//            if (game.checkMaster(user)) {
//                game.setMaster(attackUser);
//                game.getGuest().getSession().getAsyncRemote().sendObject(rivalMessage);
//            } else {
//                game.setGuest(attackUser);
//                game.getMaster().getSession().getAsyncRemote().sendObject(rivalMessage);
//            }
//
//
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onStartGame(Message message, Session userSession) {
//        Message response = new Message();
//        JsonObject content = new JsonObject();
//
//        response.setAction(GameAction.START_GAME);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            Game game = games.get(gameId);
//            game.start();
//            content.add("game", game.getStateAsJson());
//
//            response.setContent(content);
//            response.setStatus(200);
//
//            game.getMaster().getSession().getAsyncRemote().sendObject(response);
//            game.getGuest().getSession().getAsyncRemote().sendObject(response);
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onReady(Message message, Session userSession) {
//        User user = users.get(userSession.getId());
//        Message response = new Message();
//        JsonObject content = new JsonObject();
//
//        response.setAction(GameAction.GUEST_READY);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            Game game = games.get(gameId);
//            game.setStatus(GUEST_READY);
//
//            content.addProperty("status", GUEST_READY);
//            response.setContent(content);
//            response.setStatus(200);
//
//            game.getMaster().getSession().getAsyncRemote().sendObject(response);
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setContent(content);
//            response.setStatus(500);
//        }
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void leaveGame(int gameId, Session userSession) {
//        Message response = new Message();
//        User user = users.get(userSession.getId());
//
//        Game game = games.get(gameId);
//        response.setStatus(200);
//        if (game.checkGuest(user)) {
//            game.getMaster().getSession().getAsyncRemote().sendObject(response);
//        } else {
//            games.remove(gameId);
//            game.getGuest().getSession().getAsyncRemote().sendObject(response);
//        }
//    }
//
//    private void onLeaveGame(Message message, Session userSession) {
//        JsonObject content = new JsonObject();
//        Message response = new Message();
//
//        response.setAction(GameAction.LEAVE_GAME);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            leaveGame(gameId, userSession);
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setStatus(500);
//            response.setContent(content);
//        }
//        userSession.getAsyncRemote().sendObject(response);
//    }
//
//    private void onGetGameState(Message message, Session userSession) {
//        JsonObject content = new JsonObject();
//        Message response = new Message();
//
//        response.setAction(GameAction.GET_GAME_STATE);
//
//        try {
//            int gameId = message.getContent().get("game_id").getAsInt();
//            Game game = games.get(gameId);
//
//            content.add("game", game.getStateAsJson());
//
//            response.setStatus(200);
//            response.setContent(content);
//        } catch (Exception e) {
//            content.addProperty("message", e.getMessage());
//            response.setStatus(500);
//            response.setContent(content);
//        }
//        userSession.getAsyncRemote().sendObject(response);
//    }
//}