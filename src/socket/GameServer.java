package socket;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import model.*;

import javax.inject.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import javax.websocket.server.PathParam;

import static model.GameStatus.GAME_OVER;

@ServerEndpoint(value = "/game-server/{user_name}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
@Singleton
public class GameServer {
    private static HashMap<String, User> users = new HashMap<String, User>();
    private static HashMap<Integer, Game> games = new HashMap<Integer, Game>();

    /**
     * Callback hook for Connection open events.
     * <p>
     * This method will be invoked when a client requests for a
     * WebSocket connection.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession, @PathParam("user_name") String userName) {
        System.out.println("New request received. Id: " + userSession.getId());
        User user = UserManager.getUserByUsername(userName);

        if (user != null) {
            user.setOnlineState();
            user.setSession(userSession);
        } else {
            user = new User(userSession, userName);
            UserManager.addUser(user);
            System.out.print("New user");
        }

        users.put(userSession.getId(), user);
    }

    /**
     * Callback hook for Connection close events.
     * <p>
     * This method will be invoked when a client closes a WebSocket
     * connection.
     *
     * @param userSession the userSession which is opened.
     */
    @OnClose
    public void onClose(Session userSession) {
        System.out.println("Connection closed. Id: " + userSession.getId());
        User user = users.get(userSession.getId());

        for (Game g : games.values()) {
            if (g.checkMaster(user) || g.checkGuest(user)) {
                leaveGame(g.getId(), userSession);
                break;
            }
        }
        user.setOfflineState();
        users.remove(userSession.getId());
    }

    @OnError
    public void onError(Session userSession, Throwable throwable) {
        User user = users.get(userSession.getId());
        user.setOfflineState();
        users.remove(userSession.getId());
    }

    /**
     * Callback hook for Message Events.
     * <p>
     * This method will be invoked when a client send a message.
     *
     * @param message     The text message
     * @param userSession The session of the client
     */
    @OnMessage
    public void onMessage(Message message, Session userSession) {
        switch (message.getAction()) {
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
            case GET_ONLINE_USERS:
                onGetOnlineUsers(message, userSession);
                break;
            case SET_GAME_MODE:
                onSetGameMode(message, userSession);
                System.out.println("ACTION_SET_GAME_MODE");
                break;
            case DONE_CHOOSE_MODE:
                onDoneChooseMode(message, userSession);
                System.out.println("ACTION_DONE_CHOOSE_MODE");
                break;
            case SET_GAME_CHARACTER:
                onSetGameCharacter(message, userSession);
                System.out.println("ACTION_SET_GAME_CHARACTER");
                break;
            case START_GAME:
                onStartGame(message, userSession);
                System.out.println("ACTION_START_GAME");
                break;
            case GUEST_READY:
                onReady(message, userSession);
                System.out.println("ACTION_READY");
                break;
            case LEAVE_GAME:
                onLeaveGame(message, userSession);
                System.out.println("ACTION_LEAVE_GAME");
                break;
            case GET_GAME_STATE:
                onGetGameState(message, userSession);
                System.out.println("ACTION_GET_GAME_STATE");
                break;
            case INVITE:
                onInvite(message, userSession);
                break;
            default:

        }
    }

    private void onInvite(Message message, Session userSession) {
        Message response = new Message();
        JsonObject content = new JsonObject();
        Gson gson = new Gson();
        User inviter = users.get(userSession.getId());
        response.setAction(GameAction.INVITE);
        try {
            String username = message.getContent().get("user_name").getAsString();
            User invitee = UserManager.getUserByUsername(username);
            content.add("user", gson.toJsonTree(inviter));

            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            content.add("game", gson.toJsonTree(game));

            response.setStatus(200);
            response.setContent(content);
            invitee.getSession().getAsyncRemote().sendObject(response);
        } catch (Exception e) {
            e.printStackTrace();
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onJoinGame(Message message, Session userSession) {
        User user = users.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.JOIN_GAME);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            game.setGuestUser(user);

            JsonObject gameJson = game.getStateAsJson();
            gameJson.add("master", game.getMasterUser().getStateAsJson());
            gameJson.add("guest", game.getGuestUser().getStateAsJson());
            content.add("game", gameJson);

            response.setStatus(200);
            response.setContent(content);

            game.getMasterUser().getSession().getAsyncRemote().sendObject(
                    new Message(200, GameAction.GUEST_JOIN_GAME, content)
            );
        } catch (Exception e) {
            e.printStackTrace();
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onCreateGame(Message message, Session userSession) {
        User user = users.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.CREATE_GAME);

        Game game = new Game(user);
        GameServer.games.put(game.getId(), game);

        try {
            JsonObject jObjGame = game.getStateAsJson();
            jObjGame.add("master", game.getMasterUser().getStateAsJson());
            content.add("game", jObjGame);
            response.setStatus(200);
            response.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
            GameServer.games.remove(game.getId());
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onAnswer(Message message, Session userSession) {
        User user = users.get(userSession.getId());
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.ANSWER_QUESTION);

        try {
            String answer = message.getContent().get("answer").getAsString();
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            Answer a = new Answer(answer);
            int score = game.getTopic().getWordScore(a.getWord());
            a.setScore(score);
            GameMode mode = game.getMode();
            switch (mode) {
                case NORMAL:
                    a.setScore(score);
                    if (game.getMasterUser().equals(user))
                        game.getMasterCharacter().addAnswer(a);
                    else game.getGuestCharacter().addAnswer(a);
                    break;
                case ATTACK:
                    model.Character p;
                    model.Character opponent;
                    if (game.getMasterUser().equals(user)) {
                        opponent = game.getGuestCharacter();
                        p = game.getMasterCharacter();
                    } else {
                        p = game.getGuestCharacter();
                        opponent = game.getMasterCharacter();
                    }

                    if (opponent.checkDuplicateAnswer(a) || p.checkDuplicateAnswer(a)) {
                        score = 0;
                    }
                    a.setScore(score);
                    p.addAnswer(a);
                    if (score != 0 || game.getMasterCharacter() instanceof MedusaCharacter || game.getGuestCharacter() instanceof MedusaCharacter) {
                        AttackCharacter tmp1 = (AttackCharacter) p;
                        AttackCharacter tmp2 = (AttackCharacter) opponent;
                        tmp1.attack(tmp2);
                        if (tmp1.isDead() || tmp2.isDead()) {
                            game.setStatus(GAME_OVER);
                        }
                    }
                    break;
            }

            response.setStatus(200);
            response.setContent(content);
        } catch (Exception e) {
            e.printStackTrace();
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onGetListGame(Message message, Session userSession) {
        Gson gson = new Gson();
        Message response = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.GET_LIST_GAMES);

        HashMap<Integer, Game> waitingGameList = new HashMap<>();
        try {
            for (Game game : games.values()) {
                if (!game.isFull()) {
                    waitingGameList.put(game.getId(), game);
                }
            }
            content = gson.toJsonTree(waitingGameList).getAsJsonObject();
            response.setContent(content);
            response.setStatus(200);
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onGetOnlineUsers(Message message, Session userSession) {
        Gson gson = new Gson();
        Message response = new Message();
        JsonObject content = new JsonObject();
        //Set<String> onlineUserName=users.keySet();
        response.setAction(GameAction.GET_ONLINE_USERS);
        try {
            content = gson.toJsonTree(users).getAsJsonObject();
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
        Message response = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.SET_GAME_MODE);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            int modeTmp = message.getContent().get("mode").getAsInt();
            Game game = games.get(gameId);
            switch (modeTmp) {
                case 0:
                    game.setMode(GameMode.NORMAL);
                    break;
                case 1:
                    game.setMode(GameMode.ATTACK);
                    break;
                default:
                    throw new Exception("Invalid game mode!");
            }

            content.add("game", game.getStateAsJson());
            response.setContent(content);
            response.setStatus(200);

            User guest = game.getGuestUser();
            if (guest != null) {
                guest.getSession().getAsyncRemote().sendObject(
                        new Message(200, GameAction.SET_GAME_MODE, content)
                );
            }
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onDoneChooseMode(Message message, Session userSession) {
        Message response = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.DONE_CHOOSE_MODE);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            User guest = game.getGuestUser();

            content.addProperty("mode", game.getMode().toString());

            response.setContent(content);
            response.setStatus(200);

            if (guest != null) {
                guest.getSession().getAsyncRemote().sendObject(response);
            }
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }

        userSession.getAsyncRemote().sendObject(response);
    }

    private void onSetGameCharacter(Message message, Session userSession) {
        Gson gson = new Gson();
        User user = users.get(userSession.getId());
        Message response = new Message();
        Message rivalMessage = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.SET_GAME_CHARACTER);
        rivalMessage.setAction(GameAction.SET_RIVAL_CHARACTER);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            int characterType = message.getContent().get("character").getAsInt();
            Game game = games.get(gameId);
            AttackCharacter attackCharacter;

            switch (characterType) {
                case GameCharacter.KNIGHT:
                    attackCharacter = new KnightCharacter();
                    break;
                case GameCharacter.MEDUSA:
                    attackCharacter = new MedusaCharacter();
                    break;
                case GameCharacter.HOT_GIRL:
                    attackCharacter = new HotgirlCharacter();
                    break;
                case GameCharacter.DRACULA:
                    attackCharacter = new DraculaCharacter();
                    break;
                default:
                    throw new Exception("Character type not found");
            }

            content.addProperty("character_type", characterType);
            content.add("character", gson.toJsonTree(attackCharacter));
            response.setContent(content);
            response.setStatus(200);
            rivalMessage = response;

            if (game.getMasterUser().equals(user)) {
                game.setMasterCharacter(attackCharacter);
                game.getMasterUser().getSession().getAsyncRemote().sendObject(rivalMessage);
            } else {
                game.setGuestCharacter(attackCharacter);
                game.getGuestUser().getSession().getAsyncRemote().sendObject(rivalMessage);
            }

        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onStartGame(Message message, Session userSession) {
        User user = users.get(userSession.getId());
        Message response = new Message();
        Message rivalMessage;
        JsonObject content = new JsonObject();

        response.setAction(GameAction.START_GAME);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            game.start();
            content.add("game", game.getStateAsJson());

            response.setContent(content);
            response.setStatus(200);

            rivalMessage = response;
            game.getGuestUser().getSession().getAsyncRemote().sendObject(rivalMessage);
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void onReady(Message message, Session userSession) {
        User user = users.get(userSession.getId());
        Message response = new Message();
        JsonObject content = new JsonObject();

        response.setAction(GameAction.GUEST_READY);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);
            game.setStatus(GameStatus.GUEST_READY);

            content.addProperty("status", game.getStatus().toString());
            response.setContent(content);
            response.setStatus(200);

            game.getMasterUser().getSession().getAsyncRemote().sendObject(response);
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setContent(content);
            response.setStatus(500);
        }
        userSession.getAsyncRemote().sendObject(response);
    }

    private void leaveGame(int gameId, Session userSession) throws RuntimeException {
        User user = users.get(userSession.getId());
        Game game = games.get(gameId);
        User gameMaster = game.getMasterUser();
        User gameGuest = game.getGuestUser();

        if (gameMaster.equals(user)) {
            switch (game.getStatus()){
                case INITIAL:
                case GUEST_READY:
                    if (gameGuest != null) {
                        game.removeGuest();
                        gameGuest.getSession().getAsyncRemote().sendObject(
                                new Message(200, GameAction.LEAVE_GAME, null)
                        );
                        gameGuest.setOnlineState();
                    }
                    games.remove(game.getId());
                    break;
                case STARTED:
                case GAME_OVER:
                    break;
            }

            user.setOnlineState();
            user.getSession().getAsyncRemote().sendObject(
                    new Message(200, GameAction.LEAVE_GAME, null)
            );
        } else if (gameGuest.equals(user)) {
            game.removeGuest();
            switch (game.getStatus()){
                case INITIAL:
                    break;
                case GUEST_READY:
                    game.setStatus(GameStatus.INITIAL);
                    break;
                case STARTED:
                case GAME_OVER:
                    break;
            }

            game.getMasterUser().getSession().getAsyncRemote().sendObject(
                    new Message(200, GameAction.GUEST_LEAVE_GAME, null)
            );

            user.setOnlineState();
            user.getSession().getAsyncRemote().sendObject(
                    new Message(200, GameAction.LEAVE_GAME, null)
            );
        } else {
            throw new RuntimeException("Permission denied!");
        }
    }

    private void onLeaveGame(Message message, Session userSession) {
        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            leaveGame(gameId, userSession);
        } catch (Exception e) {
            JsonObject content = new JsonObject();
            content.addProperty("message", e.getMessage());
            userSession.getAsyncRemote().sendObject(
                    new Message(500, GameAction.LEAVE_GAME, content)
            );
        }
    }

    private void onGetGameState(Message message, Session userSession) {
        JsonObject content = new JsonObject();
        Message response = new Message();

        response.setAction(GameAction.GET_GAME_STATE);

        try {
            int gameId = message.getContent().get("game_id").getAsInt();
            Game game = games.get(gameId);

            content.add("game", game.getStateAsJson());

            response.setStatus(200);
            response.setContent(content);
        } catch (Exception e) {
            content.addProperty("message", e.getMessage());
            response.setStatus(500);
            response.setContent(content);
        }
        userSession.getAsyncRemote().sendObject(response);
    }
}