package nju.zjl.cvs.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    public static void main(String[] args){
        ServerSocket server;
        try{
            server = new ServerSocket(23456);
        }catch(IOException exception){
            System.err.println("an error occured when create Serversocket");
            exception.printStackTrace();
            return;
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        DatagramSocket datagramSocket = null;
        Socket client1 = null;
        Socket client2 = null;

        while(true)try{
            datagramSocket = new DatagramSocket();

            client1 = server.accept();
            DataInputStream in1 = new DataInputStream(client1.getInputStream());
            DataOutputStream out1 = new DataOutputStream(client1.getOutputStream());
            int udp1 = in1.readInt();
            out1.writeInt(datagramSocket.getLocalPort());
            System.out.println("client1");

            client2 = server.accept();
            DataInputStream in2 = new DataInputStream(client2.getInputStream());
            DataOutputStream out2 = new DataOutputStream(client2.getOutputStream());
            int udp2 = in2.readInt();
            out2.writeInt(datagramSocket.getLocalPort());
            System.out.println("client2");

            boolean c = new Random().nextBoolean();
            out1.writeBoolean(c);
            out2.writeBoolean(!c);
            System.out.println("begin");

            exec.execute(new GameServer(client1.getInetAddress(), udp1, client2.getInetAddress(), udp2, datagramSocket));

            client1.close();
            client2.close();
        }catch(IOException exception){
            System.err.println("an error occured when establish game");
            exception.printStackTrace();
            if(datagramSocket != null){
                datagramSocket.close();
                datagramSocket = null;
            }
            try{
                if(client1 != null){
                    client1.close();
                    client1 = null;
                }
                if(client2 != null){
                    client2.close();
                    client2 = null;
                }
            }catch(IOException exception2){
                System.err.println("an error occured when close TCP connection");
                exception2.printStackTrace();
            }
        }
    }
}
