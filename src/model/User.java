package model;

import javax.persistence.*;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private String avatar;

    private transient Session session;
    private transient Set<Game> playedGames = new HashSet<>();
    private transient Game currentGame = new Game();

    public User() {}

    public User(int id, Session session) {
        this.id = id;
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void save(){
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<Game> getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(Set<Game> playedGames) {
        this.playedGames = playedGames;
    }

    public Game getPlayingGame() {
        return currentGame;
    }

    public void setPlayingGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}