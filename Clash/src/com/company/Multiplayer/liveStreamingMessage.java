package com.company.Multiplayer;

import com.company.Models.Soldiers.Soldier;
import com.company.UIs.MapUI;

import java.io.Serializable;
import java.util.ArrayList;

public class liveStreamingMessage implements Serializable {
    Soldier soldier;
    ArrayList<Integer> healths = new ArrayList<>();

    public ArrayList<Integer> getHealths() {
        return healths;
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }

    public void setHealths(ArrayList<Integer> healths) {
        this.healths = healths;
    }
}
