package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

public class ArcherCharacter extends AttackCharacter {
    public ArcherCharacter() {
        super();
        this.health = 100;
        this.attack = 6;
    }

    @Override
    public void attack(AttackCharacter guardCharacter) {
        if (this.isDead() || this.isStunned) {
            return;
        }
        if (!guardCharacter.guard(this)) {
            guardCharacter.takeDamage(attack);
        }
    }

    @Override
    public boolean guard(AttackCharacter attackCharacter) {
        if(!super.guard(attackCharacter)){
            this.attack += 2;
            return false;
        }

        return true;
    }

    @Override
    public void power(AttackCharacter guardCharacter) {
        if (this.numBeingAttacked >= 5) {
            final long timeInterval = 5000;
            new Thread(() -> {
                while (!guardCharacter.isDead()) {
                    guardCharacter.takeDamage(10);
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                Thread.currentThread().interrupt();
            }).start();

            this.isPowered = true;
        }
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.HOT_GIRL);
        return json;
    }
}
