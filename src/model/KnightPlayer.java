package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

public class KnightPlayer extends AttackPlayer {
    public KnightPlayer() {
        super();
        this.health = 120;
        this.attack = 12;
    }

    public KnightPlayer(Player player) {
        super(player);
        this.health = 120;
        this.attack = 12;
    }

    @Override
    public void attack(AttackPlayer guardPlayer) {
        if (this.isDead() || this.isStunned) {
            return;
        }

        if(!guardPlayer.guard(this)){
            guardPlayer.takeDamage(this.attack);
            if (isPowered) this.attack += 2;
        }
    }

    @Override
    public boolean guard(AttackPlayer attackPlayer) {
        return false;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if(this.isPowered) this.attack = 12;
    }

    @Override
    public void power(AttackPlayer guardPlayer) {
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
