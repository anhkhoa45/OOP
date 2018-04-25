package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

import java.util.Random;

public class DraculaCharacter extends Character {

    private int luckyNumber;

    public DraculaCharacter() {
        super();
        this.health = 100;
        this.attack = 6;
        Random random = new Random();
        this.luckyNumber = random.nextInt(10) + 1;
    }

    @Override
    public void attack(Character guardCharacter) {
        if (this.isDead() || this.isStunned) {
            return;
        }

        if (!guardCharacter.guard(this)) {
            this.health += this.attack / 2;
            guardCharacter.takeDamage(attack);
        }
    }

    @Override
    public boolean guard(Character attackCharacter) {
        Random random = new Random();
        int anyNumber = random.nextInt(10) + 1;
        return this.luckyNumber == anyNumber;
    }

    @Override
    public void power(Character guardCharacter) {
        if (numBeingAttacked < 5) return;
        if(!this.isPowered) this.isPowered = true;
        guardCharacter.isBlackout = true;
    }

//    @Override
//    public JsonObject getStateAsJson() throws RuntimeException {
//        JsonObject json = super.getStateAsJson();
//        json.addProperty("character_type", GameCharacter.DRACULA);
//        return json;
//    }
}
