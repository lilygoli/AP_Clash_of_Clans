package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Buildings.*;
import com.company.Models.Cell;
import com.company.Models.Config;
import com.company.Models.Defences.*;
import com.company.Models.*;

import java.util.ArrayList;

public abstract class Soldier {
    // TODO: 4/21/2018 add heal func
    static ArrayList<Soldier> soldierSubClasses;
    private int cost;
    private int buildDuration;
    private int health;
    private int damage;
    private int radius;
    private int maxSpeed;
    private int level;
    private int addedHealth;
    private int addedDamage;
    private int unlockLevel;
    private double x, y;
    private boolean canFly;
    private boolean dead;
    private Direction direction;

    // TODO: 4/21/2018 read health from config in constructor

    private final double MOVE_PER_TURN = 1 / maxSpeed;

    public static ArrayList<Soldier> getSoldierSubClasses() {
        return soldierSubClasses;
    }

    public int getCost() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "ELEXIR_COST");
    }

    public int getBuildDuration() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION");
    }

    public int getHealth() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH");
    }

    public int getDamage() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_DAMAGE");
    }

    public int getRadius() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_RADIUS");
    }

    public int getMaxSpeed() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_MAX_SPEED");
    }

    public int getLevel() {
        return level;
    }

    public int getAddedHealth() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ADDED_HEALTH");
    }

    public int getAddedDamage() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ADDED_DAMAGE");
    }

    public int getUnlockLevel() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_UNLOCK_LEVEL");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getCanFly() {
        if (Config.getDictionary().get(this.getClass().getSimpleName() + "_CAN_FLY") == 0)
            return false;
        return true;
    }

    public boolean isDead() {
        return dead;
    }

    public Direction getDirection() {
        return direction;
    }

    public static void setSoldierSubClasses(ArrayList<Soldier> soldierSubClasses) {
        Soldier.soldierSubClasses = soldierSubClasses;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setBuildDuration(int buildDuration) {
        this.buildDuration = buildDuration;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setAddedHealth(int addedHealth) {
        this.addedHealth = addedHealth;
    }

    public void setAddedDamage(int addedDamage) {
        this.addedDamage = addedDamage;
    }

    public void setUnlockLevel(int unlockLevel) {
        this.unlockLevel = unlockLevel;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setDirecton(Direction directon) {
        this.direction = directon;
    }

    public void upgrade() {
        damage += addedDamage;
        health += addedHealth;
        level++;
    }


    public void attackTarget(Village enemyVillage , String favoriteTarget) {
        Cell target = new Cell();
        if (favoriteTarget.equals("Storage")) {
            target = findDestinationForGiant(enemyVillage);
        }
        else if (favoriteTarget.equals("Defence")) {
            target = findDestinationForArcher(enemyVillage);
        }
        else if (favoriteTarget.equals("Wall")) {
            target = findDestinationForWallBreaker(enemyVillage);
        }
        else{
            target = findDestinationForAll(enemyVillage);
        }

        if (hasReachedDestination(target)) {
            target.setStrength(target.getStrength() - getDamage());
            if (target.getStrength() <= 0) {
                target.setRuined(true);
            }
        } else {
            direction = findDirection(enemyVillage, target);
            moveSoldier(direction);
        }
    }

    public void moveSoldier(Direction directon) {
        if (directon == Direction.LEFT) {
            x = x - MOVE_PER_TURN;
        } else if (directon == Direction.RIGHT) {
            x = x + MOVE_PER_TURN;
        } else if (directon == Direction.UP) {
            y = y + MOVE_PER_TURN;
        } else if (directon == Direction.DOWN) {
            y = y - MOVE_PER_TURN;
        }
    }

    public Cell findDestinationForArcher(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100d;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                // TODO: 4/18/2018 add wall and trap
                if (enemyVillage.getMap()[i][j].getClass().isInstance(Defence.class)) {
                    if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                        destination = enemyVillage.getMap()[i][j];
                        minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                    }
                }
            }
        }
        if (Math.abs(minDistance - 100) < 0.01) {
            return findDestinationForAll(enemyVillage);
        }
        return destination;
    }

    public Cell findDestinationForGiant(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100d;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (enemyVillage.getMap()[i][j].getClass().isInstance(Storage.class) || enemyVillage.getMap()[i][j].getClass().isInstance(Mine.class)) {
                    if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                        destination = enemyVillage.getMap()[i][j];
                        minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                    }
                }
            }
        }
        if (Math.abs(minDistance - 100) < 0.01) {
            return findDestinationForAll(enemyVillage);
        }
        return destination;
    }

    public Cell findDestinationForWallBreaker(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100d;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (enemyVillage.getMap()[i][j].getClass().isInstance(Wall.class)) {
                    if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                        destination = enemyVillage.getMap()[i][j];
                        minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                    }
                }
            }
        }
        if (Math.abs(minDistance - 100) < 0.01) {
            return findDestinationForAll(enemyVillage);
        }
        return destination;
    }
    // TODO: 4/18/2018 add helaer
    public Cell findDestinationForAll(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100d;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                    destination = enemyVillage.getMap()[i][j];
                    minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                }
            }
        }
        return destination;
    }

    public Direction findDirection(Village enemyVillage, Cell destination) {
        // TODO: 4/18/2018 make it complete
        return Direction.UP;
    }

    public boolean hasReachedDestination(Cell target) {
        if (Math.sqrt(Math.pow(x - target.getX(), 2) + Math.pow(y - target.getY(), 2)) <= radius) {
            return true;
        }
        return false;
    }


}
