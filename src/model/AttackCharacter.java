package model;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public abstract class AttackCharacter extends Character {
    protected int health;
    protected int attack;
    protected boolean isStunned;
    protected boolean isPowered;
    protected boolean isBlackout;
    protected int numBeingAttacked;

    public AttackCharacter(){
        super();
        this.isStunned = false;
        this.isPowered = false;
        this.isBlackout = false;
        this.numBeingAttacked = 0;
    }

    abstract public void attack(AttackCharacter guardCharacter);

    public boolean guard(AttackCharacter attackCharacter){
        Answer attackerAns = attackCharacter.getAnswers().get(attackCharacter.getAnswers().size() -1);

        if(this.checkDuplicateAnswer(attackerAns)){
            attackerAns.setScore(0);
            return true;
        } else {
            return false;
        }
    }

    abstract public void power(AttackCharacter guardCharacter);

    public boolean isDead() {
        return this.health == 0;
    }

    public void takeDamage(int damage){
        if(this.health > damage){
            this.numBeingAttacked++;
            this.health -= damage;
        } else {
            this.health = 0;
        }
    }

    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("health", this.health);
        json.addProperty("attack", this.attack);
        json.addProperty("isStunned", this.isStunned);
        json.addProperty("isPowered", this.isPowered);
        json.addProperty("isBlackout", this.isBlackout);
        json.addProperty("numBeingAttacked", this.numBeingAttacked);
        return json;
    }
}
