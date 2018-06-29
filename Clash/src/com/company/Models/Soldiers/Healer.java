package com.company.Models.Soldiers;

import com.company.Models.Config;
import com.company.Models.Towers.Cell;
import com.company.Models.Village;
import com.company.UIs.AttackMapUI;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.Arrays;

public class Healer extends Soldier {
    private int heal;
    private int healAdded;
    private int timeInWar=0;

    public Healer(int time) {
        super(time);
        heal = Config.getDictionary().get(this.getClass().getSimpleName() + "_HEAL");
    }

    public int getHeal() {
        return heal;
    }

    public int getHealAdded() {
        return Config.getDictionary().get(this.getClass().getSimpleName() + "_ADDED_HEAL");
    }

    public void setTimeInWar(int timeInWar) {
        this.timeInWar = timeInWar;
    }

    public int getTimeInWar() {
        return timeInWar;
    }

    @Override
    public void upgrade() {
        heal += getHealAdded();
    }

    @Override
    public void attackTarget(Village attackerVillage, Village enemyVillage) {
        Soldier targetSoldier = null;
        double minDist = 100d;
        for (Soldier soldier : AttackMapUI.getController().getGame().getTroops()) {
            if (soldier.getX() != -1 && soldier.getY() != -1 && !soldier.getClass().getSimpleName().equals("Healer")) {
                double dist = Math.sqrt(Math.pow(this.getX() - soldier.getX(), 2.0) + Math.pow(this.getX() - soldier.getY(), 2.0));
                if (dist <= minDist) {
                    minDist = dist;
                    targetSoldier = soldier;
                }
            }
        }
        if (targetSoldier != null) {

            Cell target = new Cell( 0 , 0);
            target.setX((int)targetSoldier.getX());
            target.setY((int)targetSoldier.getY());
            setTarget(target);
            if (hasReachedDestination(target)) {
                String relativeDirection = getRelativeDirection(target);
                chooseRelativeDirection(relativeDirection);
                targetSoldier.setHealth(targetSoldier.getHealth() + this.getHeal());
                if (targetSoldier.getHealth() >= Config.getDictionary().get(targetSoldier.getClass().getSimpleName() + "_HEALTH") + getLevel() * Config.getDictionary().get(targetSoldier.getClass().getSimpleName() + "_ADDED_HEALTH")) {
                    targetSoldier.setHealth(Config.getDictionary().get(targetSoldier.getClass().getSimpleName() + "_HEALTH") + getLevel() * Config.getDictionary().get(targetSoldier.getClass().getSimpleName() + "_ADDED_HEALTH"));
                }
                targetSoldier.getLeftHealth().setWidth(1.0 * (Screen.getPrimary().getVisualBounds().getHeight() / 32) * targetSoldier.getHealth() / Config.getDictionary().get(targetSoldier.getClass().getSimpleName() + "_HEALTH"));
            }else{
                setDirection(findDirection(enemyVillage, target));
                moveSoldier(attackerVillage, getDirection(), enemyVillage);
                switch (getDirection()) {
                    case UP:
                        this.getImageView().setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveUp"));
                        break;
                    case DOWN:
                        this.getImageView().setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveDown"));
                        break;
                    case RIGHT:
                        this.getImageView().setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveLeft"));
                        break;
                    case LEFT:
                        this.getImageView().setImage(AttackMapUI.getSoldiersGif().get(this.getClass().getSimpleName() + "MoveRight"));
                        break;
                }
            }
        }
    }
}
