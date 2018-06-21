package com.company.UIs;

import com.company.Controller.Controller;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.stage.Stage;

public class PassTurnThread extends Application implements Runnable {
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
            if (controller.getGame().isUnderAttackOrDefense()) {
                if (controller.getGame().isWarFinished()) {
                    controller.getGame().healAfterWar();
                    controller.getGame().setUnderAttackOrDefense(false);
                    try {
                        Task task = new Task() {
                            @Override
                            protected Object call() throws Exception {
                                System.out.println(stage);
                                MapUI.start(stage);
                                return null;
                            }
                        };
                        new Thread(task).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
                try {
                    Thread.sleep(UIConstants.DELTA_T);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        run();
    }
}
