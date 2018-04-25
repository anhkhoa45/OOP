/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

/**
 * @author Ngoc
 */
public class HotgirlCharacter extends Character {
    public HotgirlCharacter() {
        super();
        this.health = 100;
        this.attack = 6;
    }

    @Override
    public void attack(Character guardPlayer) {
        if (this.isDead() || this.isStunned) {
            return;
        }

        if (!guardPlayer.guard(this)) {
            guardPlayer.takeDamage(attack);
        }
    }

    @Override
    public boolean guard(Character attackPlayer) {
        this.attack += 2;
        return false;
    }

    @Override
    public void power(Character attackPlayer) {
        if (attackPlayer.answers.size() == 5) {
            final long timeInterval = 5000;
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    while (true) {
                        // ------- code for task to run
                        attackPlayer.health -= 10;
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

//    @Override
//    public JsonObject getStateAsJson() throws RuntimeException {
//        JsonObject json = super.getStateAsJson();
//        json.addProperty("character_type", GameCharacter.HOT_GIRL);
//        return json;
//    }
}
