package model;

import com.google.gson.JsonObject;
import socket.GameAction;
import socket.Message;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private String password;
    private String avatar;
    private transient Session session;
    private transient Thread pingInterval;
    private UserStatus status;
    private Set<Game> playedGames = new HashSet<>();

    public User(Session session, String name, String password, String avatar) {
        this.session = session;
        this.name = name;
        this.password = password;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public void startPingInterval(){
        this.pingInterval = new Thread(() -> {
            try{
                while(this.getSession().isOpen()){
                    Thread.sleep(1000);
                    this.getSession().getAsyncRemote().sendObject(
                            new Message(200, GameAction.PING, null)
                    );
                }
                this.setOfflineState();
            } catch (Exception e){
                this.setOfflineState();
            }
        });
        pingInterval.start();
    }

    private void stopPingInterval(){
        this.pingInterval.interrupt();
    }

    public void setOnlineState() {
        this.status = UserStatus.ONLINE;
    }

    public void setPlayingState() {
        this.status = UserStatus.PLAYING;
    }

    public void setOfflineState() {
        this.status = UserStatus.OFFLINE;
        try {
            this.stopPingInterval();
            this.getSession().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Game> getPlayedGames() {
        return playedGames;
    }

    public void addPlayedGame(Game game) {
        this.playedGames.add(game);
    }

    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return ((User) obj).getName().equals(this.name);
        }

        return false;
    }

    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("avatar", this.avatar);
        return json;
    }

}