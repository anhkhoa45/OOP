import AttackCharacter from './AttackCharacter';

class DraculaCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.name = name;
  }
}

export default DraculaCharacter;
