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
  }

  updateState({health, attack, answers, isStunned, isPowered, isBlackout, numBeingAttacked}){
    this.health = health;
    this.attack = attack;
    this.answers = answers;
    this.isStunned = isStunned;
    this.isPowered = isPowered;
    this.isBlackout = isBlackout;
    this.numBeingAttacked = numBeingAttacked;

    return this;
  }
}

export default Character;
