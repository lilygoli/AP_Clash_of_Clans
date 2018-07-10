package com.company.Multiplayer;

import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.UIs.AttackMapUI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class UDPReceiver extends Thread {
    private boolean running;
    private byte[] buf = new byte[2048];
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
            String received = new String(packet.getData(), 0, packet.getLength());
            try {
                ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                Game game = (Game) iStream.readObject();
                System.out.println(game);
              //  AttackMapUI.getController().setGame(game);
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
