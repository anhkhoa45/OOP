package model;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int MODE_NORMAL = 0;
    public static final int MODE_ATTACK = 1;

    public static int gameCount = 0;

    private int id;
    private User userMaster;
    private User userGuest;
    private Character characterMaster;
    private Character characterGuest;
    private Topic topic;
    private int mode;
    private GameStatus status;
    private long timeStarted;


    public Game() {
        this.id = gameCount++;
    }

    public Game(User userMaster) {
        this.id = gameCount++;
        this.userMaster = userMaster;
        this.status = GameStatus.INITIAL;
    }

    public Game(Character characterMaster, Character characterGuest) {
        this.id = gameCount++;
        this.characterMaster = characterMaster;
        this.characterGuest = characterGuest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserMaster() {
        return userMaster;
    }

    public void setUserMaster(User userMaster) {
        this.userMaster = userMaster;
    }

    public User getUserGuest() {
        return userGuest;
    }

    public void setUserGuest(User userGuest) {
        this.userGuest = userGuest;
    }

    public Character getCharacterMaster() {
        return characterMaster;
    }

    public void setCharacterMaster(Character characterMaster) {
        this.characterMaster = characterMaster;
    }

    public Character getCharacterGuest() {
        return characterGuest;
    }

    public void setCharacterGuest(Character characterGuest) {
        this.characterGuest = characterGuest;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public long getTimeStarted() {
        return timeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.timeStarted = timeStarted;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public boolean checkMaster(Character user) {
        return this.characterMaster.equals(user);
    }

    public boolean checkGuest(Character user) {
        return this.characterGuest.equals(user);
    }

    public boolean isEmpty() {
        return this.characterMaster == null && this.characterGuest == null;
    }

    public boolean isWaiting() {
        return this.characterGuest == null;
    }

    public boolean isFull() {
        return this.characterGuest != null;
    }

    public boolean isGuestReady() {
        return (status == GameStatus.GUEST_READY);
    }

    public boolean isStarted() {
        return (status == GameStatus.STARTED);
    }

    public boolean isGameOver() {
        return (status == GameStatus.GAME_OVER);
    }

    public Topic getTopic() {
        return topic;
    }

    public void removeGuest() {
        this.characterGuest = null;
    }

    public boolean start() {
        if (this.isFull() && this.isGuestReady()) {
            this.status = GameStatus.STARTED;
            this.timeStarted = System.nanoTime();
            this.topic = TopicFactory.getRandomTopic();
            return true;
        } else {
            return false;
        }
    }

    public long getRemainTime() {
        long currentTime = System.nanoTime();
        return (currentTime - this.timeStarted);
    }

    public boolean destroy() {
        return (characterMaster == null && characterGuest == null);
    }

    public JsonObject getStateAsJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", this.id);
        json.addProperty("mode", this.mode);
        json.addProperty("topic", this.topic != null ? this.topic.getValue() : "");
        json.addProperty("status", this.status.toString());
        json.add("characterMaster", this.characterMaster.getStateAsJson());
        if (this.characterGuest != null) {
            json.add("characterGuest", this.characterGuest.getStateAsJson());
        }

        return json;
    }
}
