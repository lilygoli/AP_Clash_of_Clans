package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;

import java.util.ArrayList;
import java.util.Iterator;

public class Cannon extends Defence {
    public Cannon(int number,int level) {
        super(number, level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers)  {
        synchronized (enemySoldiers) {
            if (this.getUnderConstructionStatus()) {
                return null;
            }
            ArrayList<Integer> validManhattanDistance = new ArrayList<>();
            validManhattanDistance.add(0);
            validManhattanDistance.add(1);
            Soldier target = findNearestEnemyInRange(enemySoldiers, false, true);
            if (target != null) {
                Iterator<Soldier> i = enemySoldiers.iterator();
                while (i.hasNext()) {
                    Soldier enemySoldier = i.next();
                    if (enemySoldier.getCanFly()) {
                        continue;
                    }
                    Integer manhattanDistance = (int) Math.abs(enemySoldier.getX() - target.getX()) + (int) Math.abs(enemySoldier.getY() - target.getY());
                    if (validManhattanDistance.contains(manhattanDistance)) {
                        enemySoldier.setHealth(enemySoldier.getHealth() - this.getDamage());
                        if (enemySoldier.getHealth() <= 0) {
                            i.remove();
                            enemySoldier.getImageView().setImage(null);
                        }
                    }
                }
            }
            return target;
        }
    }

}
