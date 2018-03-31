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
    @Override
    public void attacking(AttackPlayer attackPlayer) {

    }

    @Override
    public boolean guarding(AttackPlayer attackPlayer) {
        return true;
    }

    @Override
    public void powering(AttackPlayer attackPlayer) {
        if(attackPlayer.answers.size()==5){
        final long timeInterval = 5000;
          Runnable runnable = new Runnable() {

          public void run() {
            while (true) {
              // ------- code for task to run
              attackPlayer.heath-=10;
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
