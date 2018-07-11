package com.company.Multiplayer;

import com.company.Models.Config;
import com.company.Models.Game;
import com.company.Models.Resource;
import com.company.Models.Soldiers.Archer;
import com.company.Models.Soldiers.Soldier;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.SideBarUI;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

public class UDPReceiver extends Thread {
    private boolean running;
    private byte[] buf = new byte[10000];

    @Override
    public void run() {
        running = true;

        while (running) {
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
                if (liveStreamingMessage1.getSoldier() == null) {
                    continue;
                }

//                if(liveStreamingMessage.troops== null){
//                    liveStreamingMessage.troops= new ArrayList<Soldier>();
//                    Archer archer= new Archer(0);
//                    archer.setX(1);
//                    archer.setY(1);
//                    liveStreamingMessage.troops.add(archer);
//                }
//                ArrayList<Soldier> soldiers = new ArrayList<>();
//                for (Soldier soldier : liveStreamingMessage1.getTroops()) {
//                    if(soldier.isHasPut() ){
//                        continue;
//                    }
//                    soldiers.add(soldier);
//                }
//                if(MapUI.getController().getGame().getTroops() == null) {
//                    MapUI.getController().getGame().setTroops(soldiers);
//                }else {
//                    MapUI.getController().getGame().getTroops().addAll(soldiers);
//                }
//                for (Soldier soldier : MapUI.getController().getGame().getTroops()) {
                Soldier soldier = liveStreamingMessage1.getSoldier();
                System.out.println(liveStreamingMessage1.getSoldier());
                soldier.setImageView(new ImageView());
                Rectangle leftHealth = new Rectangle((1.0 * Screen.getPrimary().getVisualBounds().getHeight() / 32) * soldier.getHealth() / Config.getDictionary().get(soldier.getClass().getSimpleName() + "_HEALTH"), 1);
                leftHealth.setFill(Color.rgb(6, 87, 51));
                soldier.setLeftHealth(leftHealth);
                Rectangle allHealth = new Rectangle(5, 1);
                allHealth.setFill(Color.rgb(159, 15, 55));
                soldier.setAllHealth(allHealth);
//                    AttackMapUI.putSoldiersImageInMap(soldier.getX(),soldier.getY(),32,AttackMapUI.canvas,AttackMapUI.getSoldiersGif().get(soldier.getClass().getSimpleName()+"MoveUp"),soldier,);
//                }
//
                MapUI.getController().getGame().getVillage().setResource(new Resource(10, 10));
                MapUI.getController().getGame().setAttackedVillage(MapUI.getController().getGame());
                MapUI.isIsInDefense(true);

                if (MapUI.getController().getGame().getTroops() == null) {
                    MapUI.getController().getGame().setTroops(new ArrayList<Soldier>());

                }
                MapUI.getController().getGame().getTroops().add(soldier);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}
