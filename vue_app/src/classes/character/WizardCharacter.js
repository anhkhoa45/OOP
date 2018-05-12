import Phaser from 'phaser'

import AttackCharacter from './AttackCharacter';

class WizardCharacter extends AttackCharacter {
  constructor(name, health, attack){
    super(health, attack);
    this.maxHealth = 80;
    this.name = name;

    this.atlas = {
      name: this.name,
      sprite: './assets/sprites/wizard.png',
      spriteScript: './assets/sprites/wizard.json'
    };

    this.animations = [
      {name: 'idle', frames: Phaser.Animation.generateFrameNames('idle/', 0, 9, '.png', 1), interval: 10, repeat: true},
      {name: 'attack', frames: Phaser.Animation.generateFrameNames('attack/', 0, 9, '.png', 1), interval: 10, repeat: false},
      {name: 'hurt', frames: Phaser.Animation.generateFrameNames('hurt/', 0, 9, '.png', 1), interval: 10, repeat: false},
      {name: 'die', frames: Phaser.Animation.generateFrameNames('die/', 0, 9, '.png', 1), interval: 10, repeat: false},
    ]
  }
}

export default WizardCharacter;
