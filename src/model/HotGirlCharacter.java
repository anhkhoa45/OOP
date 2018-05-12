package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

import java.util.Random;

public class HotGirlCharacter extends AttackCharacter {

    private int evasionRate;

    public HotGirlCharacter() {
        super();
        this.health = 100;
        this.attack = 6;
        Random random = new Random();
        this.evasionRate = random.nextInt(10) + 1;
    }

    @Override
    public void attack(AttackCharacter guardCharacter) {
        if (this.isDead() || this.isStunned) {
            return;
        }
        if (!guardCharacter.guard(this)) {
            this.health += this.attack / 2;
            guardCharacter.takeDamage(attack);
        }
    }

    @Override
    public boolean guard(AttackCharacter attackCharacter) {
        if(!super.guard(attackCharacter)){
            Random random = new Random();
            int anyNumber = random.nextInt(100);
            return anyNumber < evasionRate;
        } else {
            return true;
        }
    }

    @Override
    public void power(AttackCharacter guardCharacter) {
        if (numBeingAttacked < 5) return;
        if (!this.isPowered) this.isPowered = true;
        guardCharacter.isBlackout = true;
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.HOT_GIRL);
        return json;
    }
}
