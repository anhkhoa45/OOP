package model;

public abstract class AttackPlayer extends Player {
    protected int heath;
    protected int attack;
    protected boolean isStuned;
    protected boolean isPowered;
    protected boolean isBlack;
    protected int numBeingAttacked;

    abstract public void attacking(AttackPlayer guardPlayer);

    abstract public boolean guarding(AttackPlayer attackPlayer);

    abstract public void powering(AttackPlayer guardPlayer);

    public boolean isDead() {
        if (this.heath <= 0) {
            return true;
        }
        return false;
    }
}
