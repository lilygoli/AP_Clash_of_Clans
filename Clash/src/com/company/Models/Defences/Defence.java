package com.company.Models.Defences;
import com.company.Models.Cell;
import com.company.Models.Config;
import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.View;

import java.util.ArrayList;

public abstract class Defence extends Cell {
    private static final String menuOptions = "1. Info\n2. Target\n3. Back";
    private static final String infoMenuOptions = "1. Overall info\n2. Upgrade info\n3. Attack info\n4. Back";
    private int number;

    public Defence(int number, int level) {
        super.setLevel(level);
        this.number = number;
    }

    public void showMenu() {
        Game.setWhereIam("You are in"+this.getClass().getSimpleName() +"enemy map menu");
        View.show(menuOptions);
    }

    public void showInfoMenu() {
        Game.setWhereIam("You are in"+this.getClass().getSimpleName() +"enemy map menu");
        View.show(infoMenuOptions);
    }

    public abstract Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers);

    public Soldier findNearestEnemyInRange(ArrayList<Soldier> enemySoldiers, boolean canShootFlyingSoldiers, boolean canShootGroundSoldiers) {
        double minDistance = -1;
        Soldier target = null;
        for(Soldier soldier : enemySoldiers) {
            if (soldier.getCanFly()) {
                if (!canShootFlyingSoldiers) {
                    continue;
                }
            }
            else {
                if (!canShootGroundSoldiers) {
                    continue;
                }
            }
            double deltaX = (soldier.getX() - this.getX()) * (soldier.getX() - this.getX());
            double deltaY = (soldier.getY() - this.getY()) * (soldier.getY() - this.getY());
            double distance = Math.sqrt(deltaX * deltaX - deltaY * deltaY);
            if (distance < minDistance && distance < this.getRadius()) {
                target = soldier;
                minDistance = distance;
            }
        }
        return target;
    }

    public int getJsonType() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_JSON_TYPE");
    }

    public int getCost() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_COST");

    }

    public int getBuildDuration() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION");
    }

    public int getDamage() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_DAMAGE");
    }

    public int getRadius() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_RADIUS");
    }

    public int getPointsGainedWhenDestructed() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_POINTS_GAINED_WHEN_DESTRUCTED");
    }

    public int getResourceGainedWhenDestructed() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_RESOURCE_GAINED_WHEN_DESTRUCTED");
    }
}
