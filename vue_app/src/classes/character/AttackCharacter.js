import Character from './Character';

class AttackCharacter extends Character {
  constructor(health, attack){
    super();
    this.health = health;
    this.attack = attack;
    this.isStunned = false;
    this.isPowered = false;
    this.isBlackout = false;
    this.numBeingAttacked = 0;
    console.log("AttackCharacter")
  }

  updateState({health, attack, answers, isStunned, isPowered, isBlackout, numBeingAttacked}){
    super.updateState({answers});
    this.health = health;
    this.attack = attack;
    this.isStunned = isStunned;
    this.isPowered = isPowered;
    this.isBlackout = isBlackout;
    this.numBeingAttacked = numBeingAttacked;

    return this;
  }
}

export default Character;
