package com.company;

import java.util.Scanner;

public class View {
    Scanner scanner= new Scanner(System.in);
    public String getInput(){
        return scanner.nextLine();
    }
    public static void show(String command) {
        System.out.println(command);
    }

}
