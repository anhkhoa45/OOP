package model;

import com.google.gson.JsonObject;
import com.sun.corba.se.spi.ior.ObjectKey;

import javax.persistence.*;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String name;
    private transient Session session;
    private UserStatus status;
    private Set<Game> playedGames = new HashSet<>();

    public User() {}

    public User(Session session) {
        this.session = session;
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

    public boolean equals(Object obj){
        if(obj instanceof User){
            return ((User) obj).getName().equals(this.name);
        }

        return false;
    }

    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        return json;
    }

}