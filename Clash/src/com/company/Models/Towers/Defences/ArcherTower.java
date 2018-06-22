package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;
import com.company.UIs.AttackMapUI;
import javafx.stage.Screen;

import java.util.ArrayList;

import static com.company.UIs.MapUI.getImageOfBuildings;

public class ArcherTower extends Defence {
    public ArcherTower(int number,int level) {
        super(number, level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        synchronized (enemySoldiers) {
            if (this.getUnderConstructionStatus()) {
                return null;
            }
            Soldier target = findNearestEnemyInRange(enemySoldiers, false, true);
            if (target != null) {
                this.setImage(getImageOfBuildings(this.getClass().getSimpleName(),".gif" , true));
                target.setHealth(target.getHealth() - this.getDamage());
                target.getLeftHealth().setWidth(1.0*(Screen.getPrimary().getVisualBounds().getHeight() / 32)*target.getHealth()/ Config.getDictionary().get(target.getClass().getSimpleName() + "_HEALTH"));
                if (target.getHealth() <= 0) {
                    enemySoldiers.remove(target);
                    target.getImageView().setImage(null);
                }
            }
            else{
                this.setImage(getImageOfBuildings(this.getClass().getSimpleName(),".png" ,true));
            }
            return target;
        }
    }
}
