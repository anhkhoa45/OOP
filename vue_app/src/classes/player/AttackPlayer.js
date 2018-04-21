class AttackPlayer {
  constructor(id, name, health, attack){
    this.id = id;
    this.name = name;
    this.health = health;
    this.attack = attack;
    this.isStuned = false;
    this.isPowered = false;
    this.isBlack = false;
    this.numBeingAttacked = 0;
  }
}

export default AttackPlayer;
