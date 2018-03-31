package model;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player {
    private ArrayList<String> answers = new ArrayList<String>();
    private transient Session session;
    private int score;

    public Player() {}

    public Player(Session session) {
        this.session = session;
        this.score = 0;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int answerQuestion(String answer){
        return 1;
    }
}
