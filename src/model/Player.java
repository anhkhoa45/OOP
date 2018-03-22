package model;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player {
    private int id;
    private transient Game playingGame;
    private ArrayList<String> answers = new ArrayList<String>();
    private transient Session session;

    public Player() {}

    public Player(int id, Session session) {
        this.id = id;
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getPlayingGame() {
        return playingGame;
    }

    public void setPlayingGame(Game playingGame) {
        this.playingGame = playingGame;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Game createGame(){
        Game game = new Game(this);
        return game;
    }

    public void leaveGame(){
        Game game = this.playingGame;

    }

    public int answerQuestion(String answer){
        return 1;
    }
}
