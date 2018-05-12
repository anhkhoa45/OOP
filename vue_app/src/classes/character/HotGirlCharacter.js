import Phaser from 'phaser'
import AttackCharacter from './AttackCharacter';

class HotGirlCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.maxHealth = 100;
    this.name = name;

    this.atlas = {
      name: this.name,
      sprite: './assets/sprites/girl.png',
      spriteScript: './assets/sprites/girl.json'
    };

    this.animations = [
      {name: 'idle', frames: Phaser.Animation.generateFrameNames('idle/', 0, 10, '.png', 1), interval: 15, repeat: true},
      {name: 'attack', frames: Phaser.Animation.generateFrameNames('attack/', 0, 18, '.png', 1), interval: 15, repeat: false},
      {name: 'hurt', frames: Phaser.Animation.generateFrameNames('hurt/', 0, 19, '.png', 1), interval: 15, repeat: false},
      {name: 'die', frames: Phaser.Animation.generateFrameNames('die/', 0, 21, '.png', 1), interval: 15, repeat: false},
    ]
  }
}

export default HotGirlCharacter;
