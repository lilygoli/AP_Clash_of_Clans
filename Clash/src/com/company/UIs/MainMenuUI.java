package com.company.UIs;

import com.company.Exception.NotValidFilePathException;
import com.company.Models.Game;
import com.company.Models.GameCenter;
import com.company.Models.Towers.Defences.ArcherTower;
import com.company.Models.Towers.Defences.Cannon;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        Scene scene = new Scene(root, screenWidth,screenHeight);
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
        makeEventHandlersForClick(screenWidth,screenHeight,root, newGameView, loadGameView, exitGameView, primaryStage);



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

    private void makeEventHandlersForClick(double screenWidth,double screenHeight,Group root, ImageView newGameView, ImageView loadGameView, ImageView exitGameView, Stage primaryStage) {
        newGameView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                MapUI mapUI = new MapUI();
                Game game = mapUI.getController().getGameCenter().makeNewGame();
                mapUI.getController().setGame(game);
                Thread gameLogic = new Thread(new PassTurnThread(mapUI.getController()));
                gameLogic.start();
                try {
                    mapUI.start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        loadGameView.setOnMouseClicked(event -> {
                File file = new File("./src/com/company/UIs/MainMenu/menuGif.gif");
                Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), false, true);
                ImageView backGroundView = new ImageView(backGround);
                root.getChildren().add(backGroundView);
                TextField textField=new TextField("please enter your preferred path");
                textField.relocate(screenWidth / 2.5, screenHeight / 2);
                root.getChildren().add(textField);
                Button button= new Button("load");
                button.relocate(screenWidth / 2.5, screenHeight / 1.7);
                root.getChildren().add(button);
                button.setOnMouseClicked(event1 -> {
                    MapUI mapUI = new MapUI();
                    Game game = null;
                    try {
                        game = mapUI.getController().getGameCenter().loadGame(textField.getText());
                        mapUI.getController().setGame(game);
                        Thread gameLogic = new Thread(new PassTurnThread(mapUI.getController()));
                        gameLogic.start();
                        try {
                            mapUI.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (NotValidFilePathException e) {
                        e.printStackTrace();
                    }

                });

        });
        exitGameView.setOnMouseClicked(event -> {
            primaryStage.close();
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
