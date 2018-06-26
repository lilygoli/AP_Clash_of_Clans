package com.company.Models.Soldiers;

import com.company.Enums.Direction;
import com.company.Models.Config;
import com.company.Models.Resource;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.Storage;
import com.company.Models.Towers.Cell;
import com.company.Models.Village;
import com.company.UIs.AttackMapUI;
import com.company.UIs.MapUI;
import com.company.UIs.UIConstants;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class Soldier {
    private static ArrayList<Soldier> soldierSubClasses = new ArrayList<Soldier>();
    private int buildDuration;
    private int health;
    private int damage;
    private int level;
    private double x = -1, y = -1;
    private boolean dead;
    private Direction direction;
    private transient ImageView imageView = new ImageView();
    private transient Rectangle leftHealth;
    private transient Rectangle allHealth;

    private final double MOVE_PER_TURN = 1.0 * getMaxSpeed() / ((UIConstants.DELTA_T+0.01)*0.002*Config.getDictionary().get("KMM"));

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
        buildDuration = Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION");
        leftHealth =new Rectangle((1.0*Screen.getPrimary().getVisualBounds().getHeight() / 32)*health/Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH"),2);
        leftHealth.setFill(Color.rgb(6,87,51));
        allHealth =new Rectangle(5,2);
        allHealth.setFill(Color.rgb(159,16,55));
    }

    public Soldier(int time) {
        setBuildDuration(Config.getDictionary().get(this.getClass().getSimpleName() + "_BUILD_DURATION") - time);
        // TODO: 4/26/2018 check
    }

    public Soldier() {
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
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

    public int getDamage() {
        return (int)Math.ceil(1.0 * damage / Config.getDictionary().get("KMM"));
    }

    private int getRadius() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_RADIUS");
    }

    public int getMaxSpeed() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_MAXSPEED");
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

    public Direction getDirection() {
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
        this.imageView.relocate(MapUI.mapCoordinates2PixelX(this.x), MapUI.mapCoordinates2PixelY(this.y));
        this.leftHealth.relocate(MapUI.mapCoordinates2PixelX(this.x), MapUI.mapCoordinates2PixelY(this.y)-5);
        this.allHealth.relocate(MapUI.mapCoordinates2PixelX(this.x), MapUI.mapCoordinates2PixelY(this.y)-5);

    }

    public void setY(double y) {
        this.y = y;
        this.imageView.relocate(MapUI.mapCoordinates2PixelX(this.x), MapUI.mapCoordinates2PixelY(this.y));
        this.leftHealth.relocate(MapUI.mapCoordinates2PixelX(this.x), MapUI.mapCoordinates2PixelY(this.y)-5);
        this.allHealth.relocate(MapUI.mapCoordinates2PixelX(this.x), MapUI.mapCoordinates2PixelY(this.y)-5);

    }


    private void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void upgrade() {
        setDamage(getAddedDamage() + getDamage());
        setHealth(getHealth() + getAddedHealth());
        setLevel(getLevel() + 1);
    }

    public void heal() {
        setHealth(Config.getDictionary().get(this.getClass().getSimpleName() + "_HEALTH") + (getLevel() * getAddedHealth()));
    }


    void attackTargets(Village attackerVillage, Village enemyVillage, Cell target) {
        if (hasReachedDestination(target)) {
            String relativeDirection = getRelativeDirection(target);
            chooseRelativeDirection(relativeDirection);
            target.setStrength(target.getStrength() - getDamage());
            if (target.getStrength() <= 0) {
                destroyAndLoot(attackerVillage, target);
            }
        } else {
            setDirection(findDirection(enemyVillage, target));
            moveSoldier(attackerVillage, getDirection(), enemyVillage);
            switch (getDirection()) {
                // TODO: 6/14/2018 left and right here is wisewersa
                case UP:
                    imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveUp"));
                    break;
                case DOWN:
                    imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveDown"));
                    break;
                case RIGHT:
                    imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveLeft"));
                    break;
                case LEFT:
                    imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveRight"));
                    break;
            }
        }
    }

    private String getRelativeDirection(Cell target) {
        if (target.getX() - getX() >= Math.abs(getY() - target.getY())) {
            return "Left";
        } else if (target.getY() - getY() >= Math.abs(target.getX() - getX())) {
            return "Down";
        } else if (getX() - target.getX() >= Math.abs(getY() - target.getY())) {
            return "Right";
        } else if (getY() - target.getY() >= Math.abs(target.getX() - getX())) {
            return "Up";
        }
        return "";
    }

    private void moveSoldier(Village attackerVillage, Direction direction, Village enemyVillage) {
        // TODO: 4/24/2018 check double int
        if (direction == Direction.LEFT) {
            if (enemyVillage.getMap()[(int) (x - 1)][(int) y].getClass().equals(Grass.class) || enemyVillage.getMap()[(int) (x - 1)][(int) y].isRuined() || getCanFly()) {
                setX(getX() - MOVE_PER_TURN);
            } else {
                Cell target = enemyVillage.getMap()[(int) (x - 1)][(int) y];
                target.setStrength(target.getStrength() - getDamage());
                String relativeDirection = getRelativeDirection(target);
                chooseRelativeDirection(relativeDirection);
                if (target.getStrength() <= 0) {
                    destroyAndLoot(attackerVillage, target);
                }
            }

        } else if (direction == Direction.RIGHT) {
            if (enemyVillage.getMap()[(int) (x + 1)][(int) y].getClass().equals(Grass.class) || enemyVillage.getMap()[(int) (x + 1)][(int) y].isRuined() || getCanFly()) {
                setX(getX() + MOVE_PER_TURN);
            } else {
                Cell target = enemyVillage.getMap()[(int) (x + 1)][(int) y];
                target.setStrength(target.getStrength() - getDamage());
                String relativeDirection = getRelativeDirection(target);
                chooseRelativeDirection(relativeDirection);
                if (target.getStrength() <= 0) {
                    destroyAndLoot(attackerVillage, target);
                }
            }
        } else if (direction == Direction.DOWN) {
            if (enemyVillage.getMap()[(int) x][(int) (y + 1)].getClass().equals(Grass.class) || enemyVillage.getMap()[(int) x][(int) (y + 1)].isRuined() || getCanFly()) {
                setY(getY() + MOVE_PER_TURN);
            } else {
                Cell target = enemyVillage.getMap()[(int) x][(int) (y + 1)];
                target.setStrength(target.getStrength() - getDamage());
                String relativeDirection = getRelativeDirection(target);
                chooseRelativeDirection(relativeDirection);
                if (target.getStrength() <= 0) {
                    destroyAndLoot(attackerVillage, target);
                }
            }
        } else if (direction == Direction.UP)

            if (enemyVillage.getMap()[(int) x][(int) (y - 1)].getClass().equals(Grass.class) || enemyVillage.getMap()[(int) x][(int) (y - 1)].isRuined() || getCanFly()) {
                setY(getY() - MOVE_PER_TURN);
            } else {
                Cell target = enemyVillage.getMap()[(int) x][(int) (y - 1)];
                target.setStrength(target.getStrength() - getDamage());
                String relativeDirection = getRelativeDirection(target);
                chooseRelativeDirection(relativeDirection);
                if (target.getStrength() <= 0) {
                    destroyAndLoot(attackerVillage, target);
                }
            }
    }

    private void chooseRelativeDirection(String relativeDirection) {
        switch (relativeDirection) {
            case "Up":
                imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "AttackUp"));
                break;
            case "Down":
                imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "AttackDown"));
                break;
            case "Left":
                imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "AttackLeft"));
                break;
            case "Right":
                imageView.setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "AttackRight"));
                break;
        }
    }

    private void destroyAndLoot(Village attackerVillage, Cell target) {
        target.setRuined(true);
        target.getImageView().setImage(null);
        target.setStrength(0);
        target.setImage(null);
        attackerVillage.setScore(attackerVillage.getScore() + target.getPointsGainedWhenDestructed());
        Resource gainedResource;
        switch (target.getClass().getSimpleName()) {
            case "GoldStorage": {
                Storage storage = (Storage) target;
                gainedResource = new Resource(target.getGoldGainedWhenDestructed() + storage.getResource(), target.getElixirGainedWhenDestructed());
                break;
            }
            case "ElixirStorage": {
                Storage storage = (Storage) target;
                gainedResource = new Resource(target.getGoldGainedWhenDestructed(), target.getElixirGainedWhenDestructed() + storage.getResource());
                break;
            }
            default:
                gainedResource = new Resource(target.getGoldGainedWhenDestructed(), target.getElixirGainedWhenDestructed());
                break;
        }
        attackerVillage.getGainedResource().setGold(gainedResource.getGold() + attackerVillage.getGainedResource().getGold());
        attackerVillage.getGainedResource().setElixir(gainedResource.getElixir() + attackerVillage.getGainedResource().getElixir());
    }

    // TODO: 4/18/2018 add healer
    public Cell findDestination(Village enemyVillage, ArrayList<String> validDestinations) {
        boolean finishedFavoriteTrget = true;
        Cell destination = new Cell(0, 0);
        double minDistance = 100d;
        //int flag = 0;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (!enemyVillage.getMap()[i][j].isRuined()) {
                    if (validDestinations.contains(enemyVillage.getMap()[i][j].getClass().getSimpleName())) {
                        finishedFavoriteTrget = false;
                        if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) <= minDistance) {
                            destination = enemyVillage.getMap()[i][j];
                            minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                            //flag = 1;
                        }
                    }
                }
            }
        }
//        if (validDestinations.contains("Camp")) {
//            if (flag == 0) {
//                return findDestination(enemyVillage, getAllValidDestinations());
//            }
//        }
        // TODO: 6/21/2018 check when all buildings destroyed what should happen?
        if (finishedFavoriteTrget && !validDestinations.contains("Camp")) {
            return findDestination(enemyVillage , getAllValidDestinations());
        }
        return destination;
    }

    public ArrayList<String> getAllValidDestinations() {
        ArrayList<String> allTowers = new ArrayList<>();
        for (Cell cell : Cell.getCellKinds()) {
            allTowers.add(cell.getClass().getSimpleName());
        }
        return allTowers;
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
                adjacent[2][1] = y + 1;
            }
            adjacent[3][1] = y;
            if (x - 1 >= 0) {
                adjacent[3][0] = x - 1;
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
        return Math.sqrt(Math.pow(x - (double) target.getX(), 2.0) + Math.pow(y - (double) target.getY(), 2.0)) <= (double) getRadius();
    }


    public abstract void attackTarget(Village attackerVillage, Village enemyVillage);

    public Rectangle getLeftHealth() {
        return leftHealth;
    }

    public void setLeftHealth(Rectangle leftHealth) {
        this.leftHealth = leftHealth;
    }

    public Rectangle getAllHealth() {
        return allHealth;
    }

    public void setAllHealth(Rectangle allHealth) {
        this.allHealth = allHealth;
    }
}
