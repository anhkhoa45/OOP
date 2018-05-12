import Phaser from 'phaser'

export default class extends Phaser.State {
  preload() {
    this.load.image('background', './assets/images/background.png');
    this.load.atlas('archer', './assets/sprites/archer.png', './assets/sprites/archer.json');
    this.load.atlas('girl', './assets/sprites/girl.png', './assets/sprites/girl.json');
    this.load.atlas('wizard', './assets/sprites/wizard.png', './assets/sprites/wizard.json');
    this.load.atlas('knight', './assets/sprites/knight.png', './assets/sprites/knight.json');
    
  }

  create () {
    this.add.sprite(0, 0, 'background');

    this.girl = this.add.sprite(0, this.game.height - 350, 'girl', 'idle/0.png');
    this.girl.scale.setTo(0.4, 0.4);

    // animation
    this.girl.animations.add('idle', Phaser.Animation.generateFrameNames('idle/', 0, 10, '.png', 1), 15, true, false);
    this.girl.animations.add('attack', Phaser.Animation.generateFrameNames('attack/', 0, 18, '.png', 1), 15, false, false);
    
    this.girl.animations.add('hurt', Phaser.Animation.generateFrameNames('hurt/', 0, 19, '.png', 1), 15, false, false);
    this.girl.animations.add('die', Phaser.Animation.generateFrameNames('die/', 0, 21, '.png', 1), 15, false, false);

    this.girl.animations.play('idle');
  }
  
  girlAttack(){
    let anim = this.girl.animations.play('attack');
    anim.onComplete.add(() => { this.girl.animations.play('idle'); }, this);
  }





  //   create () {
  //   this.add.sprite(0, 0, 'background');

  //   this.archer = this.add.sprite(0, this.game.height - 350, 'archer', 'idle/0.png');
  //   this.archer.scale.setTo(0.5, 0.5);

  //   // animation
  //   this.archer.animations.add('idle', Phaser.Animation.generateFrameNames('idle/', 0, 9, '.png', 1), 10, true, false);
  //   this.archer.animations.add('attack', Phaser.Animation.generateFrameNames('attack/', 0, 9, '.png', 1), 10, false, false);
    
  //   this.archer.animations.add('hurt', Phaser.Animation.generateFrameNames('hurt/', 0, 9, '.png', 1), 10, false, false);
  //   this.archer.animations.add('die', Phaser.Animation.generateFrameNames('die/', 0, 9, '.png', 1), 10, false, false);

  //   this.archer.animations.play('idle');
  // }
  
  // archerAttack(){
  //   let anim = this.archer.animations.play('attack');
  //   anim.onComplete.add(() => { this.archer.animations.play('idle'); }, this);
  // }



  // create () {
  //   this.add.sprite(0, 0, 'background');

  //   this.knight = this.add.sprite(0, this.game.height - 350, 'knight', 'idle/0.png');
  //   this.knight.scale.setTo(0.5, 0.5);

  //   // animation
  //   this.knight.animations.add('idle', Phaser.Animation.generateFrameNames('idle/', 0, 9, '.png', 1), 10, true, false);
  //   this.knight.animations.add('attack', Phaser.Animation.generateFrameNames('attack/', 0, 9, '.png', 1), 10, false, false);
    
  //   this.knight.animations.add('hurt', Phaser.Animation.generateFrameNames('hurt/', 0, 9, '.png', 1), 10, false, false);
  //   this.knight.animations.add('die', Phaser.Animation.generateFrameNames('die/', 0, 9, '.png', 1), 10, false, false);

  //   this.knight.animations.play('idle');
  // }
  
  // knightAttack(){
  //   let anim = this.knight.animations.play('attack');
  //   anim.onComplete.add(() => { this.knight.animations.play('idle'); }, this);
  // }

  // create () {
  //   this.add.sprite(0, 0, 'background');

  //   this.wizard = this.add.sprite(0, this.game.height - 350, 'wizard', 'Stand/0.png');
  //   this.wizard.scale.setTo(0.5, 0.5);

  //   // animation
  //   this.wizard.animations.add('idle', Phaser.Animation.generateFrameNames('idle/', 0, 9, '.png', 1), 10, true, false);
  //   this.wizard.animations.add('attack', Phaser.Animation.generateFrameNames('attack/', 0, 9, '.png', 1), 10, false, false);
    
  //   this.wizard.animations.add('hurt', Phaser.Animation.generateFrameNames('hurt/', 0, 9, '.png', 1), 10, false, false);
  //   this.wizard.animations.add('die', Phaser.Animation.generateFrameNames('Die/', 0, 9, '.png', 1), 10, false, false);

  //   this.wizard.animations.play('idle');
  // }
  
  // wizardAttack(){
  //   let anim = this.wizard.animations.play('attack');
  //   anim.onComplete.add(() => { this.wizard.animations.play('idle'); }, this);
  // }




  update(){
    
  }
}
