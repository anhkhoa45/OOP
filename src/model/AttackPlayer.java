package model;

public abstract class AttackPlayer extends Player {
    protected int heath;
    protected int attack;
    protected boolean isStuned;
    protected boolean isPowered;

    abstract public void attack(AttackPlayer attackPlayer);
    abstract public void guard(AttackPlayer attackPlayer);
    abstract public void power(AttackPlayer attackPlayer);
}
