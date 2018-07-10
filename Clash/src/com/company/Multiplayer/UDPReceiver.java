package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.Models.Soldiers.Archer;
import com.company.Models.Soldiers.Soldier;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.SideBarUI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPReceiver extends Thread {
    private boolean running;
    private byte[] buf = new byte[10000];
    @Override
    public void run() {
        running = true;

        while (running) {
            System.out.println("run");
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                AttackMapUI.udpSocket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
//            String received = new String(packet.getData(), 0, packet.getLength());
            try {
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                liveStreamingMessage liveStreamingMessage1;
                   liveStreamingMessage1 = (liveStreamingMessage) iStream.readObject();
                   System.out.println(liveStreamingMessage1.getTroops());
                if (liveStreamingMessage1.getTroops() == null){
                    System.out.println("so stuck");
                    continue;
                }
//                if(liveStreamingMessage.troops== null){
//                    liveStreamingMessage.troops= new ArrayList<Soldier>();
//                    Archer archer= new Archer(0);
//                    archer.setX(1);
//                    archer.setY(1);
//                    liveStreamingMessage.troops.add(archer);
//                }
                MapUI.getController().getGame().setTroops(liveStreamingMessage1.getTroops());
                MapUI.getController().getGame().setAttackedVillage(MapUI.getController().getGame());
                MapUI.isIsInDefense(true);
//                AttackMapUI.makeAttackGameBoard(SideBarUI.primaryStage,MapUI.getController());
                iStream.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
//            if (received.equals("end")) {
//                running = false;
//                continue;
//            }
        }
        AttackMapUI.udpSocket.close();
    }

}
