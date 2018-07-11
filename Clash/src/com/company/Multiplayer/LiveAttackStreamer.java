package com.company.Multiplayer;

import com.company.Models.Soldiers.Soldier;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.util.ArrayList;

public class LiveAttackStreamer extends Thread{
    public void run() {
        while(true) {
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            try {
                ObjectOutput oo = new ObjectOutputStream(bStream);
                liveStreamingMessage lsm = new liveStreamingMessage();
                ArrayList<Integer> healths = new ArrayList<>();
                System.out.println(MapUI.getController().getGame().getTroops());
                for (int i = 0; i <30 ; i++) {
                    for (int j = 0; j <30 ; j++) {
                        healths.add(MapUI.getController().getGame().getVillage().getMap()[i][j].getStrength());
                    }
                }
                lsm.setTroops(MapUI.getController().getGame().getTroops());
                lsm.setHealths(healths);
                oo.writeObject(lsm);

                for (Soldier soldier : MapUI.getController().getGame().getTroops()) {
                    if (soldier.getX() != -1) {
                        soldier.setHasPut(true);
                    }
                }
               // System.out.println("streamer sent"+AttackMapUI.getController().getGame());
                oo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            byte[] buf = bStream.toByteArray();
            DatagramPacket gamePacket = new DatagramPacket(buf, buf.length, AttackMapUI.attackedIP, 12346);
            try {
                AttackMapUI.udpSocket.send(gamePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
