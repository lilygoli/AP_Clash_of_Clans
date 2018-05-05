package com.company.View;

import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.Storage;
import com.company.Models.Village;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
    private Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }

    public String getInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public static void show(String command) {
        System.out.println(command);
    }

    public void showMap(Village village) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (i >= 29 || j >= 29 || j <= 0 || i <= 0) {
                    result.append("1");
                } else {
                    if (village.getMap()[j][i].getClass() == Grass.class) {
                        result.append("0");
                    } else {
                        result.append("B");
                    }
                }
            }
            result.append("\n");
        }
        result = new StringBuilder(result.toString().trim());
        show(result.toString());
    }

    public void showAttackMap(Village village, ArrayList<Soldier> troops) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            outer:
            for (int j = 0; j < 30; j++) {
                for (Soldier troop : troops) {
                    if (troop.getX() == i && troop.getY() == j) {
                        result.append("S");
                        continue outer;
                    }
                }
                if (village.getMap()[j][i].getClass() == Grass.class) {
                    result.append("0");
                } else {
                    result.append("1");
                }
            }
            result.append("\n");
        }
        show(result.toString().trim());
    }
    // showMenu inja filter e joda dashte bashe

}
