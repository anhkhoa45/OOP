package model;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Game {
    public static int gameCount = 0;

    private int id;
    private Character master;
    private Character guest;
    private Topic topic;
    private GameStatus status;
    private long timeStarted;


    public Game() {
        this.id = gameCount++;
    }

    public Game(Character master) {
        this.id = gameCount++;
        this.master = master;
        this.status = GameStatus.INITIAL;
    }

    public Game(Character master, Character guest) {
        this.id = gameCount++;
        this.master = master;
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Character getMaster() {
        return master;
    }

    public void setMaster(Character master) {
        this.master = master;
    }

    public Character getGuest() {
        return guest;
    }

    public void setGuest(Character guest) {
        this.guest = guest;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public boolean checkMaster(Character user) {
        return this.master.equals(user);
    }

    public boolean checkGuest(Character user) {
        return this.guest.equals(user);
    }

    public boolean isEmpty() {
        return this.master == null && this.guest == null;
    }

    public boolean isWaiting() {
        return this.guest == null;
    }

    public boolean isFull() {
        return this.guest != null;
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
        this.guest = null;
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
        return (master == null && guest == null);
    }

//    public JsonObject getStateAsJson() {
//        JsonObject json = new JsonObject();
//
//        json.addProperty("id", this.id);
//        json.addProperty("mode", this.mode);
//        json.addProperty("question", this.question != null ? this.question.getValue() : "");
//        json.addProperty("status", this.status);
//        json.add("master", this.master.getStateAsJson());
//        if (this.guest != null) {
//            json.add("guest", this.guest.getStateAsJson());
//        }
//
//        return json;
//    }
}
