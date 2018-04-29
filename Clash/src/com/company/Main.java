package com.company;


import com.company.Models.Controller;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        View view = new View();
            controller.mainCommandAnalyzer();
    }
}
