package com.company.Models.Towers.Buildings;

import com.company.Models.Builder;
import com.company.Models.Towers.Cell;
import com.company.Models.Config;
import com.company.Models.Towers.Defences.Trap;
import com.company.Models.Towers.Defences.Wall;
import com.company.Models.Game;
import com.company.View.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainBuilding extends Building {
    private ArrayList<Builder> builders = new ArrayList<Builder>();
    private int numberOfBuilders = 1;

    public MainBuilding(int level) {
        super(1, level); // only one should be constructed at the beginning of the game
        this.setStrength(Config.getDictionary().get(this.getClass().getSimpleName()+"_STRENGTH"));
        Builder firstBuilder = new Builder(1);
        builders.add(firstBuilder);
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    public ArrayList<Builder> getBuilders() {
        return builders;
    }

    public void setBuilders(ArrayList<Builder> builders) {
        this.builders = builders;
    }

    public int getNumberOfBuilders() {
        return numberOfBuilders;
    }

    public void setNumberOfBuilders(int numberOfBuilders) {
        this.numberOfBuilders = numberOfBuilders;
    }

    public String findAvailableBuildings(int gold, int elixir) {
        ArrayList<String> allBuildings = new ArrayList<String>();
        for (Cell cell : Cell.getCellKinds()) {
            allBuildings.add(cell.getClass().getSimpleName());
        }
        allBuildings.remove("MainBuilding");
        Collections.sort(allBuildings);
        StringBuilder availableBuildings = new StringBuilder();
        for (String building : allBuildings) {
            if (Config.getDictionary().get(building + "_GOLD_COST") <= gold && Config.getDictionary().get(building + "_ELIXIR_COST") <= elixir) {
                availableBuildings.append(building).append("\n");
            }
        }
        availableBuildings = new StringBuilder(availableBuildings.toString().trim());
        return availableBuildings.toString();
    }

    public void showMenu() {
        Game.setWhereIAm("You are in TownHall Menu");
        String menu = "1. Info\n2. Available buildings\n3. Status\n4. back";
        View.show(menu);
    }

    public void upgrade() {
        setLevel(getLevel() + 1);
        if (getLevel() % 5 == 0) {
            numberOfBuilders++;
            Builder builder = new Builder(numberOfBuilders);
            builders.add(builder);
        }
        setStrength(getStrength() + 500);
    }

}
