package com.company.Models.Buildings;

import com.company.Models.Builder;
import com.company.View.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainBuilding extends Building {
    private ArrayList<Builder> builders = new ArrayList<Builder>();
    private int numberOfBuilders = 1;
    private GoldStorage innerGoldStorage = new GoldStorage();
    private ElixirStorage innerElixirStorage = new ElixirStorage();

    public MainBuilding() {
        super(1, 0); // only one should be constructed at the beginning of the game
        Builder firstBuilder = new Builder(1);
        builders.add(firstBuilder);
        this.setHealth(this.getStrength());
        this.setTimeLeftOfConstruction(this.getBuildDuration());
    }

    public int getNumberOfBuilders() {
        return numberOfBuilders;
    }

    public void setNumberOfBuilders(int numberOfBuilders) {
        this.numberOfBuilders = numberOfBuilders;
    }

    public void showAvailableBuildings(int gold, int elixir) {
        ArrayList<String> allBuildings = new ArrayList<String>(Arrays.asList(Building.getNameOfChildren()));
        Collections.sort(allBuildings);
        StringBuilder availableBuildings = new StringBuilder();
        int counter = 0;
        for (String building : allBuildings
                ) {
            if (Config.getDictionary().get(building + "_GOLD_COST") <= gold || Config.getDictionary().get(building + "ELIXIR_COST") <= elixir) {
                counter++;
                availableBuildings.append(Integer.toString(counter)).append(". ").append(building).append("\n");
            }
        }
        availableBuildings = new StringBuilder(availableBuildings.toString().trim());
        View.show(availableBuildings.toString());
    }

    public void showMenu() {
        // Game.setWhereIam("Town Hall Menu"); should be implemented here on in controller after class Game is implemented
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
        setHealth(getHealth() + 500);
    }
}
