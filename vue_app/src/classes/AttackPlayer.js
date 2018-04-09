class AttackPlayer {
  constructor(health, attack){
    this.health = health;
    this.attack = attack;
    this.isStuned = false;
    this.isPowered = false;
    this.isBlack = false;
    this.numBeingAttacked = 0;
  }
}

export default AttackPlayer;
