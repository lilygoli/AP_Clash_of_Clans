package com.company.Models.Defences;

import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;

public class Cannon extends Defence {
    public Cannon(int number,int level) {
        super(number, level);
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        ArrayList<Integer> validManhattanDistance = new ArrayList<>();
        validManhattanDistance.add(0);
        validManhattanDistance.add(1);
        Soldier target = findNearestEnemyInRange(enemySoldiers, false, true);
        if (target != null) {
            for (Soldier enemySoldier : enemySoldiers) {
                if (enemySoldier.getCanFly()) {
                    continue;
                }
                Integer manhatanDistance =(int)Math.abs(enemySoldier.getX() - target.getX()) + (int)Math.abs(enemySoldier.getY() - target.getY());
                if (validManhattanDistance.contains(manhatanDistance)) {
                    enemySoldier.setHealth(enemySoldier.getHealth() - this.getDamage());
                }
            }
        }
        return target;
    }
}
