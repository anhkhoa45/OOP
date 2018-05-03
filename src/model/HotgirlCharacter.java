package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

public class HotgirlCharacter extends AttackCharacter {
    public HotgirlCharacter() {
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
        this.attack += 2;
        return false;
    }

    @Override
    public void power(AttackCharacter attackCharacter) {
        if (attackCharacter.getAnswers().size() == 5) {
            final long timeInterval = 5000;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        // ------- code for task to run
                        attackCharacter.health -= 10;
                        // ------- ends here
                        try {
                            Thread.sleep(timeInterval);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };

            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.HOT_GIRL);
        return json;
    }
}
