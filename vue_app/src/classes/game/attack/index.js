import Phaser from 'phaser'

import config from './config'

const docElement = document.documentElement;
const width = docElement.clientWidth > config.gameWidth ? config.gameWidth : docElement.clientWidth;
const height = docElement.clientHeight > config.gameHeight ? config.gameHeight : docElement.clientHeight;

function preload() {
  this.load.setBaseURL('/');

  this.load.image('sky', '../../../assets/img/game_bg1.jpg');
  this.load.image('logo', '../../../assets/img/phaser3-logo.png');
  this.load.image('red', '../../../assets/img/red.png');
}

function create() {
  this.add.image(570, 300, 'sky');

  let particles = this.add.particles('red');

  let emitter = particles.createEmitter({
    speed: 100,
    scale: {start: 1, end: 0},
    blendMode: 'ADD'
  });

  let logo = this.physics.add.image(570, 300, 'logo');

  logo.setVelocity(100, 200);
  logo.setBounce(1, 1);
  logo.setCollideWorldBounds(true);

  emitter.startFollow(logo);
}


export default class Game extends Phaser.Game {
  constructor() {

    super({
      type: Phaser.AUTO,
      width: width,
      height: height,
      parent: 'gameCanvas',
      physics: {
        default: 'arcade',
        arcade: {
          gravity: { y: 200 }
        }
      },
      scene: {
        preload: preload,
        create: create
      }
    });
  }
}
