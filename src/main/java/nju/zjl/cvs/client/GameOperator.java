package nju.zjl.cvs.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

import nju.zjl.cvs.game.Constants.Camp;
import nju.zjl.cvs.game.Operation;
import nju.zjl.cvs.game.Operator;
import nju.zjl.cvs.server.AppPacket;



public class GameOperator implements Operator, Runnable{
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
        synchronized(lock){
            if(logicFrame >= expected){
                return null;
            }
            else{
                return operationList.get(logicFrame);
            }
        }
    }

    @Override
    public void run(){
        byte[] buf = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
        while(!gameOver)try{
            datagramSocket.receive(datagramPacket);
            ObjectInputStream objin = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData()));
            AppPacket pkt = (AppPacket)objin.readObject();
            synchronized(lock){
                if(expected + 3 >= operationList.size()){
                    operationList.ensureCapacity(operationList.size() + 180);
                }
                if(pkt.logicFrame >= expected){
                    operationList.add(pkt.logicFrame, pkt.payload3);
                }
                if(pkt.logicFrame - 1 >= expected){
                    operationList.add(pkt.logicFrame - 1, pkt.payload2);
                }
                if(pkt.logicFrame - 2 >= expected){
                    operationList.add(pkt.logicFrame - 2, pkt.payload1);
                }
                expected = pkt.logicFrame + 1;
            }
        }catch(IOException | ClassNotFoundException exception){
            System.err.println("an error occurred when receive or read packet");
            exception.printStackTrace();
        }

        exitConnection();
    }

    public void connect(String hostIp, int port, Consumer<Boolean> establish, Consumer<Camp> begin){
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
            establish.accept(false);
        }

        try(
            Socket client = new Socket(ip, port);
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
        ){
            datagramSocket = new DatagramSocket();
            out.writeInt(datagramSocket.getLocalPort());
            udp = in.readInt();
            establish.accept(true);

            boolean c = in.readBoolean();
            begin.accept(c ? Camp.CALABASH : Camp.SNAKE);
        }catch(IOException exception){
            System.err.println("an error occurred when establish connection or game");
            exception.printStackTrace();
            establish.accept(false);
        }        
    }

    public void terminate(){
        gameOver = true;
    }

    public String saveRecord(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        Date time = new Date();
        String fileName = formatter.format(time) + ".record";
        File file = new File(fileName);
        try{
            file.createNewFile();
        }catch(IOException exception){
            exception.printStackTrace();
            return null;
        }

        try(
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream objout = new ObjectOutputStream(new BufferedOutputStream(fout));
        ){
            objout.writeInt(operationList.size());
            for(Operation[] ops : operationList){
                objout.writeObject(ops);
            }
        }catch(IOException exception){
            exception.printStackTrace();
            return null;
        }
        return fileName;
    }

    protected void exitConnection(){
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

    protected DatagramSocket datagramSocket;
    protected InetAddress ip;
    protected int udp;


    private final Object lock = new Object();
    protected boolean gameOver = false;
    protected int expected = 0;
    protected ArrayList<Operation[]> operationList = new ArrayList<>(180);
}
