import AttackCharacter from './AttackCharacter';

class KnightCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.name = name;
  }
}

export default KnightCharacter;
