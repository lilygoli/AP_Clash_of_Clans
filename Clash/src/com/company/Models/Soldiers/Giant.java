package com.company.Models.Soldiers;

import com.company.Models.Cell;
import com.company.Models.Defences.Wall;
import com.company.Models.Village;

public class Giant extends Soldier {
    @Override
    public Cell findDestination(Village enemyVillage) {
        Cell destination = new Cell();
        double minDistance = 100;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (enemyVillage.getMap()[i][j].getClass() == (Storage.class) || enemyVillage.getMap()[i][j].getClass().equals(Mine.class)) {
                    if (Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2)) < minDistance) {
                        destination = enemyVillage.getMap()[i][j];
                        minDistance = Math.sqrt(Math.pow(x - i, 2) + Math.pow(y - j, 2));
                    }
                }
            }
        }
        return destination;
    }
}
