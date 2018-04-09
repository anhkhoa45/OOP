package model;

public abstract class AttackPlayer extends Player {
    protected int health;
    protected int attack;
    protected boolean isStuned;
    protected boolean isPowered;
    protected boolean isBlack;
    protected int numBeingAttacked;

    public AttackPlayer(){
        this.isStuned = false;
        this.isPowered = false;
        this.isBlack = false;
    }

    public AttackPlayer(Player player) {
        super(player.getId(), player.getSession());
    }

    abstract public void attack(AttackPlayer guardPlayer);

    abstract public boolean guard(AttackPlayer attackPlayer);

    abstract public void power(AttackPlayer guardPlayer);

    public boolean isDead() {
        return this.health <= 0;
    }
}
