package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

public class KnightCharacter extends Character {
    public KnightCharacter() {
        super();
        this.health = 120;
        this.attack = 12;
    }

    @Override
    public void attack(Character guardPlayer) {
        if (this.isDead() || this.isStunned) {
            return;
        }

        if(!guardPlayer.guard(this)){
            guardPlayer.takeDamage(this.attack);
            if (isPowered) this.attack += 2;
        }
    }

    @Override
    public boolean guard(Character attackPlayer) {
        return false;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if(this.isPowered) this.attack = 12;
    }

    @Override
    public void power(Character guardPlayer) {
        if (numBeingAttacked < 5) return;
        if(!this.isPowered) this.isPowered = true;
    }

//    @Override
//    public JsonObject getStateAsJson() throws RuntimeException {
//        JsonObject json = super.getStateAsJson();
//        json.addProperty("character_type", GameCharacter.KNIGHT);
//        return json;
//    }
}
