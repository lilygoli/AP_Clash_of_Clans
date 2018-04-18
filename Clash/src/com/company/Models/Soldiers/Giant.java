package com.company.Models.Soldiers;

import com.company.Models.Cell;
import com.company.Models.Defences.Wall;
import com.company.Models.Village;

public class Giant extends Soldier {
    @Override
    public Cell findDestination(Village enemyVillage , String favoriteTarget) {
        return super.findDestination(enemyVillage , "Storage");
    }
}
