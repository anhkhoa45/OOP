package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

import java.util.Random;

public class DracularPlayer extends AttackPlayer {

    private int luckyNumber;

    public DracularPlayer() {
        super();
        this.health = 100;
        this.attack = 6;
        Random random = new Random();
        this.luckyNumber = random.nextInt(10) + 1;
    }

    public DracularPlayer(Player player) {
        super(player);
        this.health = 100;
        this.attack = 6;
        Random random = new Random();
        this.luckyNumber = random.nextInt(10) + 1;
    }

    @Override
    public void attack(AttackPlayer guardPlayer) {
        if (this.isDead() || this.isStunned) {
            return;
        }

        if (!guardPlayer.guard(this)) {
            this.health += this.attack / 2;
            guardPlayer.takeDamage(attack);
        }
    }

    @Override
    public boolean guard(AttackPlayer attackPlayer) {
        Random random = new Random();
        int anyNumber = random.nextInt(10) + 1;
        return this.luckyNumber == anyNumber;
    }

    @Override
    public void power(AttackPlayer guardPlayer) {
        if (numBeingAttacked < 5) return;
        if(!this.isPowered) this.isPowered = true;
        guardPlayer.isBlackout = true;
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.DRACULA);
        return json;
    }
}
