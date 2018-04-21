package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            return this.id == ((Player) obj).getId();
        }
        return false;
    }

    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = new JsonObject();
        Gson gson = new Gson();
        JsonElement answers = gson.toJsonTree(this.answers, new TypeToken<ArrayList<Answer>>() {}.getType());

        if (!answers.isJsonArray()) {
            throw new RuntimeException("Get player state failed");
        }

        json.addProperty("id", this.id);
        json.add("answers", answers.getAsJsonArray());
        json.addProperty("score", this.score);

        return json;
    }
}
