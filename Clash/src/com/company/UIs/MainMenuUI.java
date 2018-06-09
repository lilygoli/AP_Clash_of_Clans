package com.company.UIs;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class MainMenuUI extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        primaryStage.setTitle("Clash");
        Group root = new Group();
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
        File file = new File("./src/com/company/UIs/MainMenu/menuGif.gif");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        root.getChildren().add(backGroundView);

        file = new File("./src/com/company/UIs/MainMenu/NewGameButton.png");
        Image newGame = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth() / 6,Screen.getPrimary().getVisualBounds().getHeight() / 6.5, false, true);
        ImageView newGameView = new ImageView(newGame);
        file = new File("./src/com/company/UIs/MainMenu/LoadGameButton.png");
        Image loadGame = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth() / 6,Screen.getPrimary().getVisualBounds().getHeight() / 6.5, false, true);
        ImageView loadGameView = new ImageView(loadGame);
        loadGameView.setScaleX(0.95);
        file = new File("./src/com/company/UIs/MainMenu/ExitButton.png");
        Image exitGame = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth() / 6,Screen.getPrimary().getVisualBounds().getHeight() / 6.5, false, true);
        ImageView exitGameView = new ImageView(exitGame);
        exitGameView.setScaleX(1.05);


        makeEventHandlersForHover(screenWidth, newGame, newGameView, loadGameView, exitGameView);
        makeEventHandlersForClick(screenWidth, newGame, newGameView, loadGameView, exitGameView, primaryStage);



        VBox menuButtons = new VBox();
        menuButtons.setPrefWidth(screenWidth / 2);
        menuButtons.getChildren().add(newGameView);
        menuButtons.getChildren().add(loadGameView);
        menuButtons.getChildren().add(exitGameView);
        menuButtons.alignmentProperty().setValue(Pos.CENTER);

        menuButtons.relocate(screenWidth / 4, screenHeight / 3.5);

        root.getChildren().add(menuButtons);



        primaryStage.show();
    }

    private void makeEventHandlersForClick(double screenWidth, Image newGame, ImageView newGameView, ImageView loadGameView, ImageView exitGameView, Stage primaryStage) {
        newGameView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapUI mapUI = new MapUI();
                try {
                    mapUI.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void makeEventHandlersForHover(double screenWidth, Image newGame, ImageView newGameView, ImageView loadGameView, ImageView exitGameView) {
        newGameView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newGameView.setFitWidth(newGame.getWidth() * 1.1);
            }
        });
        newGameView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newGameView.setFitWidth(screenWidth / 6);
            }
        });
        loadGameView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadGameView.setFitWidth(newGame.getWidth() * 1.1);
            }
        });
        loadGameView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadGameView.setFitWidth(screenWidth / 6);
            }
        });
        exitGameView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitGameView.setFitWidth(newGame.getWidth() * 1.1);
            }
        });
        exitGameView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitGameView.setFitWidth(screenWidth / 6);
            }
        });
    }
}
