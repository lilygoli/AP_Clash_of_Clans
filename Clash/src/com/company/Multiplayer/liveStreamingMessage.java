package com.company.Multiplayer;

import com.company.Models.Soldiers.Soldier;
import com.company.UIs.MapUI;

import java.io.Serializable;
import java.util.ArrayList;

public class liveStreamingMessage implements Serializable {
    static ArrayList<Soldier> troops;
    ArrayList<Integer> healths = new ArrayList<>();

    public ArrayList<Integer> getHealths() {
        return healths;
    }

    public static ArrayList<Soldier> getTroops() {
        return troops;
    }

    public void setTroops(ArrayList<Soldier> troops) {
        this.troops = troops;
    }

    public void setHealths(ArrayList<Integer> healths) {
        this.healths = healths;
    }
}
