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
            server = new ServerSocket(2345);
        }catch(IOException exception){
            System.err.println("an error occured when create Serversocket");
            exception.printStackTrace();
            return;
        }
        ExecutorService exec = Executors.newCachedThreadPool();
        while(true){
            try(
                Socket client1 = server.accept();
                Socket client2 = server.accept();
                DataInputStream in1 = new DataInputStream(client1.getInputStream());
                DataInputStream in2 = new DataInputStream(client2.getInputStream());
                DataOutputStream out1 = new DataOutputStream(client1.getOutputStream());
                DataOutputStream out2 = new DataOutputStream(client2.getOutputStream());
            ){
                int udp1 = in1.readInt();
                int udp2 = in2.readInt();

                DatagramSocket datagramSocket = new DatagramSocket();
                
                int i = new Random().nextInt(2);
                out1.writeInt(datagramSocket.getLocalPort());
                out2.writeInt(datagramSocket.getLocalPort());
                out1.writeInt(i);
                out2.writeInt(~i);

                exec.execute(new GameServer(client1.getInetAddress(), udp1, client2.getInetAddress(), udp2, datagramSocket));
            }catch(IOException exception){
                System.err.println("an error occured when create connection");
                exception.printStackTrace();
            }
        }
    }
}
