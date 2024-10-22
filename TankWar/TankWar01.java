package com.lbytech.TankWar;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankWar01 extends JFrame {
    private MyPanel myPanel = null;

    public TankWar01() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1:新游戏，2:继续上局");
        int choice = scanner.nextInt();
        myPanel = new MyPanel(choice);
        Thread thread = new Thread(myPanel);
        thread.start();
        this.add(myPanel);
        this.addKeyListener(myPanel);
        this.setTitle("Tank War");
        this.setSize(1300, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("关闭");
                Recorder.keepRecord();
            }
        });
    }
    public static void main(String[] args) {

        new TankWar01();

    }
}
