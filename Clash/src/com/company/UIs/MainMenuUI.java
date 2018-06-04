package com.company.UIs;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
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
        File file = new File("/Users/ashkan/Desktop/AP_7/Clash/src/com/company/ImagesAndGifs/MainMenu/Background.png");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        root.getChildren().add(backGroundView);

        file = new File("/Users/ashkan/Desktop/AP_7/Clash/src/com/company/ImagesAndGifs/MainMenu/NewGameButton.png");
        Image newGame = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth() / 4.5,Screen.getPrimary().getVisualBounds().getHeight() / 5, false, true);
        ImageView newGameView = new ImageView(newGame);
        file = new File("/Users/ashkan/Desktop/AP_7/Clash/src/com/company/ImagesAndGifs/MainMenu/LoadGameButton.png");
        Image loadGame = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth() / 4.5,Screen.getPrimary().getVisualBounds().getHeight() / 5, false, true);
        ImageView loadGameView = new ImageView(loadGame);
        file = new File("/Users/ashkan/Desktop/AP_7/Clash/src/com/company/ImagesAndGifs/MainMenu/ExitButton.png");
        Image exitGame = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth() / 4.5,Screen.getPrimary().getVisualBounds().getHeight() / 5, false, true);
        ImageView exitGameView = new ImageView(exitGame);

        makeEventHandlers(screenWidth, newGame, newGameView, loadGameView, exitGameView);

        VBox menuButtons = new VBox();
        menuButtons.setPrefWidth(screenWidth / 2);
        menuButtons.getChildren().add(newGameView);
        menuButtons.getChildren().add(loadGameView);
        menuButtons.getChildren().add(exitGameView);
        menuButtons.alignmentProperty().setValue(Pos.CENTER);

        menuButtons.relocate(screenWidth / 4, screenHeight / 5);

        root.getChildren().add(menuButtons);



        primaryStage.show();
    }

    private void makeEventHandlers(double screenWidth, Image newGame, ImageView newGameView, ImageView loadGameView, ImageView exitGameView) {
        newGameView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newGameView.setFitWidth(newGame.getWidth() * 1.1);
            }
        });
        newGameView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                newGameView.setFitWidth(screenWidth / 4.5);
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
                loadGameView.setFitWidth(screenWidth / 4.5);
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
                exitGameView.setFitWidth(screenWidth / 4.5);
            }
        });
    }
}
