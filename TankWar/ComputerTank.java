package com.lbytech.TankWar;

import java.util.Vector;

public class ComputerTank extends Tank implements Runnable {
    int step = 0;
    public ComputerTank(int x,int y, int direction) {
        super(x,y,direction);
    }

    public void shoot(){
        Bullet bullet = new Bullet(this);
        bullets.add(bullet);
        Thread thread = new Thread(bullet);
        thread.start();
    }

    public void run(){
        while(true){
            //移动
            switch(this.getDirection()){
                case 0:{
                    if(this.getY()>0) {
                        moveUp();
                    }
                    step++;
                    break;
                }
                case 1:{
                    if(this.getY()+60<750) {
                        moveDown();
                    }
                    step++;
                    break;
                }
                case 2:{
                    if(this.getX()>0) {
                        moveLeft();
                    }
                    step++;
                    break;
                }
                case 3:{
                    if(this.getX()+60<1000) {
                        moveRight();
                    }
                    step++;
                    break;
                }
            }
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(step % 30 == 0) {
                this.setDirection((int) (Math.random() * 4));
                step = 0;
            }
            if(this.isLive == false)    break;

            if(this.bullets.size()<=5 && step % 15 == 0){
                shoot();
            }
        }
    }
}
