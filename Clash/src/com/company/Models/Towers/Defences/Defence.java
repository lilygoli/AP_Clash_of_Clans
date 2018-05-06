package com.company.Models.Towers.Defences;
import com.company.Models.Towers.Cell;
import com.company.Models.Config;
import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.View.View;

import java.util.ArrayList;

public abstract class Defence extends Cell {
    private static final String menuOptions = "1. Info\n2. Target\n3. Back";
    private static final String infoMenuOptions = "1. Overall info\n2. Upgrade info\n3. Attack info\n4. Upgrade\n5. Back";
    private int strength;
    private int damage;

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public Defence(int number, int level) {
        super(number,level);
    }


    @Override
    public void upgrade() {
        this.setLevel(this.getLevel() + 1);
        this.strength += 10;
        this.damage += 1;
    }

    public void showMenu() {
        Game.setWhereIAm("You are in"+this.getClass().getSimpleName() +"enemy map menu");
        View.show(menuOptions);
    }

    public void showInfoMenu() {
        Game.setWhereIAm("You are in"+this.getClass().getSimpleName() +"enemy map menu");
        View.show(infoMenuOptions);
    }

    public abstract Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers); // TODO: 4/23/18 enemy soldier should die sometimes:)

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

    public int getBuildDuration() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION");
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;

    }


    public int getRadius() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_RADIUS");
    }


}
