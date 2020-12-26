package nju.zjl.cvs.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import nju.zjl.cvs.game.Constants.Camp;
import nju.zjl.cvs.game.Operation;
import nju.zjl.cvs.game.Operator;
import nju.zjl.cvs.server.AppPacket;



public class GameOperator implements Operator, Runnable{
    public GameOperator(){
        gameOver = false;
    }

    @Override
    public void addOperation(Operation op){
        try{
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream objout = new ObjectOutputStream(bout);
            Operation[] temp = new Operation[1];
            temp[0] = op;
            AppPacket pkt = new AppPacket(1, -1, temp, new Operation[0], new Operation[0]);
            objout.writeObject(pkt);
            objout.flush();
            byte[] message = bout.toByteArray();
            DatagramPacket datagramPacket = new DatagramPacket(message, message.length, ip, udp);
            datagramSocket.send(datagramPacket);
        }catch(IOException exception){
            System.err.println("an error occurred when send packet");
            exception.printStackTrace();
        }
    }

    @Override
    public Operation[] getLogicFrames(int logicFrame){
        Operation[] ret = operationMap.get(logicFrame);
        operationMap.remove(logicFrame);
        return ret;
    }

    @Override
    public void run(){
        byte[] buf = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
        while(!gameOver)try{
            datagramSocket.receive(datagramPacket);
            System.out.println("receieve logicFrame: " + System.currentTimeMillis());
            ObjectInputStream objin = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));
            AppPacket pkt = (AppPacket)objin.readObject();
            if(pkt.logicFrame >= expected){
                operationMap.put(pkt.logicFrame, pkt.payload3);
            }
            if(pkt.logicFrame - 1 >= expected){
                operationMap.put(pkt.logicFrame - 1, pkt.payload2);
            }
            if(pkt.logicFrame - 2 >= expected){
                operationMap.put(pkt.logicFrame - 2, pkt.payload1);
            }
            expected = pkt.logicFrame + 1;
        }catch(IOException | ClassNotFoundException exception){
            System.err.println("an error occurred when receive or read packet");
            exception.printStackTrace();
        }

        try{
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream objout = new ObjectOutputStream(bout);
            AppPacket pkt = new AppPacket(2, -1, new Operation[0], new Operation[0], new Operation[0]);
            objout.writeObject(pkt);
            objout.flush();
            byte[] message = bout.toByteArray();
            DatagramPacket dp = new DatagramPacket(message, message.length, ip, udp);
            datagramSocket.send(dp);
        }catch(IOException exception){
            System.err.println("an error occurred when send packet");
            exception.printStackTrace();
        }
        datagramSocket.close();
    }

    public Camp connect(String hostIp, int port){
        byte[] ipBytes = new byte[4];
        String [] s = hostIp.split("\\.");
        for(int i = 0; i < 4; i++){
            ipBytes[i] = (byte)Integer.parseInt(s[i]);
        }
        try{
            ip = InetAddress.getByAddress(ipBytes);
        }catch(UnknownHostException exception){
            System.err.println("hostIp is illegal: " + hostIp);
            exception.printStackTrace();
            return null;
        }
        try(
            Socket client = new Socket(ip, port);
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
        ){
            datagramSocket = new DatagramSocket();
            out.writeInt(datagramSocket.getLocalPort());
            udp = in.readInt();
            boolean c = in.readBoolean();
            return c ? Camp.CALABASH : Camp.SNAKE;
        }catch(IOException exception){
            System.err.println("an error occurred when establish connection or game");
            exception.printStackTrace();
            return null;
        }        
    }

    public void terminate(){
        gameOver = true;
    }

    protected DatagramSocket datagramSocket;
    protected InetAddress ip;
    protected int udp;

    protected boolean gameOver;
    protected int expected = 0;
    protected ConcurrentHashMap<Integer, Operation[]> operationMap = new ConcurrentHashMap<>();
}
