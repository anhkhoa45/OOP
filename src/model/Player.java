package model;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player {
    private int id;
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
