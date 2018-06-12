package com.company.UIs;

import com.company.Controller.Controller;

public class PassTurnThread implements Runnable {
    Controller controller = new Controller();

    public PassTurnThread(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while(true) {
            controller.getGame().passTurn();
            try {
                Thread.sleep(UIConstants.DELTA_T);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
