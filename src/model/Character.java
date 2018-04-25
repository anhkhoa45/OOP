package model;

import com.google.gson.JsonObject;
import javafx.beans.value.ObservableBooleanValue;

import java.util.ArrayList;
import java.util.List;

public abstract class Character {
    protected int health;
    protected int attack;
    protected boolean isStunned;
    protected boolean isPowered;
    protected boolean isBlackout;
    protected int numBeingAttacked;
    protected List<Answer> answers = new ArrayList<>();

    public Character(){
        super();
        this.isStunned = false;
        this.isPowered = false;
        this.isBlackout = false;
        this.numBeingAttacked = 0;
    }

    public boolean checkDuplicateAnswer(Answer answer){
        for(Answer a : this.answers) {
            if(a.getAnswer().equals(answer.getAnswer())){
                return true;
            }
        }
        return false;
    }


    public void takeDamage(int damage){
        if(this.health > damage){
            this.numBeingAttacked++;
            this.health -= damage;
        } else {
            this.health = 0;
        }
    }

    abstract public void attack(Character guardCharacter);

    abstract public boolean guard(Character attackCharacter);

    abstract public void power(Character guardCharacter);

    public boolean isDead() {
        return this.health == 0;
    }

//    @Override
//    public JsonObject getStateAsJson() throws RuntimeException {
//        JsonObject json = super.getStateAsJson();
//        json.addProperty("health", this.health);
//        json.addProperty("attack", this.attack);
//        json.addProperty("isStunned", this.isStunned);
//        json.addProperty("isPowered", this.isPowered);
//        json.addProperty("isBlackout", this.isBlackout);
//        json.addProperty("numBeingAttacked", this.numBeingAttacked);
//        return json;
//    }
}
