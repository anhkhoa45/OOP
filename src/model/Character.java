package model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private List<Answer> answers = new ArrayList<>();

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public boolean checkDuplicateAnswer(Answer answer){
        for (Answer a : this.answers) {
            if (a.getWord().equals(answer.getWord())){
                return true;
            }
        }
        return false;
    }

    public JsonObject getStateAsJson() throws RuntimeException{
        JsonObject json = new JsonObject();
        Gson gson = new Gson();
        List<Answer> e = this.answers;
        JsonElement a = gson.toJsonTree(e, new TypeToken<List<Answer>>() {}.getType());

        if (!a.isJsonArray()) {
            throw new RuntimeException("Get player state failed");
        }

        json.add("answers", a.getAsJsonArray());

        return json;
    }
}
