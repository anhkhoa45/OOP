package model;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player {
    private int id;
    private Game playingGame;
    private ArrayList<String> answers = new ArrayList<String>();
    private Session session;

    public Player() {

    }

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
        Game game = new Game();
        game.addPlayer(this);
        return game;
    }

    public void leaveGame(){
        if(this.playingGame != null){
            this.playingGame.removePlayer(this);
            this.answers.clear();
            this.playingGame = null;
        }
    }

    public int answerQuestion(String answer){
        this.answers.add(answer);
        return (int)(Math.random() * 5 + 1);
    }
}
