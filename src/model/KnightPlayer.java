package model;

public class KnightPlayer extends AttackPlayer {

    public KnightPlayer() {
        this.heath = 120;
        this.attack = 12;
        this.isStuned = false;
        this.isPowered = false;
    }

    public void attacking(AttackPlayer guardPlayer) {
        if (this.isDead() || this.isStuned) {
            return;
        }
        if (isPowered) {
            this.powering(guardPlayer);
        } else {
            guardPlayer.heath -= this.attack;
            guardPlayer.guarding(this);
        }
    }

    @Override
    public boolean guarding(AttackPlayer attackPlayer) {
        return true;
    }

    @Override
    public void powering(AttackPlayer guardPlayer) {
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
        if (this.answers.contains(answer)) {
            return true;
        }
        return false;
    }
}
