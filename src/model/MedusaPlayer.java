/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Timer;

import java.lang.*;
/**
 *
 * @author Ngoc
 */
public class MedusaPlayer extends AttackPlayer {
    private int stunTime;
    public MedusaPlayer() {
        this.heath=80;
        this.attack=10;
    }
    
    @Override
    public void attack(AttackPlayer attackPlayer) {
        
    }

    @Override
    public void guard(AttackPlayer attackPlayer) {
        Answer a=attackPlayer.answers.get(attackPlayer.answers.size()-1);
        if (this.answers.contains(a)) {
            //attackPlayer.isStuned=true;
            freeze(5000, attackPlayer);
            }
    }

    @Override
    public void power(AttackPlayer attackPlayer) {
        if(attackPlayer.answers.size()==5){
            freeze(10000, attackPlayer);
        }
    }
    public void freeze(int time, AttackPlayer attackPlayer){
        new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    attackPlayer.isStuned = true;

                    try {
                    Thread.sleep(time);                
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                    attackPlayer.isStuned = false;
                }
            }).start();
    }
}
