package com.lbytech.TankWar;

import java.io.*;
import java.util.Vector;

public class Recorder {

    private static int allComputerTankNum = 0;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String filePath = "staticRecord.txt";
    private static Vector<ComputerTank> computerTanks = null;
    private static UserTank userTank = null;
    //Node的vector用于保存坦克信息
    private static Vector<Node> nodes = new Vector<>();

    public static void setComputerTanks(Vector<ComputerTank> computerTanks) {
        Recorder.computerTanks = computerTanks;
    }

    public static void setUserTank(UserTank userTank) {
        Recorder.userTank = userTank;
    }

    public static String getFilePath() {
        return filePath;
    }

    //保存当局信息
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(filePath));
            bw.write(allComputerTankNum + "\r\n");
            bw.write(userTank.getX()+" "+ userTank.getY()+" "+ userTank.getDirection()+"\r\n");
            for (int i = 0; i < computerTanks.size(); i++) {
                ComputerTank computerTank = computerTanks.get(i);
                if(computerTank.isLive){
                    bw.write(computerTank.getX() + " "+ computerTank.getY()+" "+ computerTank.getDirection()+"\r\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                bw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    //读取recordPath，恢复相关信息
    static public Vector<Node> getNodesAndNum() {
        try {
            br = new BufferedReader(new FileReader(filePath));
            allComputerTankNum = Integer.parseInt(br.readLine());
            String line;
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                int x = Integer.parseInt(xyd[0]);
                int y = Integer.parseInt(xyd[1]);
                int direction = Integer.parseInt(xyd[2]);
                nodes.add(new Node(x,y,direction));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return nodes;
    }
    public static void setAllComputerTankNum(int allComputerTankNum) {
        Recorder.allComputerTankNum = allComputerTankNum;
    }

    public static int getAllComputerTankNum() {
        return allComputerTankNum;
    }

    public static void plusAllComputerTankNum() {
        allComputerTankNum++;
    }
}
