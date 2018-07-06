package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Soldiers.Soldier;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.company.UIs.MapUI.getImageOfBuildings;

public class Trap extends Defence{
    public Trap(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
    }

    @Override
    public Soldier findAndShootUnit(ArrayList<Soldier> enemySoldiers){
        synchronized (enemySoldiers) {
            if (this.getUnderConstructionStatus()) {
                return null;
            }
            Soldier target = findNearestEnemyInRange(enemySoldiers, true, true);
            if (target != null) {
                this.setImage(getImageOfBuildings(this.getClass().getSimpleName(),".gif" , true));
                new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(this.getImageView().imageProperty(), null))).play();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                target.setHealth(target.getHealth() - this.getDamage());
                target.getLeftHealth().setWidth(1.0*(Screen.getPrimary().getVisualBounds().getHeight() / 32)*target.getHealth()/ Config.getDictionary().get(target.getClass().getSimpleName() + "_HEALTH"));
                if (target.getHealth() <= 0) {
                    enemySoldiers.remove(target);
                    target.getImageView().setImage(null);
                }
                this.setRuined(true);
                this.getImageView().setImage(null);
                this.setStrength(0);
                this.setImage(null);
            }
            else{
                this.setImage(getImageOfBuildings(this.getClass().getSimpleName(),".png" ,true));
            }
            return target;
        }
    }
}
