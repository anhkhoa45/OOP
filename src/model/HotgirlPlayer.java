/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Ngoc
 */
public class HotgirlPlayer extends AttackPlayer{
    public HotgirlPlayer(){
        this.health = 100;
        this.attack = 6;
    }

    public HotgirlPlayer(Player player){
        super(player);
        this.health = 100;
        this.attack = 6;
    }

    @Override
    public void attack(AttackPlayer attackPlayer) {

    }

    @Override
    public boolean guard(AttackPlayer attackPlayer) {
        return true;
    }

    @Override
    public void power(AttackPlayer attackPlayer) {
        if(attackPlayer.answers.size()==5){
        final long timeInterval = 5000;
          Runnable runnable = new Runnable() {

          @Override
          public void run() {
            while (true) {
              // ------- code for task to run
              attackPlayer.health-=10;
              // ------- ends here
              try {
               Thread.sleep(timeInterval);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              }
            }
          };

          Thread thread = new Thread(runnable);
          thread.start();
        }
    }
}
