package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Buildings.*;
import com.company.Models.Cell;
import com.company.Models.Config;
import com.company.Models.Defences.*;
import com.company.Models.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Soldier {
    private static ArrayList<Soldier> soldierSubClasses;
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

    static {
        soldierSubClasses.add(new Archer());
        soldierSubClasses.add(new Dragon());
        soldierSubClasses.add(new Giant());
        soldierSubClasses.add(new Guardian());
        soldierSubClasses.add(new Healer());
        soldierSubClasses.add(new WallBreaker());
    }

    {
        health = Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH");
        damage = Config.getDictionary().get(this.getClass().getSimpleName() + "_DAMAGE");
    }

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
        return health;
    }

    public int getDamage() {
        return damage;
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

    public void heal() {
        health = Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH") + (level * addedHealth);
    }


    public void attackTarget(Village enemyVillage, String favoriteTarget) {
        Cell target;
        if (favoriteTarget.equals("Storage")) {
            target = findDestinationForGiant(enemyVillage);
        } else if (favoriteTarget.equals("Defence")) {
            target = findDestinationForArcher(enemyVillage);
        } else if (favoriteTarget.equals("Wall")) {
            target = findDestinationForWallBreaker(enemyVillage);
        } else {
            target = findDestinationForAll(enemyVillage);
        }

        if (hasReachedDestination(target)) {
            target.setStrength(target.getStrength() - getDamage());
            if (target.getStrength() <= 0) {
                target.setRuined(true);
                target.setStrength(0);
            }
        } else {
            direction = findDirection(enemyVillage, target);
            moveSoldier(direction);
        }
    }

    public void moveSoldier(Direction direction) {
        if (direction == Direction.LEFT) {
            x = x - MOVE_PER_TURN;
        } else if (direction == Direction.RIGHT) {
            x = x + MOVE_PER_TURN;
        } else if (direction == Direction.UP) {
            y = y + MOVE_PER_TURN;
        } else if (direction == Direction.DOWN) {
            y = y - MOVE_PER_TURN;
        }
    }

    public Cell findDestinationForArcher(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100d;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (!enemyVillage.getMap()[i][j].isRuined()) {
                    if (enemyVillage.getMap()[i][j].getClass().isInstance(Defence.class) && !enemyVillage.getMap()[i][j].getClass().isInstance(Wall.class) && !enemyVillage.getMap()[i][j].getClass().isInstance(Trap.class)) {
                        if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                            destination = enemyVillage.getMap()[i][j];
                            minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                        }
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
                if (!enemyVillage.getMap()[i][j].isRuined()) {
                    if (enemyVillage.getMap()[i][j].getClass().isInstance(Storage.class) || enemyVillage.getMap()[i][j].getClass().isInstance(Mine.class)) {
                        if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                            destination = enemyVillage.getMap()[i][j];
                            minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                        }
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
                if (!enemyVillage.getMap()[i][j].isRuined()) {
                    if (enemyVillage.getMap()[i][j].getClass().isInstance(Wall.class)) {
                        if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                            destination = enemyVillage.getMap()[i][j];
                            minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                        }
                    }
                }
            }
        }
        if (Math.abs(minDistance - 100) < 0.01) {
            return findDestinationForAll(enemyVillage);
        }
        return destination;
    }

    // TODO: 4/18/2018 add healer
    public Cell findDestinationForAll(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100d;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (!enemyVillage.getMap()[i][j].isRuined()) {
                    if (!enemyVillage.getMap()[i][j].getClass().isInstance(Grass.class) && !enemyVillage.getMap()[i][j].getClass().isInstance(Trap.class)) {
                        if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                            destination = enemyVillage.getMap()[i][j];
                            minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                        }
                    }
                }
            }
        }
        return destination;
    }

    public Direction findDirection(Village enemyVillage, Cell destination) {
        // TODO: 4/18/2018 make it complete
        int infinity = Integer.MAX_VALUE;
        // TODO: 4/23/2018 add POSITION Class
        LinkedList<Integer> queueX = new LinkedList<>();
        LinkedList<Integer> queueY = new LinkedList<>();
        int[][] distance = new int[30][30];
        Direction[][] lastDir = new Direction[30][30];
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                distance[i][j] = infinity;
            }
        }
        queueX.add(destination.getX());
        queueY.add(destination.getY());
        lastDir[destination.getX()][destination.getY()] = Direction.NONE;
        distance[destination.getX()][destination.getY()] = 0;
        while (!queueX.isEmpty()) {
            int x = queueX.getFirst(), y = queueY.getFirst();
            Integer[][] adjacent = new Integer[4][2];
            adjacent[0][0] = x;
            if (y - 1 >= 0) {
                adjacent[0][1] = y - 1;
            }
            adjacent[1][1] = y;
            if (x + 1 < 30) {
                adjacent[1][0] = x + 1;
            }
            adjacent[2][0] = x;
            if (y + 1 < 30) {
                adjacent[2][1] = y;
            }
            adjacent[3][1] = y;
            if (x - 1 >= 0) {
                adjacent[3][0] = x;
            }
            for (int i = 0; i < 4; i++) {
                // TODO: 4/23/2018 refactor
                if (!adjacent[i][0].equals(null) && !adjacent[i][1].equals(null) && distance[x][y] + 1 + (int) (enemyVillage.getMap()[adjacent[i][0]][adjacent[i][1]].getStrength() / damage) < distance[adjacent[i][0]][adjacent[i][1]]) {
                    distance[adjacent[i][0]][adjacent[i][1]] = distance[x][y] + 1 + (int) (enemyVillage.getMap()[adjacent[i][0]][adjacent[i][1]].getStrength() / damage);
                    switch (i) {
                        case 0:
                            lastDir[adjacent[i][0]][adjacent[i][1]] = Direction.DOWN;
                            break;
                        case 1:
                            lastDir[adjacent[i][0]][adjacent[i][1]] = Direction.LEFT;
                            break;
                        case 2:
                            lastDir[adjacent[i][0]][adjacent[i][1]] = Direction.UP;
                            break;
                        case 3:
                            lastDir[adjacent[i][0]][adjacent[i][1]] = Direction.RIGHT;
                            break;
                    }
                    queueX.add(adjacent[i][0]);
                    queueY.add(adjacent[i][1]);

                }
            }
            queueX.removeFirst();
            queueY.removeFirst();
        }
        // TODO: 4/23/2018 check double and int 
        return lastDir[(int)getX()][(int)getY()];
    }

    public boolean hasReachedDestination(Cell target) {
        if (Math.sqrt(Math.pow(x - target.getX(), 2) + Math.pow(y - target.getY(), 2)) <= radius) {
            return true;
        }
        return false;
    }


}
