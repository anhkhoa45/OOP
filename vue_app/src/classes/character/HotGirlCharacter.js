import AttackCharacter from './AttackCharacter';

class HotGirlCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.name = name;
  }
}

export default HotGirlCharacter;
