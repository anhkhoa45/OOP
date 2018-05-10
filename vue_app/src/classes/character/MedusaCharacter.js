import AttackCharacter from './AttackCharacter';

class MedusaCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.name = name;
  }
}

export default MedusaCharacter;
