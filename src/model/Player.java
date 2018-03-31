package model;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player {
    private int id;
    protected ArrayList<Answer> answers = new ArrayList<Answer>();
    private transient Session session;
    private int score;

    public Player() {}

    public Player(int id, Session session) {
        this.id = id;
        this.session = session;
        this.score = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void leaveGame(){
    }

    public int answerQuestion(String answer){
        return 1;
    }
}
