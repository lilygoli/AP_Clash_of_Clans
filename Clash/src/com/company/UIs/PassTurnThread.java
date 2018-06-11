package com.company.UIs;

import com.company.Controller.Controller;

public class PassTurnThread implements Runnable {
    Controller controller = new Controller();
    public static final int deltaT = 10;

    public PassTurnThread(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while(true) {
            controller.getGame().passTurn();
            try {
                Thread.sleep(deltaT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
