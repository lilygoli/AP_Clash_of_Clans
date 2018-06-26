package com.company.UIs;

import com.company.Controller.Controller;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class PassTurnThread  implements Runnable {
    Controller controller = new Controller();
    Stage stage;

    public PassTurnThread(Controller controller , Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    @Override
    public void run() {
        while (true) {
            controller.getGame().passTurn();
            if (!controller.getGame().isUnderAttackOrDefense()) {
                try {
                    Thread.sleep(UIConstants.DELTA_T);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
