package nju.zjl.cvs.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import nju.zjl.cvs.game.Constants;
import nju.zjl.cvs.game.Operation;



public class GameServer implements Runnable {
    public GameServer(InetAddress p1Ip, int p1Udp, InetAddress p2Ip, int p2Udp, DatagramSocket datagramSocket){
        this.p1Ip = p1Ip;
        this.p1Udp = p1Udp;
        this.p2Ip = p2Ip;
        this.p2Udp = p2Udp;
        this.datagramSocket = datagramSocket;
        operationList.add(new LinkedList<>());
        operationList.add(new LinkedList<>());
        operationList.add(new LinkedList<>());
    }



    @Override
    public void run(){
        Sender sender = new Sender();
        new Thread(sender).start();
        byte[] buf = new byte[256];
        try(
            ByteArrayInputStream bin = new ByteArrayInputStream(buf);
            ObjectInputStream objin = new ObjectInputStream(bin);
        ){
            while(exit < 2)try{
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(datagramPacket);
                objin.reset();
                AppPacket pkt = (AppPacket)objin.readObject();
                switch(pkt.type){
                    case 1:
                        synchronized(last){
                            operationList.get(last).add(pkt.payload1[0]);
                        }
                        break;
                    case 2:
                        exit++;
                        break;
                    default:
                        break;
                }
            }catch(IOException | ClassNotFoundException exception){
                System.err.println("an error occured when receive or read packet");
                exception.printStackTrace();
            }finally{
                sender.stop();
            }
        }catch(IOException exception){
            System.err.println("an error occured when create input stream");
            exception.printStackTrace();
        }
    }

    private InetAddress p1Ip;
    private int p1Udp;
    private InetAddress p2Ip;
    private int p2Udp;
    private DatagramSocket datagramSocket;

    private Integer last = 2;
    private Integer logicFrame = 0;
    private Vector<LinkedList<Operation>> operationList = new Vector<>(3);
    private int exit = 0;

    class Sender implements Runnable {
        @Override
        public void run(){
            try(
                ByteArrayOutputStream bout = new ByteArrayOutputStream(256);
                ObjectOutputStream objout = new ObjectOutputStream(bout);
            ){
                while(!terminate)try{
                    TimeUnit.MILLISECONDS.sleep(1000 /(Constants.FPS / 3));
                    AppPacket pkt;
                    synchronized(last){
                        pkt = new AppPacket(0, logicFrame, operationList.get((last + 1) % 3).toArray(new Operation[0]), operationList.get((last + 2) % 3).toArray(new Operation[0]), operationList.get(last).toArray(new Operation[0]));
                        last = (last + 1) % 3;
                        operationList.get(last).clear();
                    }
                    objout.reset();
                    objout.writeObject(pkt);
                    objout.flush();
                    DatagramPacket datagramPacket = new DatagramPacket(bout.toByteArray(), bout.size(), p1Ip, p1Udp);
                    datagramSocket.send(datagramPacket);
                    datagramPacket.setAddress(p2Ip);
                    datagramPacket.setPort(p2Udp);
                    datagramSocket.send(datagramPacket);
                    logicFrame++;
                }catch(InterruptedException exception){
                    exception.printStackTrace();
                }catch(IOException exception){
                    System.err.println("an error occured when send or build packet");
                    exception.printStackTrace();
                }
            }catch(IOException exception){
                System.err.println("an error occured when create output stream");
                exception.printStackTrace();
            }
        }

        public void stop(){
            terminate = true;
        }
        private boolean terminate = false;
    }
}
