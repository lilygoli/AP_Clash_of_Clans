package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.Iterator;

import static com.company.UIs.MapUI.getImageOfBuildings;

public class GuardianGiant extends Defence {
    public GuardianGiant(int number, int level) {
        super(number, level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName() + "_STRENGTH"));
    }

    @Override
    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers) {
        Iterator<Soldier> iterator = enemySoldiers.iterator();
        while (iterator.hasNext()) {
            Soldier enemySoldier = iterator.next();
            if (enemySoldier.getCanFly()) {
                continue;
            }
            int distance = (int) Math.abs(enemySoldier.getX() - this.getX()) + (int) Math.abs(enemySoldier.getY() - this.getY());
            if (distance <= 2){
                this.setImage(getImageOfBuildings(this.getClass().getSimpleName(),".gif" , true));
                enemySoldier.setHealth(enemySoldier.getHealth() - this.getDamage());
                enemySoldier.getLeftHealth().setWidth(1.0 * (Screen.getPrimary().getVisualBounds().getHeight() / 32) * enemySoldier.getHealth() / Config.getDictionary().get(enemySoldier.getClass().getSimpleName() + "_HEALTH"));
                if (enemySoldier.getHealth() <= 0) {
                    iterator.remove();
                    enemySoldier.getImageView().setImage(null);
                }
            }
        }
        this.setImage(getImageOfBuildings(this.getClass().getSimpleName(),".jpg" , true));
        return null;
    }
}
