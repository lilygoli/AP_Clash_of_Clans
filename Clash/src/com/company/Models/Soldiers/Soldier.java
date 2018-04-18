package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Cell;
import com.company.Models.Defences.Defence;
import com.company.Models.Defences.Wall;
import com.company.Models.Village;

import java.util.ArrayList;

public abstract class Soldier {

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

    private final double MOVE_PER_TURN = 1 / maxSpeed;

    public static ArrayList<Soldier> getSoldierSubClasses() {
        return soldierSubClasses;
    }

    public int getCost() {
        return cost;
    }

    public int getBuildDuration() {
        return buildDuration;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getRadius() {
        return radius;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getLevel() {
        return level;
    }

    public int getAddedHealth() {
        return addedHealth;
    }

    public int getAddedDamage() {
        return addedDamage;
    }

    public int getUnlockLevel() {
        return unlockLevel;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getCanFly() {
        return canFly;
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
    }


    public void attackTarget(Cell target, Village enemyVillage , String favoriteTarget) {
        if (hasReachedDestination(enemyVillage , favoriteTarget)) {
            target.setStrength(target.getStrength() - damage);
            if (target.getStrength() <= 0) {
                target.setRuined(true);
            }
        } else {
            direction = findDirection(enemyVillage, findDestination(enemyVillage , favoriteTarget));
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

    public Cell findDestination(Village enemyVillage, String favoriteTarget) {
        if (favoriteTarget.equals("Defence")) {
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
                return findDestination(enemyVillage, "all");
            }
            return destination;
        } else if (favoriteTarget.equals("Storage")) {
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
                return findDestination(enemyVillage, "all");
            }
            return destination;
        } else if (favoriteTarget.equals("Wall")) {
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
                return findDestination(enemyVillage, "all");
            }
            return destination;
        }
        // TODO: 4/18/2018 add helaer
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

    public boolean hasReachedDestination(Village enemyVillage , String favoriteTarget) {
        if (Math.sqrt(Math.pow(x - findDestination(enemyVillage , favoriteTarget).getX(), 2) + Math.pow(y - findDestination(enemyVillage , favoriteTarget).getY(), 2)) <= radius) {
            return true;
        }
        return false;
    }


}
