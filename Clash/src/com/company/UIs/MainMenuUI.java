package com.company.UIs;

import com.company.Exception.NotValidFilePathException;
import com.company.Models.Config;
import com.company.Models.Game;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Camp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.File;

public class MainMenuUI extends Application{
    private static Thread gameLogic;

    @Override
    public void start(Stage primaryStage){
        double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        primaryStage.setResizable(false);
        primaryStage.setTitle("ArLiKan");
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
        primaryStage.setOnCloseRequest(event -> System.exit(0));
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
        newGameView.setOnMouseClicked(event1 -> {
            root.getChildren().clear();
            File file = new File("./src/com/company/UIs/MainMenu/menuGif.gif");
            Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
            ImageView backGroundView = new ImageView(backGround);
            root.getChildren().add(backGroundView);
            Label label= new Label("Please enter your name");
            label.setFont(Font.font("Papyrus",27));
            label.setTextFill(Color.ROSYBROWN);
            TextField name = new TextField("");
            name.setMinWidth(200);
            name.setMaxWidth(200);
            Button enter = new Button("Enter");
            enter.setStyle("-fx-background-color: #a5862e");
            Button back = new Button("Back");
            back.setStyle("-fx-background-color: #a5862e");
            VBox firstVBox= new VBox(label,name,enter , back);
            firstVBox.relocate(screenWidth/2.65, screenHeight/2.4);
            firstVBox.setSpacing(10);
            firstVBox.setAlignment(Pos.CENTER);
            root.getChildren().add(firstVBox);
            enter.setOnMouseClicked(event -> {
                ProgressBar progressBar = new ProgressBar();
                progressBar.setScaleX(2);
                progressBar.setStyle("-fx-accent: #9f1037;");
                ProgressIndicator progressIndicator = new ProgressIndicator();
                progressIndicator.setStyle("-fx-accent: #9f1037;");
                Group loadingRoot = new Group();
                File fileBackground = new File("./src/com/company/UIs/MapResources/MapBackGround.jpg");
                Image backGroundImage = new Image(fileBackground.toURI().toString(), screenWidth, screenHeight, false, true);
                ImageView backGroundImageView = new ImageView(backGroundImage);
                backGroundImageView.setOpacity(0.7);
                loadingRoot.getChildren().add(backGroundImageView);
                VBox vBox = new VBox(progressBar, progressIndicator);
                vBox.setSpacing(10);
                vBox.relocate(screenWidth / 2.2, screenHeight / 2.2);
                loadingRoot.getChildren().add(vBox);
                Scene scene = new Scene(loadingRoot, screenWidth, screenHeight);
                primaryStage.setScene(scene);
                primaryStage.show();
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        Thread.sleep(5000);
                        return new MapUI();
                    }
                };
                new Thread(task).start();
                task.setOnSucceeded(event2 -> {
                    MapUI mapUI = (MapUI) task.getValue();
                    Game game = mapUI.getController().getGameCenter().makeNewGame();
                    mapUI.getController().setGame(game);
                    game.setPlayerName(name.getText());
                    MainMenuUI.gameLogic = new Thread(new PassTurnThread(mapUI.getController(), primaryStage));
                    gameLogic.start();
                    try {
                        MapUI.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            back.setOnMouseClicked(event -> start(primaryStage));
        });

        loadGameView.setOnMouseClicked(event -> loadGameButton(screenWidth, screenHeight, root, primaryStage));
        exitGameView.setOnMouseClicked(event -> primaryStage.close());
    }

    private void loadGameButton(double screenWidth, double screenHeight, Group root, Stage primaryStage) {
        File file = new File("./src/com/company/UIs/MainMenu/menuGif.gif");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        root.getChildren().add(backGroundView);
        TextField textField = new TextField();
        textFiledStyle(textField , screenWidth , screenHeight);
        root.getChildren().add(textField);
        Button load = new Button("Load");
        load.relocate(screenWidth / 2.3, screenHeight / 2.0);
        buttonStyles(load);
        changeColorButtons(load);
        root.getChildren().add(load);
        load.setOnMouseClicked(event1 -> {
            MapUI mapUI = new MapUI();
            Game game;
            try {
                game = mapUI.getController().getGameCenter().loadGame(textField.getText());
                mapUI.getController().setGame(game);
                Thread gameLogic = new Thread(new PassTurnThread(mapUI.getController() , primaryStage));
                for (Camp camp : mapUI.getController().getGame().getVillage().getCamps()) {
                    for (Soldier soldier : camp.getSoldiers()) {
                        soldier.setImageView(new ImageView());
                        Rectangle leftHealth=new Rectangle((1.0*Screen.getPrimary().getVisualBounds().getHeight() / 32)*soldier.getHealth()/ Config.getDictionary().get(soldier.getClass().getSimpleName() + "_HEALTH"),1);
                        leftHealth.setFill(Color.rgb(6,87,51));
                        soldier.setLeftHealth(leftHealth);
                        Rectangle allHealth=new Rectangle(5,1);
                        allHealth.setFill(Color.rgb(159,15,55));
                        soldier.setAllHealth(allHealth);
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
        Button back = new Button("Back");
        back.relocate(screenWidth / 2.3, screenHeight / 1.6);
        buttonStyles(back);
        back.setOnMouseClicked(event -> start(primaryStage));
        changeColorButtons(back);
        root.getChildren().add(back);
    }

    private void textFiledStyle(TextField textField , double screenWidth, double screenHeight) {
        textField.setPromptText("please enter your preferred path");
        textField.setFocusTraversable(false);
        textField.setMinWidth(500);
        textField.setMaxWidth(500);
        textField.setFont(Font.font(30));
        textField.setBackground(Background.EMPTY);
        textField.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
        textField.relocate(screenWidth / 3.3, screenHeight / 2.75);
    }

    private void buttonStyles(Button button) {
        button.setMaxWidth(150);
        button.setMinWidth(150);
        button.setFont(Font.font(30));
        button.setBackground(Background.EMPTY);
        button.setStyle("-fx-border-radius: 1; -fx-border-width:3;  -fx-border-color: #a5862e;-fx-background-color: #a5862e");
    }

    private void changeColorButtons(Button button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-border-radius: 10; -fx-border-width:3;  -fx-border-color: #8f631d;-fx-background-color: #8f631d"));
        button.setOnMouseExited(event -> button.setStyle("-fx-border-radius: 10; -fx-border-width:3;  -fx-border-color: #a5862e;-fx-background-color: #a5862e"));
    }

    private void makeEventHandlersForHover(double screenWidth, Image newGame, ImageView newGameView, ImageView loadGameView, ImageView exitGameView) {
        newGameView.setOnMouseMoved(event -> newGameView.setFitWidth(newGame.getWidth() * 1.1));
        newGameView.setOnMouseExited(event -> newGameView.setFitWidth(screenWidth / 6));
        loadGameView.setOnMouseMoved(event -> loadGameView.setFitWidth(newGame.getWidth() * 1.1));
        loadGameView.setOnMouseExited(event -> loadGameView.setFitWidth(screenWidth / 6));
        exitGameView.setOnMouseMoved(event -> exitGameView.setFitWidth(newGame.getWidth() * 1.1));
        exitGameView.setOnMouseExited(event -> exitGameView.setFitWidth(screenWidth / 6));
    }
}
