package model;

import com.google.gson.JsonObject;

public abstract class AttackPlayer extends Player {
    protected int health;
    protected int attack;
    protected boolean isStunned;
    protected boolean isPowered;
    protected boolean isBlackout;
    protected int numBeingAttacked;

    public AttackPlayer(){
        super();
        this.isStunned = false;
        this.isPowered = false;
        this.isBlackout = false;
    }

    public AttackPlayer(Player player) {
        super(player.getId(), player.getSession());
        this.answers = player.answers;
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

    abstract public void attack(AttackPlayer guardPlayer);

    abstract public boolean guard(AttackPlayer attackPlayer);

    abstract public void power(AttackPlayer guardPlayer);

    public boolean isDead() {
        return this.health == 0;
    }

    @Override
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
