package com.company.Models.Soldiers;

import com.company.Exception.BusyCellException;
import com.company.Exception.MarginalTowerException;
import com.company.Models.Buildings.Grass;
import org.omg.CORBA.BAD_CONTEXT;

public class Village{
    private Cell[][] map = new Cell[30][30];

    public Cell[][] getMap() {
        return map;
    }

    public void buildTower(Cell tower) throws BusyCellException, MarginalTowerException {
        if (tower.getX() <= 0 || tower.getX() >= 29 || tower.getY() <= 0 || tower.getY() >= 29) {
            throw new MarginalTowerException();
        }
        if (!map[tower.getX()][tower.getY()].getClass().isInstance(Grass.class)) {
            map[tower.getX()][tower.getY()] = tower;
        }
        else {
            throw new BusyCellException();
        }
    }
}
