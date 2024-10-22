package com.lbytech.TankWar;

import java.util.Vector;

public class Tank {
    private int x;
    private int y;
    private int direction;
    boolean isLive = true;
    private int speed = 2;
    Vector<Bullet> bullets = new Vector<>();
    public Tank(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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
    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public void moveUp(){
        this.y-=speed;
    }
    public void moveDown(){
        this.y+=speed;
    }
    public void moveLeft(){
        this.x-=speed;
    }
    public void moveRight(){
        this.x+=speed;
    }
}
