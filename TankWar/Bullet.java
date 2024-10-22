package com.lbytech.TankWar;

public class Bullet implements Runnable {
    private Tank tank;
    private int x;
    private int y;
    private int bulletDirection;
    boolean isLive = true;
    public Bullet(Tank tank) {
        this.tank = tank;
        switch (tank.getDirection()) {
            case 0: {
                // g.fill3DRect(x+10+10-3, y+30-40, 6, 40, false);//炮筒
                x = tank.getX() + 20 - 3;
                y = tank.getY() - 10;
                bulletDirection = 0;
                break;
            }
            case 1: {
                //g.fill3DRect(x+10+10-3, y+30, 6, 40, false);//炮筒
                x = tank.getX() + 20 - 3;
                y = tank.getY() + 30 + 40 - 6;
                bulletDirection = 1;
                break;
            }
            case 2: {
                //g.fill3DRect(x+30-40, y+10+10-3, 40, 6, false);//炮筒
                x = tank.getX() + 30 - 40;
                y = tank.getY() + 20 - 3;
                bulletDirection = 2;
                break;
            }
            case 3: {
                //g.fill3DRect(x+30, y+10+10-3, 40, 6, false)
                x = tank.getX() + 30 + 40 - 6;
                y = tank.getY() + 20 - 3;
                bulletDirection = 3;
                break;
            }
        }
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setLive(boolean live) {
        isLive = live;
    }

    public Tank getTank() {
        return tank;
    }

    @Override
    public void run() {
        switch (bulletDirection) {
            case 0:{
                while (this.getY()>=0){
                    y--;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            case 1:{
                while (this.getY()<=750){
                    y++;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            case 2:{
                while (this.getX()>=0){
                    x--;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
            case 3:{
                while (this.getX()<=1000){
                    x++;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            }
        }
        isLive = false;
    }

}
