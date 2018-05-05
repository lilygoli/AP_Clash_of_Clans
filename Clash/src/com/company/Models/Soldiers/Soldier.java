package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Towers.Cell;
import com.company.Models.Config;
import com.company.Models.Towers.Defences.*;
import com.company.Models.*;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Soldier {
    private static ArrayList<Soldier> soldierSubClasses=new ArrayList<Soldier>();
    private int buildDuration;
    private int health;
    private int damage;
    private int level;
    private double x = -1, y = -1;
    private boolean dead;
    private Direction direction;

    static {
        soldierSubClasses.add(new Archer(0));
        soldierSubClasses.add(new Dragon(0));
        soldierSubClasses.add(new Giant(0));
        soldierSubClasses.add(new Guardian(0));
        soldierSubClasses.add(new Healer(0));
        soldierSubClasses.add(new WallBreaker(0));
    }

    {
        health = Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH");
        damage = Config.getDictionary().get(this.getClass().getSimpleName() + "_DAMAGE");
        buildDuration=Config.getDictionary().get(this.getClass().getSimpleName()+"_BUILD_DURATION");
    }

    public Soldier(int time){
        setBuildDuration(Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION") - time);
        // TODO: 4/26/2018 check
    }
    public Soldier(){}

    private final double MOVE_PER_TURN = 1;
    // TODO: 4/26/2018 what is move per turn

    public static ArrayList<Soldier> getSoldierSubClasses() {
        return soldierSubClasses;
    }

    public int getCost() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ELEXIR_COST");
    }

    public int getBuildDuration() {
        return buildDuration;
    }

    public int getHealth() {
        return health;
    }

    private int getDamage() {
        return damage;
    }

    private int getRadius() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_RADIUS");
    }

    public int getMaxSpeed() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_MAX_SPEED");
    }

    public int getLevel() {
        return level;
    }

    private int getAddedHealth() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ADDED_HEALTH");
    }

    private int getAddedDamage() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ADDED_DAMAGE");
    }

    public int getUnlockLevel() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_UNLOCKLEVEL");
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean getCanFly() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_CAN_FLY") != 0;
    }

    public boolean isDead() {
        return dead;
    }

    private Direction getDirection() {
        return direction;
    }

    private void setBuildDuration(int buildDuration) {
        this.buildDuration = buildDuration;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    private void setDamage(int damage) {
        this.damage = damage;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    private void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void upgrade() {
        setDamage(getAddedDamage() + getDamage());
        setHealth(getHealth()+ getAddedHealth());
        setLevel(getLevel() + 1);
    }

    public void heal() {
        setHealth(Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH") + (getLevel()* getAddedHealth()));
    }


    void attackTargets(Village attackerVillage, Village enemyVillage, String favoriteTarget) {
        // TODO: 4/23/2018 add resource decrease
        Cell target;
        switch (favoriteTarget) {
            case "Storage":
                target = findDestinationForGiant(enemyVillage);
                break;
            case "Defence":
                target = findDestinationForArcher(enemyVillage);
                break;
            case "Wall":
                target = findDestinationForWallBreaker(enemyVillage);
                break;
            default:
                target = findDestinationForAll(enemyVillage);
                break;
        }

        if (hasReachedDestination(target)) {
            target.setStrength(target.getStrength() - getDamage());
            if (target.getStrength() <= 0) {
                target.setRuined(true);
                target.setStrength(0);
            }
        } else {
            setDirection(findDirection(enemyVillage, target));
            moveSoldier(attackerVillage,getDirection(), enemyVillage);
        }
    }

    private void moveSoldier(Village attackerVillage, Direction direction, Village enemyVillage) {
        // TODO: 4/24/2018 check double int
        if (direction == Direction.LEFT) {
            if (enemyVillage.getMap()[(int) (x - 1)][(int) y].getClass().equals(Grass.class) || enemyVillage.getMap()[(int)(x - 1)][(int)y].isRuined() || getCanFly()) {
                x = x - MOVE_PER_TURN;
            } else {
                Cell target = enemyVillage.getMap()[(int) (x - 1)][(int) y];
                target.setStrength(target.getStrength() - getDamage());
                if (target.getStrength() <= 0) {
                    target.setRuined(true);
                    target.setStrength(0);
                    attackerVillage.setScore(attackerVillage.getScore()+target.getPointsGainedWhenDestructed());
                    Resource resource;
                    switch (target.getClass().getSimpleName()) {
                        case "GoldStorage": {
                            Storage storage = (Storage) target;
                            resource = new Resource(attackerVillage.getResource().getGold() + target.getGoldGainedWhenDestructed() + storage.getResource(), attackerVillage.getResource().getElixir() + target.getElixirGainedWhenDestructed());

                            break;
                        }
                        case "ElixirStorage": {
                            Storage storage = (Storage) target;
                            resource = new Resource(attackerVillage.getResource().getGold() + target.getGoldGainedWhenDestructed(), attackerVillage.getResource().getElixir() + target.getElixirGainedWhenDestructed() + storage.getResource());
                            break;
                        }
                        default:
                            resource = new Resource(attackerVillage.getResource().getGold() + target.getGoldGainedWhenDestructed(), attackerVillage.getResource().getElixir() + target.getElixirGainedWhenDestructed());
                            break;
                    }
                    attackerVillage.setResource(resource);

                }
            }

        } else if (direction == Direction.RIGHT) {
            if (enemyVillage.getMap()[(int) (x + 1)][(int) y].getClass().equals(Grass.class)  || enemyVillage.getMap()[(int)(x + 1)][(int)y].isRuined() || getCanFly()) {
                x = x + MOVE_PER_TURN;
            } else {
                Cell target = enemyVillage.getMap()[(int) (x + 1)][(int) y];
                target.setStrength(target.getStrength() - getDamage());
                if (target.getStrength() <= 0) {
                    target.setRuined(true);
                    target.setStrength(0);
                }
            }
        } else if (direction == Direction.UP) {
            if (enemyVillage.getMap()[(int) x][(int) (y + 1)].getClass().equals(Grass.class) || enemyVillage.getMap()[(int) x][(int) (y + 1)].isRuined() || getCanFly()) {
                y = y + MOVE_PER_TURN;
            } else {
                Cell target = enemyVillage.getMap()[(int) x][(int) (y + 1)];
                target.setStrength(target.getStrength() - getDamage());
                if (target.getStrength() <= 0) {
                    target.setRuined(true);
                    target.setStrength(0);
                }
            }
        } else if (direction == Direction.DOWN) {
            if (enemyVillage.getMap()[(int) x][(int) (y - 1)].getClass().equals(Grass.class) || enemyVillage.getMap()[(int) x][(int) (y - 1)].isRuined() || getCanFly()) {
                y = y - MOVE_PER_TURN;
            } else {
                Cell target = enemyVillage.getMap()[(int) x][(int) (y - 1)];
                target.setStrength(target.getStrength() - getDamage());
                if (target.getStrength() <= 0) {
                    target.setRuined(true);
                    target.setStrength(0);
                }
            }
        }
    }

    private Cell findDestinationForArcher(Village enemyVillage) {
        Cell destination = new Cell(0,0);
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

    private Cell findDestinationForGiant(Village enemyVillage) {
        Cell destination = new Cell(0,0);
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

    private Cell findDestinationForWallBreaker(Village enemyVillage) {
        Cell destination = new Cell(0,0);
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
    private Cell findDestinationForAll(Village enemyVillage) {
        Cell destination = new Cell(0,0);
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
        int infinity = Integer.MAX_VALUE;
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
            Integer[][] adjacent = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};
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
                if (adjacent[i][0] != -1 && adjacent[i][1] != -1 && distance[x][y] + 1 + (int) (enemyVillage.getMap()[adjacent[i][0]][adjacent[i][1]].getStrength() / damage) < distance[adjacent[i][0]][adjacent[i][1]]) {
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
        return lastDir[(int) getX()][(int) getY()];
    }

    private boolean hasReachedDestination(Cell target) {
        return Math.sqrt(Math.pow(x - target.getX(), 2) + Math.pow(y - target.getY(), 2)) <= getRadius();
    }


    public abstract void attackTarget(Village attackerVillage,Village enemyVillage);
}
