package model;

import java.util.Random;

public class DracularPlayer extends AttackPlayer {

    private int luckyNumber;

    public DracularPlayer() {
        this.health = 100;
        this.attack = 6;
        this.isStuned = false;
        this.isPowered = false;
        this.isBlack = false;
        Random random = new Random();
        this.luckyNumber = random.nextInt(10) + 1;
    }

    @Override
    public void attack(AttackPlayer guardPlayer) {
        if (this.isDead() || this.isStuned) {
            return;
        }
        if (isPowered) {
            this.power(guardPlayer);
        } else {
            if (!guardPlayer.guard(this)) {
                this.health += this.attack / 2;
                guardPlayer.health -= this.attack;
            }
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
        if (!this.isPowered) {
            this.isPowered = true;
        } else {
            guardPlayer.isBlack = true;
            //chuyen man hinh thanh mau den trong 10s
        }
    }
}
