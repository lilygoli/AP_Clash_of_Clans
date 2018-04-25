package com.company;

import com.company.Models.Buildings.Grass;
import com.company.Models.Cell;
import com.company.Models.Village;

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
                if (village.getMap()[j][i].getClass() == Grass.class && i < 29 && j < 29 && j > 0 && i > 0) {
                    result.append("0");
                } else {
                    result.append("1");
                }
            }
            result.append("\n");
        }
        result = new StringBuilder(result.toString().trim());
        show(result.toString());
    }
    // TODO: 4/25/2018 whereIam va showMenu inja filter e joda dashte bashe

}
