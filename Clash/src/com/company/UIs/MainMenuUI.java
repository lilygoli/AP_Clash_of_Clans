package com.company.UIs;

import com.company.Exception.NotValidFilePathException;
import com.company.Models.Game;
import com.company.Models.GameCenter;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Camp;
import com.company.Models.Towers.Defences.ArcherTower;
import com.company.Models.Towers.Defences.Cannon;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class MainMenuUI extends Application{
    private static Thread gameLogic;

    public static Thread getGameLogic() {
        return gameLogic;
    }

    public static void setGameLogic(Thread gameLogic) {
        MainMenuUI.gameLogic = gameLogic;
    }

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
                MainMenuUI.gameLogic = new Thread(new PassTurnThread(mapUI.getController()));
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
                TextField textField=new TextField();
                textField.setPromptText("please enter your preferred path");
//                textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        textField.setText("");
//                    }
//                });
                textField.setMinWidth(500);
                textField.setMaxWidth(500);
                textField.setFont(Font.font(30));
                textField.setBackground(Background.EMPTY);
                textField.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
                textField.relocate(screenWidth / 3.3, screenHeight / 2.5);
                root.getChildren().add(textField);
                Button button= new Button("load");
                button.relocate(screenWidth / 2.3, screenHeight / 1.8);
                button.setMaxWidth(150);
                button.setMinWidth(150);
                button.setFont(Font.font(30));
                button.setBackground(Background.EMPTY);
                button.setStyle("-fx-border-radius: 10; -fx-border-width:3;  -fx-border-color: #a5862e;-fx-background-color: #a5862e");
                button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-border-radius: 10; -fx-border-width:3;  -fx-border-color: #8f631d;-fx-background-color: #8f631d");
                    }
                });
                button.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        button.setStyle("-fx-border-radius: 10; -fx-border-width:3;  -fx-border-color: #a5862e;-fx-background-color: #a5862e");
                    }
                });
                root.getChildren().add(button);
                button.setOnMouseClicked(event1 -> {
                    MapUI mapUI = new MapUI();
                    Game game = null;
                    try {
                        game = mapUI.getController().getGameCenter().loadGame(textField.getText());
                        mapUI.getController().setGame(game);
                        Thread gameLogic = new Thread(new PassTurnThread(mapUI.getController()));
                        for (Camp camp : mapUI.getController().getGame().getVillage().getCamps()) {
                            for (Soldier soldier : camp.getSoldiers()) {
                                soldier.setImageView(new ImageView());
                            }
                        }
                        gameLogic.start();
                        try {
                            MapUI.start(primaryStage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (NotValidFilePathException e) {
                        NotValidFilePathException exception = new NotValidFilePathException();
                        exception.getImageView().setX(Screen.getPrimary().getVisualBounds().getWidth()/2-exception.getImageView().getImage().getWidth()*2);
                        exception.getImageView().setY(exception.getImageView().getY()+70);
                        new Timeline(new KeyFrame(Duration.seconds(4), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                        root.getChildren().add(exception.getImageView());
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
