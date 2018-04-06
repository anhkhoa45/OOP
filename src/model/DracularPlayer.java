package model;

import java.util.Random;

public class DracularPlayer extends AttackPlayer {

    private int luckyNumber;

    DracularPlayer() {
        this.health = 100;
        this.attack = 6;
        this.isStuned = false;
        this.isPowered = false;
        this.isBlack = false;
        Random random = new Random();
        this.luckyNumber = random.nextInt(10) + 1;
    }

    @Override
    public void attacking(AttackPlayer guardPlayer) {
        if (this.isDead() || this.isStuned) {
            return;
        }
        if (isPowered) {
            this.powering(guardPlayer);
        } else {
            if (guardPlayer.guarding(this) == false) {
                this.health += this.attack / 2;
                guardPlayer.health -= this.attack;
            }
        }
    }

    @Override
    public boolean guarding(AttackPlayer attackPlayer) {
        Random random = new Random();
        int anyNumber = random.nextInt(10) + 1;
        return this.luckyNumber == anyNumber;
    }

    @Override
    public void powering(AttackPlayer guardPlayer) {
        if (this.isPowered == false) {
            this.isPowered = true;
        }
        if (this.isPowered==true) {
            guardPlayer.isBlack = true;
            //chuyen man hinh thanh mau den trong 10s
        }
    }
}
