package com.lbytech.TankWar;

import java.util.Vector;

public class UserTank extends Tank {

    public UserTank(int x, int y, int direction) {
        super(x, y, direction);
    }

    public void shoot(){
        if(bullets.size()<=5) {
            Bullet bullet = new Bullet(this);
            bullets.add(bullet);
            Thread thread = new Thread(bullet);
            thread.start();
        }
    }
}
