package model;

import com.google.gson.JsonObject;

import javax.persistence.*;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private static int id = 0;
    private String name;
    private Session session;
    private UserStatus status;
    private Set<Game> playedGames = new HashSet<>();

    public User() {}

    public User(Session session) {
        this.id++;
        this.session = session;
    }

    public User(Session session, String name){
        this.id++;
        this.session=session;
        this.name=name;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setOnlineState() {
        this.status = UserStatus.ONLINE;
    }

    public void setPlayingState() {
        this.status = UserStatus.PLAYING;
    }

    public void setOfflineState() {
        this.session = null;
        this.status = UserStatus.OFFLINE;
    }

    public Set<Game> getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(Set<Game> playedGames) {
        this.playedGames = playedGames;
    }

    public void doAnswer() {

    }

    public JsonObject getStateAsJson() {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        return json;
    }

}