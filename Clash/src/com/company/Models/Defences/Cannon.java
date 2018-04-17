package com.company.Models.Defences;

import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class Cannon extends Defence {
    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        ArrayList<Integer> validManhatanDistance = new ArrayList<>();
        validManhatanDistance.add(0);
        validManhatanDistance.add(1);
        Soldier target = findNearestEnemyInRange(enemySoldiers, false, true);
        if (target != null) {
            for (Soldier enemySoldier : enemySoldiers) {
                Integer manhatanDistance = Math.abs(enemySoldier.getX() _ target.getX()) + Math.abs(enemySoldier.getY() - target.getY());
                if (validManhatanDistance.contains(manhatanDistance)) {
                    enemySoldier.setHealth(enemySoldier.getHealth() - this.getDamage());
                }
            }
        }
        return target;
    }
}
