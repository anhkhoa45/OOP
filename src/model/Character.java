package model;

import com.google.gson.JsonObject;
import javafx.beans.value.ObservableBooleanValue;

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

}
