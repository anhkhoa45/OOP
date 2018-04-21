package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

public class KnightPlayer extends AttackPlayer {

    public KnightPlayer() {
        this.health = 120;
        this.attack = 12;
    }

    public KnightPlayer(Player player) {
        super(player);
        this.health = 120;
        this.attack = 12;
    }

    public void attack(AttackPlayer guardPlayer) {
        if (this.isDead() || this.isStunned) {
            return;
        }
        if (isPowered) {
            this.power(guardPlayer);
        } else {
            guardPlayer.health -= this.attack;
            guardPlayer.guard(this);
        }
    }

    @Override
    public boolean guard(AttackPlayer attackPlayer) {
        return true;
    }

    @Override
    public void power(AttackPlayer guardPlayer) {
        if (this.isPowered == false) {
            this.isPowered = true;
        }
        if (numBeingAttacked>5 && this.isPowered==true) {
            this.attack += (numBeingAttacked-5)*2;
        }
        if (opponentHasCorrectAnswer(guardPlayer)) {
            this.attack = 12;
        }
    }
    
    public boolean opponentHasCorrectAnswer(AttackPlayer guardPlayer) {
        Answer answer = guardPlayer.answers.get(guardPlayer.answers.size()-1);
        return this.answers.contains(answer);
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.KNIGHT);
        return json;
    }
}
