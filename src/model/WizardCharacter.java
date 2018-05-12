/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.JsonObject;
import socket.GameCharacter;

import java.lang.*;

public class WizardCharacter extends AttackCharacter {
    private int stunTime;

    public WizardCharacter() {
        super();
        this.health = 80;
        this.attack = 10;
    }

    @Override
    public void attack(AttackCharacter guardCharacter) {
        if (this.isDead() || this.isStunned) {
            return;
        }
        if (!guardCharacter.guard(this)) {
            guardCharacter.takeDamage(this.attack);
        }
    }

    @Override
    public boolean guard(AttackCharacter attackCharacter) {
        Answer a = attackCharacter.getAnswers().get(attackCharacter.getAnswers().size() - 1);
        if (this.checkDuplicateAnswer(a)) {
            freeze(5000, attackCharacter);
            return true;
        }
        return false;
    }

    @Override
    public void power(AttackCharacter guardCharacter) {
        if (this.numBeingAttacked >= 5) {
            freeze(10000, guardCharacter);
            this.isPowered = true;
        }
    }

    public void freeze(int time, AttackCharacter guardCharacter) {
        new Thread(() -> {
            guardCharacter.isStunned = true;

            try {
                Thread.sleep(time);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            guardCharacter.isStunned = false;
        }).start();
    }

    @Override
    public JsonObject getStateAsJson() throws RuntimeException {
        JsonObject json = super.getStateAsJson();
        json.addProperty("character_type", GameCharacter.WIZARD);
        return json;
    }
}
