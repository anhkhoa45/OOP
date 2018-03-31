package model;

public abstract class AttackPlayer extends Player {
    private int heath;
    private int attack;
    private int isStuned;
    private int isPowered;

    abstract public void attack(AttackPlayer attackPlayer);
    abstract public void guard(AttackPlayer attackPlayer);
    abstract public void power();
}
