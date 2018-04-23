package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    protected ArrayList<Answer> answers = new ArrayList<Answer>();
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

    public void addAnswer(Answer answer){
        this.answers.add(answer);
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
        List<Answer> e = this.answers;
        JsonElement a = gson.toJsonTree(e, new TypeToken<List<Answer>>() {}.getType());

        if (!a.isJsonArray()) {
            throw new RuntimeException("Get player state failed");
        }

        json.addProperty("id", this.id);
        json.add("answers", a.getAsJsonArray());

        return json;
    }
}
