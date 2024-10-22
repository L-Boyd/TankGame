package com.lbytech.TankWar;

//import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

class MyPanel extends JPanel implements KeyListener, Runnable{
    UserTank userTank = null;
    Vector<ComputerTank> computerTanks = new Vector<>();
    Vector<Boom> booms = new Vector<>();
    //用于恢复上局坦克
    Vector<Node> nodes = null;

    public MyPanel(int choice) {//
        Recorder.setComputerTanks(computerTanks);


        if (choice == 1||!new File(Recorder.getFilePath()).exists()) {//新游戏
            userTank = new UserTank(400, 100, 2);
            Recorder.setUserTank(userTank);
            computerTanks.add(new ComputerTank(100,100,1));
            computerTanks.add(new ComputerTank(200,100,1));
            computerTanks.add(new ComputerTank(300,100,1));
            computerTanks.add(new ComputerTank(400,100,1));
            computerTanks.add(new ComputerTank(500,100,1));
            computerTanks.add(new ComputerTank(600,100,1));
            computerTanks.add(new ComputerTank(700,100,1));
            computerTanks.add(new ComputerTank(800,200,1));
            computerTanks.add(new ComputerTank(800,300,1));
            computerTanks.add(new ComputerTank(800,400,1));
            computerTanks.add(new ComputerTank(800,500,1));
        }
        else if(choice == 2) {//继续上局
            nodes = Recorder.getNodesAndNum();
            Node utNode = nodes.get(0);
            userTank = new UserTank(utNode.getX(),utNode.getY(),utNode.getDirection());
            Recorder.setUserTank(userTank);
            for(int j = 1; j < nodes.size(); j++) {
                Node node = nodes.get(j);
                int x = node.getX();
                int y = node.getY();
                int direction = node.getDirection();
                computerTanks.add(new ComputerTank(x,y,direction));
            }
        }
        for(int j = 0; j < computerTanks.size(); j++) {//开启电脑坦克线程
            new Thread(computerTanks.get(j)).start();
        }

    }

    //展示战场信息
    public void showInfo(Graphics g) {
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 20);
        g.setFont(font);
        int num = Recorder.getAllComputerTankNum();
        g.drawString("您累计击毁坦克",1020,30);
        drawTank(1020,50,g,1,1);
        g.setColor(Color.black);
        g.drawString(""+num,1080,100);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0, 0, 1000, 750);//背景
        showInfo(g);
        //画用户坦克和发射的炮弹
        if(userTank!=null && userTank.isLive) {//如果用户坦克存活
            //画用户坦克
            drawTank(userTank.getX(), userTank.getY(), g, userTank.getDirection(), 0);
            //画用户发射的炮弹们
            for (int j = 0; j<userTank.bullets.size(); j++) {
                if(userTank.bullets.get(j).isLive) {//如果炮弹还存活
                    drawBullet(userTank.bullets.get(j), g);//画出炮弹
                    //System.out.println("子弹x：" + userTank.bullets.get(j).getX() + "子弹y：" + userTank.bullets.get(j).getY());
                }
                else{//如果炮弹不存活
                    userTank.bullets.remove(userTank.bullets.get(j));//将炮弹移除
                }
            }
        }
        else {//如果用户坦克不存活
            //System.out.println("游戏结束");
        }
        //如果坦克重叠，则调转方向
        for(int j = 0; j<computerTanks.size(); j++) {
            for(int i = 0; i<computerTanks.size(); i++){
                ComputerTank ctj = computerTanks.get(j);
                ComputerTank cti = computerTanks.get(i);
                int ctjX = ctj.getX();
                int ctjY = ctj.getY();
                int ctiX = cti.getX();
                int ctiY = cti.getY();
                if(cti != ctj) {
                    switch (ctj.getDirection()) {
                        case 0: {//j向上
                            if (cti.getDirection() == 0 || cti.getDirection() == 1) {
                                if ((ctjX >= ctiX && ctjX <= ctiX + 40) || (ctjX + 40 >= ctiX && ctjX + 40 <= ctiX + 40)) {//左上角和右上角的点是否进入i
                                    if (ctjY >= ctiY && ctjY <= ctiY + 60) {
                                        ctj.setDirection(1);
                                        ctj.moveDown();
                                    }
                                }
                            } else if (cti.getDirection() == 2 || cti.getDirection() == 3) {
                                if ((ctjX >= ctiX && ctjX <= ctiX + 60) || (ctjX + 40 >= ctiX && ctjX + 40 <= ctiX + 60)) {
                                    if (ctjY >= ctiY && ctjY <= ctiY + 40) {
                                        ctj.setDirection(1);//转向
                                        ctj.moveDown();
                                    }
                                }
                            }
                            break;
                        }
                        case 1: {//j向下
                            if (cti.getDirection() == 0 || cti.getDirection() == 1) {
                                if ((ctjX >= ctiX && ctjX <= ctiX + 40) || (ctjX + 40 >= ctiX && ctjX + 40 <= ctiX + 40)) {//左上角和右上角的点是否进入i
                                    if (ctjY + 60 >= ctiY && ctjY <= ctiY + 60) {
                                        ctj.setDirection(0);
                                        ctj.moveUp();
                                    }
                                }
                            } else if (cti.getDirection() == 2 || cti.getDirection() == 3) {
                                if ((ctjX >= ctiX && ctjX <= ctiX + 60) || (ctjX + 40 >= ctiX && ctjX + 40 <= ctiX + 60)) {
                                    if (ctjY + 60 >= ctiY && ctjY + 60 <= ctiY + 40) {
                                        ctj.setDirection(0);//转向
                                        ctj.moveUp();
                                    }
                                }
                            }
                            break;
                        }
                        case 2: {//j向左
                            if (cti.getDirection() == 0 || cti.getDirection() == 1) {
                                if ((ctjY >= ctiY && ctjY <= ctiY + 60) || (ctjY + 40 >= ctiY && ctjX + 40 <= ctiY + 60)) {//左上角和左下角的点是否进入i
                                    if (ctjX >= ctiX && ctjX <= ctiX + 40) {
                                        ctj.setDirection(3);
                                        ctj.moveRight();
                                    }
                                }
                            } else if (cti.getDirection() == 2 || cti.getDirection() == 3) {
                                if ((ctjY >= ctiY && ctjY <= ctiY + 40) || (ctjY + 40 >= ctiY && ctjX + 40 <= ctiY + 40)) {//左上角和左下角的点是否进入i
                                    if (ctjX >= ctiX && ctjX <= ctiX + 60) {
                                        ctj.setDirection(3);
                                        ctj.moveRight();
                                    }
                                }
                            }
                            break;
                        }
                        case 3: {//j向右
                            if (cti.getDirection() == 0 || cti.getDirection() == 1) {
                                if ((ctjY >= ctiY && ctjY <= ctiY + 60) || (ctjY + 40 >= ctiY && ctjX + 40 <= ctiY + 60)) {//右上角和右下角的点是否进入i
                                    if (ctjX + 60 >= ctiX && ctjX + 60 <= ctiX + 40) {
                                        ctj.setDirection(2);
                                        ctj.moveLeft();
                                    }
                                }
                            } else if (cti.getDirection() == 2 || cti.getDirection() == 3) {
                                if ((ctjY >= ctiY && ctjY <= ctiY + 40) || (ctjY + 40 >= ctiY && ctjX + 40 <= ctiY + 40)) {//左上角和左下角的点是否进入i
                                    if (ctjX + 60 >= ctiX && ctjX + 60 <= ctiX + 60) {
                                        ctj.setDirection(2);
                                        ctj.moveLeft();
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }

        //画人机和他们的炮弹们
        for(int j = 0; j < computerTanks.size(); j++){
            //画第j个人机坦克的第k个炮弹
            for (int k = 0;k<computerTanks.get(j).bullets.size(); k++) {
                if(computerTanks.get(j).bullets.get(k).isLive) {//如果炮弹还存活
                    drawBullet(computerTanks.get(j).bullets.get(k), g);//画出炮弹
                }
                else{//如果炮弹不存活
                    computerTanks.get(j).bullets.remove(computerTanks.get(j).bullets.get(k));//将炮弹移除
                }
            }
            if(computerTanks.get(j).isLive) {//如果人机坦克存活
                //画人机坦克
                drawTank(computerTanks.get(j).getX(), computerTanks.get(j).getY(), g, computerTanks.get(j).getDirection(), 1);
            }
            else{//如果人机坦克不存活
                //将其从Vector中移除
                computerTanks.remove(computerTanks.get(j));
                Recorder.plusAllComputerTankNum();
            }

        }

        //画坦克爆炸
        for(int j = 0; j<booms.size(); j++){
            drawBoom(booms.get(j), g);
        }
    }

    //画坦克函数
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        //x，y是坦克左上角坐标，direction是方向。
        switch (type) {
            case 0: {//我们的坦克
                g.setColor(Color.cyan);
                break;
            }
            case 1: {//敌人的坦克
                g.setColor(Color.pink);
                break;
            }
        }
        switch (direction) {
            case 0: {//向上
                g.fill3DRect(x, y, 10, 60, false);//履带
                g.fill3DRect(x+30, y, 10, 60, false);//履带
                g.fill3DRect(x+10, y+10, 20, 40, false);//坦克身体
                g.fillOval(x+10, y+20, 20, 20);//坦克盖子
                g.fill3DRect(x+10+10-3, y+30-40, 6, 40, false);//炮筒
                break;
            }
            case 1: {//向下
                g.fill3DRect(x, y, 10, 60, false);//履带
                g.fill3DRect(x+30, y, 10, 60, false);//履带
                g.fill3DRect(x+10, y+10, 20, 40, false);//坦克身体
                g.fillOval(x+10, y+20, 20, 20);//坦克盖子
                g.fill3DRect(x+10+10-3, y+30, 6, 40, false);//炮筒
                break;
            }
            case 2: {//向左
                g.fill3DRect(x, y, 60, 10, false);//履带
                g.fill3DRect(x, y+30, 60, 10, false);//履带
                g.fill3DRect(x+10, y+10, 40, 20, false);//坦克身体
                g.fillOval(x+30-10, y+10, 20, 20);//坦克盖子
                g.fill3DRect(x+30-40, y+10+10-3, 40, 6, false);//炮筒
                break;
            }
            case 3: {//向右
                g.fill3DRect(x, y, 60, 10, false);//履带
                g.fill3DRect(x, y+30, 60, 10, false);//履带
                g.fill3DRect(x+10, y+10, 40, 20, false);//坦克身体
                g.fillOval(x+30-10, y+10, 20, 20);//坦克盖子
                g.fill3DRect(x+30, y+10+10-3, 40, 6, false);//炮筒
                break;
            }
        }
    }
    //画炮弹函数
    public void drawBullet(Bullet bullet, Graphics g){
        g.setColor(Color.BLACK);
        g.fillOval(bullet.getX(),bullet.getY(),6,6);
    }
    //画爆炸函数
    public void drawBoom(Boom boom, Graphics g){
        g.setColor(Color.RED);
        g.fillOval(boom.getX(),boom.getY(),boom.getLife(),boom.getLife());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(userTank!=null && userTank.isLive) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                if (userTank.getX() > 0) {
                    userTank.moveLeft();
                    userTank.setDirection(2);
                }
                this.repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                if (userTank.getY() + 60 < 750) {
                    userTank.moveDown();
                    userTank.setDirection(1);
                }
                this.repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                if (userTank.getX() + 60 < 1000) {
                    userTank.moveRight();
                    userTank.setDirection(3);
                }
                this.repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                if (userTank.getY() > 0) {
                    userTank.moveUp();
                    userTank.setDirection(0);
                }
                this.repaint();
            }
            if (e.getKeyCode() == KeyEvent.VK_J) {
                userTank.shoot();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_Q) {
                computerTanks.get(0).shoot();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //判断我方炮弹是否打中敌方坦克
    public boolean hitTankOrNot(Tank shootTank, Tank shootedTank){
        if(shootTank!=null && shootedTank!=null && shootTank.isLive) {
            switch (shootedTank.getDirection()) {
                case 0: {
                    for (Bullet utbullet : shootTank.bullets) {
                        if (utbullet.getX() >= shootedTank.getX() && utbullet.getX() <= shootedTank.getX() + 40) {
                            if (utbullet.getY() >= shootedTank.getY() - 10 && utbullet.getY() <= shootedTank.getY() + 60) {
                                utbullet.isLive = false;//命中的炮弹爆炸
                                return true;
                            }
                        }
                    }
                    break;
                }
                case 1: {
                    for (Bullet utbullet : shootTank.bullets) {
                        if (utbullet.getX() >= shootedTank.getX() && utbullet.getX() <= shootedTank.getX() + 40) {
                            if (utbullet.getY() >= shootedTank.getY() && utbullet.getY() <= shootedTank.getY() + 70) {
                                utbullet.isLive = false;
                                return true;
                            }
                        }
                    }
                    break;
                }
                case 2: {
                    for (Bullet utbullet : shootTank.bullets) {
                        if (utbullet.getX() >= shootedTank.getX() - 10 && utbullet.getX() <= shootedTank.getX() + 60) {
                            if (utbullet.getY() >= shootedTank.getY() && utbullet.getY() <= shootedTank.getY() + 40) {
                                utbullet.isLive = false;
                                return true;
                            }
                        }
                    }
                    break;
                }
                case 3: {
                    for (Bullet utbullet : shootTank.bullets) {
                        if (utbullet.getX() >= shootedTank.getX() && utbullet.getX() <= shootedTank.getX() + 70) {
                            if (utbullet.getY() >= shootedTank.getY() && utbullet.getY() <= shootedTank.getY() + 40) {
                                utbullet.isLive = false;
                                return true;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.repaint();
            //击中敌方坦克，爆炸，击中我方坦克，爆炸
            for(int i = 0; i<computerTanks.size(); i++){
                //如果用户炮弹打中敌方坦克
                if(hitTankOrNot(userTank,computerTanks.get(i))){
                    //computerTanks.remove(computerTanks.get(i));
                    computerTanks.get(i).isLive=false;//敌方坦克死亡
                    Boom boom = new Boom(computerTanks.get(i).getX(),computerTanks.get(i).getY());//坦克产生爆炸
                    booms.add(boom);
                    new Thread(boom).start();
                }
                if(hitTankOrNot(computerTanks.get(i),userTank)){
                    userTank.isLive=false;
                    Boom boom = new Boom(userTank.getX(),userTank.getY());
                    booms.add(boom);
                    new Thread(boom).start();
                    userTank = null;
                }
            }
        }
    }
}

