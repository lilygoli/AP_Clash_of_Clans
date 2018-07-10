package com.company.Multiplayer;

import com.company.UIs.AttackMapUI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;

public class LiveAttackStreamer extends Thread{
    public void run() {
        while(true) {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            try {
                ObjectOutput oo = new ObjectOutputStream(bStream);
                oo.writeObject(AttackMapUI.getController().getGame());
               // System.out.println("streamer sent"+AttackMapUI.getController().getGame());
                oo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] buf = bStream.toByteArray();
            DatagramPacket gamePacket = new DatagramPacket(buf, buf.length, AttackMapUI.attackedIP, 8888);
            try {
                AttackMapUI.udpSocket.send(gamePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
