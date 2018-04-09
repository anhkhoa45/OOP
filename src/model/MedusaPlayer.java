/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.lang.*;

/**
 * @author Ngoc
 */
public class MedusaPlayer extends AttackPlayer {
    private int stunTime;

    public MedusaPlayer() {
        this.health=80;
        this.attack=10;
    }

    public MedusaPlayer(Player player) {
        super(player);
        this.health=80;
        this.attack=10;
    }

    @Override
    public void attack(AttackPlayer attackPlayer) {

    }

    @Override
    public boolean guard(AttackPlayer attackPlayer) {
        Answer a = attackPlayer.answers.get(attackPlayer.answers.size() - 1);
        if (this.answers.contains(a)) {
            //attackPlayer.isStuned=true;
            freeze(5000, attackPlayer);
            return true;
        }
        return true;
    }

    @Override
    public void power(AttackPlayer attackPlayer) {
        if (attackPlayer.answers.size() == 5) {
            freeze(10000, attackPlayer);
        }
    }

    public void freeze(int time, AttackPlayer attackPlayer) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                attackPlayer.isStuned = true;

                try {
                    Thread.sleep(time);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                attackPlayer.isStuned = false;
            }
        }).start();
    }
}
