import AttackCharacter from './AttackCharacter';

class DraculaCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.name = name;
    console.log("OK")
  }
}

export default DraculaCharacter;
