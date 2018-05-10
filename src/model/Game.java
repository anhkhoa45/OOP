package model;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static int gameCount = 0;

    private int id;
    private Character masterCharacter;
    private Character guestCharacter;
    private User masterUser;
    private User guestUser;
    private Topic topic;
    private GameMode mode;
    private GameStatus status;
    private long timeStarted;
    private long timeEnd;

    public Game(User master) {
        this.id = gameCount++;
        this.masterUser = master;
        this.status = GameStatus.INITIAL;
    }

    public Game(User master, User guest) {
        this(master);
        this.guestUser = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public User getMasterUser() {
        return masterUser;
    }

    public User getGuestUser() {
        return guestUser;
    }

    public void setGuestUser(User guestUser) {
        this.guestUser = guestUser;
    }

    public void removeGuest() {
        this.guestUser = null;
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

    public Character getMasterCharacter() {
        return masterCharacter;
    }

    public void setMasterCharacter(Character masterCharacter) {
        this.masterCharacter = masterCharacter;
    }

    public Character getGuestCharacter() {
        return guestCharacter;
    }

    public void setGuestCharacter(Character guestCharacter) {
        this.guestCharacter = guestCharacter;
    }
    
    public boolean checkMaster(User user) {
        return this.masterUser.equals(user);
    }

    public boolean checkGuest(User user) {
        return this.guestUser.equals(user);
    }

    public boolean isEmpty() {
        return this.masterUser == null && this.guestUser == null;
    }

    public boolean isWaiting() {
        return this.guestUser == null;
    }

    public boolean isFull() {
        return this.guestUser != null;
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
    
    public void setTopic(Topic topic){
        this.topic=topic;
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

    public long getPlayedTime() {
        long currentTime = System.nanoTime();
        return (currentTime - this.timeStarted);
    }

    public boolean destroy() {
        return (masterCharacter == null && guestCharacter == null);
    }

    public JsonObject getStateAsJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", this.id);
        if(this.mode != null) {
            json.addProperty("mode", this.mode.toString());
        }
        json.addProperty("topic", this.topic != null ? this.topic.getValue() : "");
        json.addProperty("status", this.status.toString());
        return json;
    }
}
