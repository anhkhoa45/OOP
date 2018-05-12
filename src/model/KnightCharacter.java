package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

public class KnightCharacter extends AttackCharacter {
    public KnightCharacter() {
        super();
        this.health = 120;
        this.attack = 12;
    }

    @Override
    public void attack(AttackCharacter guardCharacter) {
        if (this.isDead() || this.isStunned) {
            return;
        }
        if (!guardCharacter.guard(this)){
            guardCharacter.takeDamage(this.attack);
            if (this.isPowered) this.attack += 2;
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if(this.isPowered) this.attack = 12;
    }

    @Override
    public void power(AttackCharacter guardCharacter) {
        if (numBeingAttacked < 5) return;
        if(!this.isPowered) this.isPowered = true;
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.KNIGHT);
        return json;
    }
}
