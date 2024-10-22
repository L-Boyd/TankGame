package com.lbytech.TankWar;

public class Boom implements Runnable {
    private int x;
    private int y;
    private int life = 60;

    public Boom(int x, int y) {
        this.y = y;
        this.x = x;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getLife() {
        return life;
    }
    public void run() {
        while(life>0){
            life-=2;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
