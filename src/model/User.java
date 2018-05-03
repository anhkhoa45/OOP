package model;

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
    private Set<Game> playedGames = new HashSet<>();
    private Game currentGame = new Game();
    private GameRole currentGameRole;

    public User() {}

    public User(Session session) {
        this.id++;
        this.session = session;
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

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public GameRole getCurrentGameRole() {
        return currentGameRole;
    }

    public void setCurrentGameRole(GameRole currentGameRole) {
        this.currentGameRole = currentGameRole;
    }

    public void addPlayedGame(Game game) {
        playedGames.add(game);
    }

    public Character getRivalCharacter() {
        if (currentGameRole == GameRole.MASTER)
            return currentGame.getGuestCharacter();
        else return currentGame.getMasterCharacter();
    }

    public void doAnswer() {

    }

}