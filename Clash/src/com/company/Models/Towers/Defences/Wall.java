package com.company.Models.Towers.Defences;

import com.company.Models.Config;
import com.company.Models.Towers.Cell;
import javafx.scene.image.ImageView;


public class Wall extends Cell{
    public Wall(int number,int level) {
        super(number,level);
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
    }
}
