package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Exception.*;
import com.company.Models.Builder;
import com.company.Models.Config;
import com.company.Models.Game;
import com.company.Models.Resource;
import com.company.Models.Towers.Buildings.*;
import com.company.Models.Towers.Cell;
import com.company.Models.Towers.Defences.Wall;
import com.company.Multiplayer.*;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SideBarUI {
    private static final String ADDRESS = "./src/com/company/UIs/SideBarMenuImages/";
    private static Controller controller;
    public static Stage primaryStage;
    public static ComboBox<String> clientsComboBox = new ComboBox<>();
    public static TextArea chatsArea = new TextArea();
    public static TextArea leaderBoard = new TextArea();
    public static ArrayList<String >  availableVillagesToAttack= new ArrayList<>();
    public static String port;
    public static int allGainedGoldsResources = 0;
    public static int allGainedElixirResources = 0;
    public static boolean isInSinglePlayer = false;
    public static ImageView chatButton = new ImageView();
    public static ImageView chatBackground = new ImageView();
    public static HBox hBox = new HBox();
    public static VBox vBox = new VBox();


    static {
        leaderBoard.setEditable(false);
        leaderBoard.relocate(50 , 180);
        leaderBoard.setMinWidth(200);
        leaderBoard.setMaxWidth(200);
        leaderBoard.setStyle("-fx-background-color: #a5862e");
        chatsArea.setEditable(false);
        chatsArea.setMinWidth(220);
        chatsArea.setMaxWidth(220);
        chatsArea.setMaxHeight(600);
        chatsArea.setMinHeight(600);
        //chatsArea.setStyle("-fx-background-color: rgba(53,89,119,0.4); .scroll-pane");
        File filecss = new File("./src/com/company/UIs/SideBarMenuImages/textArea.css");
        chatsArea.getStylesheets().add(filecss.toURI().toString());

        File file = new File("./src/com/company/UIs/SideBarMenuImages/ChatroomBackground.jpg");
        Image image = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        chatBackground.setImage(image);
        chatBackground.setScaleY(1);
        chatBackground.setScaleX(0.3);
        chatBackground.relocate(-1000 , -1000);
        //chatBackground.relocate(-630 , 0);
        File file2 = new File("./src/com/company/UIs/SideBarMenuImages/ChatButton.png");
        Image image2 = new Image(file2.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        chatButton.setImage(image2);
        chatButton.setScaleX(0.02);
        chatButton.setScaleY(0.1);
        chatButton.relocate(-1000 , -1000);
        //chatButton.relocate(-363 , -20);
    }

    public static void setController(Controller controller) {
        SideBarUI.controller = controller;
    }

    public static void makeSideBar(Group group, boolean isInEnemyMap) {
        clientsComboBox.getItems().clear();
        ImageView sideBarBackgroundImageView =getImageView("labelLessCroppedMenu.png");
        Double sideBarStartingX = -sideBarBackgroundImageView.getImage().getWidth() / 16;
        sideBarBackgroundImageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());
        sideBarBackgroundImageView.setScaleY(0.95);
        sideBarBackgroundImageView.setX(sideBarStartingX);
        sideBarBackgroundImageView.setY(-20);
        ImageView borderImageView;
        if(!isInEnemyMap){
            borderImageView = getImageView("upperBorder.png");
        }else {
            borderImageView = getImageView("AttackUpperBorder.png");
        }

        borderImageView.setScaleX(0.6);
        borderImageView.setScaleY(0.8);
        borderImageView.setY(40);
        borderImageView.setX(sideBarStartingX + 20);

        ImageView saveView = getImageView("save.png");
        saveView.setOnMouseClicked(event -> {
            makeSideBar(group,false);
            TextField pathTextField=new TextField("enter path");
            TextField nameTextField= new TextField("enter name");
            Button saveButton=new Button("save");
            pathTextField.setBackground(Background.EMPTY);
            pathTextField.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
            pathTextField.setOnMouseClicked(event12 -> pathTextField.setText(""));
            nameTextField.setBackground(Background.EMPTY);
            nameTextField.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
            nameTextField.setOnMouseClicked(event13 -> nameTextField.setText(""));
            saveButton.setStyle("-fx-background-color: rgba(143,99,29,0.87)");
            VBox vBox = new VBox(10 , pathTextField , nameTextField , saveButton);
            vBox.relocate(UIConstants.BUTTON_STARTING_X + 10 , Screen.getPrimary().getVisualBounds().getHeight() * 0.2);
            group.getChildren().add(vBox);
            saveButton.setOnMouseClicked(event1 -> {
                try {
                    MapUI.getShowMapAnimationTimer().stop();
                    controller.getGameCenter().saveGame(controller.getGame(), pathTextField.getText(), nameTextField.getText());
                    primaryStage.close();
                } catch (NotValidFilePathException e) {
                    NotValidFilePathException exception = new NotValidFilePathException();
                    new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(exception.getImageView());
                }
            });

        });
        saveView.setX(sideBarStartingX + 30);
        saveView.setY(Screen.getPrimary().getVisualBounds().getHeight() * 6 / 10);
        saveView.setScaleX(0.5);
        group.getChildren().add(sideBarBackgroundImageView);
        group.getChildren().add(borderImageView);
        if(!isInEnemyMap) {
            makeResourceLabels(group,sideBarStartingX);
            group.getChildren().add(saveView);
        }else {
            Label gold=new Label("");
            Label elixir= new Label("");
            Label achievedGold= new Label("");
            Label achievedElixir= new Label("");
            group.getChildren().add(gold);
            group.getChildren().add(elixir);
            group.getChildren().add(achievedGold);
            group.getChildren().add(achievedElixir);
            new AnimationTimer() {
                @Override
                public void handle(long now) {
                    makeResourceLabelsInAttack(group, sideBarStartingX, gold, elixir, achievedGold, achievedElixir);
                }
            }.start();
        }

    }

    public static void makeStartingMenu(Group group, Stage stage){
        primaryStage = stage;
        makeSideBar(group,false);
        ImageView attackImage = getImageView("Attack.png");
        String playerName=MapUI.getController().getGame().getPlayerName();
        attackImage.setOnMouseClicked((MouseEvent event) -> {
            //start
            makeSideBar(group,false);
            Button multiPlayer= new Button("MultiPlayer");
            multiPlayer.setMinWidth(100);
            multiPlayer.setMaxWidth(100);
            multiPlayer.setStyle("-fx-background-color: #a5862e");
            multiPlayer.setOnMouseClicked(event3 -> {
                makeSideBar(group,false);
                Button host = new Button("Host");
                host.setStyle("-fx-background-color: #a5862e");
                host.setMinWidth(70);
                host.setMaxWidth(70);
                host.setOnMouseClicked(event1 -> {
                    AttackMapUI.server = new Server();
                    AttackMapUI.server.start();
                    try {
                        if (AttackMapUI.udpSocket == null) {
                            AttackMapUI.udpSocket = new DatagramSocket(12346);//change
                        }
                    } catch (SocketException e) {
                        e.printStackTrace();
                    }
                    try {
                        initClient(playerName, "localhost");
                        makeLoadEnemyMapMenu(group);
                    } catch (Exception e) {
                        makeLoadEnemyMapMenu(group);
                    }
                });
                Button client = new Button("join");
                client.setStyle("-fx-background-color: #a5862e");
                client.setMinWidth(70);
                client.setMaxWidth(70);
                client.setOnMouseClicked(event1 -> {
                    TextField ip = new TextField("");
                    ip.setBackground(Background.EMPTY);
                    ip.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
                    // ip.relocate(UIConstants.ATTACK_STARTING_X, UIConstants.ATTACK_STARTING_Y + 90);
                    ip.setPrefWidth(100);
//                    TextField port = new TextField("");
//                    port.setBackground(Background.EMPTY);
//                    port.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
                    // port.relocate(UIConstants.ATTACK_STARTING_X, UIConstants.ATTACK_STARTING_Y + 120);
                    //port.setPrefWidth(100);
                    Button connect = new Button("Connect");
                    //connect.relocate(UIConstants.ATTACK_STARTING_X , UIConstants.ATTACK_STARTING_Y + 140);
                    group.getChildren().add(connect);
                    connect.setStyle("-fx-background-color: #a5862e");
                    connect.setMinWidth(150);
                    connect.setMaxWidth(150);
                    connect.setOnMouseClicked(event2 -> {
                        //SideBarUI.port= port.getText();
                        try {
                            if (AttackMapUI.udpSocket == null) {
                                AttackMapUI.udpSocket = new DatagramSocket(12346);//change
                            }
                        } catch (SocketException e) {
                            e.printStackTrace();
                        }
                        try {
                            initClient(playerName, ip.getText());
                            makeLoadEnemyMapMenu(group);
                        }
                        catch (IOException e){
                            makeStartingMenu(group , stage);
                        }
                    });
                    VBox vBox3 = new VBox(20 , ip , connect);
                    vBox3.relocate(UIConstants.ATTACK_STARTING_X + 40 , UIConstants.ATTACK_STARTING_Y + 130);
                    group.getChildren().add(vBox3);
                    connect.relocate(UIConstants.ATTACK_STARTING_X + 80 , UIConstants.ATTACK_STARTING_Y + 130);
                });
                Button back = new Button("back");
                back.setStyle("-fx-background-color: #a5862e");
                back.setMinWidth(70);
                back.setMaxWidth(70);
                back.setOnMouseClicked(event12 -> makeStartingMenu(group ,stage));
                VBox vBox2 = new VBox(20 , host , client , back);
                vBox2.relocate(UIConstants.ATTACK_STARTING_X  + 75 , UIConstants.ATTACK_STARTING_Y);
                group.getChildren().add(vBox2);
            });
            Button singlePlayer = new Button("Single Player");
            singlePlayer.setMinWidth(100);
            singlePlayer.setMaxWidth(100);
            singlePlayer.setStyle("-fx-background-color: #a5862e");
            singlePlayer.setOnMouseClicked(event1 -> {
                isInSinglePlayer = true;
                makeSideBar(group,false);
                makeComboBox(group,false);
            });
            VBox vBox = new VBox(20 ,multiPlayer , singlePlayer);
            vBox.relocate(UIConstants.ATTACK_STARTING_X  + 60 , UIConstants.ATTACK_STARTING_Y);
            group.getChildren().add(vBox);
        });
        attackImage.setScaleX(0.6);
        attackImage.setScaleY(0.8);
        attackImage.relocate(UIConstants.ATTACK_STARTING_X,UIConstants.ATTACK_STARTING_Y);
        group.getChildren().add(attackImage);
    }

    private static void initClient(String  name, String ip) throws IOException{
        AttackMapUI.clientSocket = new Socket(ip , 12345);
        AttackMapUI.clientObjectOutput = new ObjectOutputStream(AttackMapUI.clientSocket.getOutputStream());
        AttackMapUI.clientObjectInput = new ObjectInputStream(AttackMapUI.clientSocket.getInputStream());
//        AttackMapUI.leaderBoardOutput = new ObjectOutputStream(AttackMapUI.leaderBoardSocket.getOutputStream());
//        AttackMapUI.leaderBoardInput = new ObjectInputStream(AttackMapUI.leaderBoardSocket.getInputStream());
        Thread clientInputListener = new ClientInputListener();
        clientInputListener.start();
//            new LeaderBoardListenerOnClient().start();
        AttackMapUI.clientName=name;
        AttackMapUI.clientObjectOutput.writeObject(name);
    }

    public static void makeLoadEnemyMapMenu(Group group) {
        chatButton.relocate(-1000 , -1000);
        chatBackground.relocate(-1000 , -1000);
        makeSideBar(group,false);

        String ip = "";

        try {
            InetAddress IP=InetAddress.getLocalHost();
            ip = IP.getHostAddress();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Label ipLabel = new Label(ip);
        ipLabel.relocate(300, 50);
        group.getChildren().add(ipLabel);

        //ComboBox<String> comboBox = new ComboBox<>();
        clientsComboBox.setOnMouseClicked(event -> {
            if(!isInSinglePlayer) {
                try {
                    AttackMapUI.clientObjectOutput.writeObject("giveClients");
                    AttackMapUI.clientObjectOutput.flush();
                    Thread.sleep(20);
                    clientsComboBox.getItems().clear();
                    clientsComboBox.getItems().addAll(availableVillagesToAttack);
                } catch (IOException | InterruptedException e) {
                    catchServerException();
                }
            }
        });
        makeComboBox(group,true);
        File file = new File("./src/com/company/UIs/SideBarMenuImages/ChatButton.png");
        Image image = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView chatRoomButton = new ImageView(image);
        chatRoomButton.setScaleX(0.02);
        chatRoomButton.setScaleY(0.1);
        chatRoomButton.relocate(-630 , -20);
        group.getChildren().add(chatRoomButton);
        chatRoomButton.setOnMouseClicked(event1 -> makeChatRoomSideBar(group));
        Button leaderBoard = new Button("LeaderBoard");
        leaderBoard.setStyle("-fx-background-color: #a5862e");
        leaderBoard.relocate(90 , UIConstants.MENU_VBOX_STARTING_Y + 100);
        group.getChildren().add(leaderBoard);
        leaderBoard.setMaxWidth(100);
        leaderBoard.setMinWidth(100);
        leaderBoard.setOnMouseClicked(event -> {
            showLeaderBoard(group);
        });
    }

    public static void catchServerException() {
        try {
            if(!AttackMapUI.clientSocket.isClosed()) {
                AttackMapUI.clientSocket.close();
                AttackMapUI.clientObjectOutput.close();
                AttackMapUI.clientObjectInput.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static void showLeaderBoard(Group group) {
        makeSideBar(group , false);
        if (group.getChildren().contains(leaderBoard)){
            group.getChildren().remove(leaderBoard);
        }
        group.getChildren().add(leaderBoard);
        Button back = new Button("back");
        back.relocate(90 , 390);
        back.setMaxWidth(100);
        back.setMinWidth(100);
        back.setStyle("-fx-background-color: #a5862e");
        group.getChildren().add(back);
        back.setOnMouseClicked(event -> makeLoadEnemyMapMenu(group));
    }

    private static void makeComboBox(Group group, boolean multiPlayer) {
        if(group.getChildren().contains(clientsComboBox)){
            group.getChildren().remove(clientsComboBox);
        }
        if(!multiPlayer){
            StringBuilder enemyMapsList = new StringBuilder("1. load map\n");
            int index = 2;
            for (Game game : controller.getGame().getAllAttackedVillages()) {
                enemyMapsList.append(index).append(". ").append(game.getPlayerName()).append("\n");
                index++;
            }
            clientsComboBox.getItems().clear();
            clientsComboBox.getItems().addAll(enemyMapsList.toString().split("\n"));
        }
        clientsComboBox.setBackground(Background.EMPTY);
        clientsComboBox.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
        clientsComboBox.setMaxWidth(300);
        clientsComboBox.setMaxWidth(300);
        clientsComboBox.relocate(90, UIConstants.MENU_VBOX_STARTING_Y);
        Button selectButton=new Button("select");
        selectButton.setStyle("-fx-background-color: #a5862e");
        selectButton.relocate(90,UIConstants.MENU_VBOX_STARTING_Y + 60);
        selectButton.setMinWidth(100);
        selectButton.setMaxWidth(100);
        selectButton.setOnMouseClicked(event -> {
            if(!isInSinglePlayer)
                loadEnemyMap(group, clientsComboBox);
            else {
                loadEnemyMapSinglePlayer(group,clientsComboBox);
            }
        });
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event3 -> {
            try {
                if (multiPlayer && Server.serverSocket!= null) {
                    Server.serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            makeStartingMenu(group,primaryStage);
        });
        group.getChildren().add(selectButton);
        group.getChildren().add(backView);
        group.getChildren().add(clientsComboBox);

        if (multiPlayer) {
//            System.out.println("my log");
            chatButton.relocate(-630 , -20);
            chatButton.setOnMouseClicked(event1 -> makeChatRoomSideBar(group));
        }
    }

    private static void makeChatRoomSideBar(Group group) {
        if (group.getChildren().contains(chatButton)){
            group.getChildren().remove(chatButton);
        }
        if (group.getChildren().contains(chatBackground)){
            group.getChildren().remove(chatBackground);
        }
        group.getChildren().add(chatButton);
        group.getChildren().add(chatBackground);


        chatButton.setOnMouseClicked(event1 -> makeLoadEnemyMapMenu(group));
        TextField message=new TextField("");
        message.setMinWidth(180);
        message.setMaxWidth(180);
        message.setBackground(Background.EMPTY);
        message.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
        Button send = new Button("Send");
        send.setStyle("-fx-background-color: #a5862e");
        send.relocate(20,UIConstants.MENU_VBOX_STARTING_Y + 100);
        group.getChildren().add(send);
        send.setOnMouseClicked(event1 -> {
            try {
                AttackMapUI.clientObjectOutput.writeObject("&" + MapUI.getController().getGame().getPlayerName() + " : " + message.getText());
                message.setText("");
            } catch (IOException e) {
                catchServerException();
            }
        });
        hBox = new HBox(10 , message ,send);
        vBox = new VBox(10 , hBox ,chatsArea);
        group.getChildren().add(vBox);
        timeLineForChat();
    }

    private static void timeLineForChat() {
        chatBackground.relocate(-chatBackground.getImage().getWidth() / 2.5 - chatBackground.getImage().getWidth() , 0);
        chatButton.relocate(-chatButton.getImage().getWidth() / 4 + 10 - chatBackground.getImage().getWidth() , -20);
        vBox.relocate(40 - chatBackground.getImage().getWidth() , 30);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1) , e->{
            chatButton.setX(chatButton.getX() + 1);
            chatBackground.setX(chatBackground.getX() + 1);
            vBox.setTranslateX(vBox.getTranslateX() + 1);
        })
        );
        timeline.setCycleCount((int) chatBackground.getImage().getWidth());
        timeline.play();
    }

    private static void loadEnemyMap(Group group, ComboBox<String> comboBox) {
        try {
            String nameOfEnemy = comboBox.getValue();
            System.out.println(comboBox.getValue());
            AttackMapUI.clientObjectOutput.reset(); //reset
            AttackMapUI.clientObjectOutput.writeObject(nameOfEnemy);
            while(controller.getGame().getAttackedVillage() == null) {
                System.out.println("stuck");
            }
            AttackMapUI.makeAttackGameBoard(primaryStage,controller);
            Thread streamer = new LiveAttackStreamer();
            streamer.start();
        } catch (IOException e) {
            catchServerException();
        }
    }

    private static void loadEnemyMapSinglePlayer(Group group , ComboBox comboBox) {
        if (comboBox.getValue().equals("1. load map")) {
            TextField textField = new TextField("please enter path");
            textField.relocate(UIConstants.BUTTON_STARTING_X, 400);
            group.getChildren().add(textField);
            Button loadButton = new Button("load");
            group.getChildren().add(loadButton);
            loadButton.relocate(200, 400);
            loadButton.setStyle("-fx-background-color: #a5862e");
            loadButton.setOnMouseClicked(event2 -> {
                Game enemyGame = null;
                try {
                    enemyGame = controller.getGameCenter().loadEnemyMap(textField.getText());
                    controller.getGame().setAttackedVillage(enemyGame);
                    AttackMapUI.makeAttackGameBoard(primaryStage, controller);
//                    if (!controller.getGame().getAllAttackedVillages().contains(enemyGame)) {
//                        controller.getGame().getAllAttackedVillages().add(enemyGame);
//                    }
                } catch (NotValidFilePathException e) {
                    new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                } catch (FileNotFoundException e) {
                    NotValidFilePathException e1 = new NotValidFilePathException();
                    new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(e1.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e1.getImageView());
                }

            });
        }else{
            controller.getGame().setAttackedVillage(controller.getGame().getAllAttackedVillages().get(Integer.parseInt(((String)comboBox.getValue()).split("\\.")[0])-2));
            try {
                AttackMapUI.makeAttackGameBoard(primaryStage,controller);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    private static void makeResourceLabels(Group group,Double sideBarStartingX) {
        Label gold= new Label(Integer.toString(controller.getGame().getVillage().getResource().getGold()));
        gold.relocate(sideBarStartingX+140,65);
        Label elixir = new Label(Integer.toString(controller.getGame().getVillage().getResource().getElixir()));
        elixir.relocate(sideBarStartingX+220,65);
        Label score = new Label(Integer.toString(controller.getGame().getVillage().getScore()));
        score.relocate(sideBarStartingX+180,100);
        group.getChildren().add(gold);
        group.getChildren().add(elixir);
        group.getChildren().add(score);
    }
    private static void makeResourceLabelsInAttack(Group group, Double sideBarStartingX,Label gold,Label elixir, Label goldAchieved, Label elixirAchieved) {
        gold.setText(Integer.toString(controller.getGame().getAttackedVillage().getVillage().getResource().getGold()));
        gold.relocate(sideBarStartingX+130,65);
        elixir.setText(Integer.toString(controller.getGame().getAttackedVillage().getVillage().getResource().getElixir()));
        elixir.relocate(sideBarStartingX+220,65);
        goldAchieved.setText(Integer.toString(controller.getGame().getVillage().getGainedResource().getGold()));
        goldAchieved.relocate(sideBarStartingX+180,85);
        elixirAchieved.setText(Integer.toString(controller.getGame().getVillage().getGainedResource().getElixir()));
        elixirAchieved.relocate(sideBarStartingX+180,105);

    }

    public static void makeMainBuildingMenu(Group group) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> makeDefaultInfoMenu(group,controller.getGame().getVillage().getMap()[14][14]));
        ImageView availableBuildingView = getImageView("AvailableBuildings.png");
        availableBuildingView.setOnMouseClicked(mouseEvent -> showAvailableBuildings(group));
        ImageView statusView = getImageView("Status.png");
        statusView.setOnMouseClicked(event -> {
            makeSideBar(group,false);
            makeLabels(group,controller.getGame().getVillage().showTownHallStatus().trim(),0.2 ,10 ,false);
            ImageView backView = getImageView("back.png");
            backView.setScaleX(0.5);
            backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
            backView.setX(UIConstants.BUTTON_STARTING_X);
            backView.setOnMouseClicked(backEvent -> makeMainBuildingMenu(group));
            group.getChildren().add(backView);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            backInVillage(group);
        });
        VBox vBox = new VBox(infoView, availableBuildingView, statusView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    public static ImageView getImageView(String imageName) {
        File infoFile = new File(ADDRESS + imageName);
        Image infoImage = new Image(infoFile.toURI().toString());
        return new ImageView(infoImage);
    }

    private static void showAvailableBuildings(Group group) {
        makeSideBar(group,false);
        makeLabels(group,"choose your preferred building",0.17, 10,false);
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setBackground(Background.EMPTY);
        comboBox.setStyle("-fx-border-radius: 5; -fx-border-width:3;  -fx-border-color: rgba(143,99,29,0.87)");
        String availableBuildings = controller.getGame().getVillage().getMainBuilding().findAvailableBuildings(controller.getGame().getVillage().getResource().getGold(), controller.getGame().getVillage().getResource().getElixir());
        comboBox.getItems().addAll(availableBuildings.split("\n"));
        comboBox.relocate(70, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(comboBox);
        Button selectButton=new Button("select");
        selectButton.setStyle("-fx-background-color: #a5862e");
        selectButton.relocate(200,UIConstants.MENU_VBOX_STARTING_Y + 3);
        selectButton.setOnMouseClicked(event -> {
            showAvailableBuildings(group);
            implementBuildATowerCommand(comboBox.getValue(),group);
        });
        group.getChildren().add(selectButton);
        ImageView backView = getImageView("Back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event -> {
            makeMainBuildingMenu(group);
        });
        group.getChildren().add(backView);
    }

    public static void makeStorageMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        overAllInfoView.setOnMouseClicked(event -> showOverallInfo(group , cell));
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        UpgradeInfoView.setOnMouseClicked(event -> showUpgradeInfo(group , cell));
        ImageView SourcesInfoView = getImageView("SourcesInfo.png");
        SourcesInfoView.setOnMouseClicked(event -> showSourcesInfo(group , cell));
        ImageView upgradeView = getImageView("Upgrade.png");
        upgradeView.setOnMouseClicked(event -> implementUpgradeBuildings(group , cell));
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> backInVillage(group));
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, SourcesInfoView, upgradeView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void showSourcesInfo(Group group, Cell cell) {
        Storage storage = (Storage)cell;
        makeSideBar(group,false);
        ImageView sourcesInfoInsides;
        if (storage.getClass().getSimpleName().equals("GoldStorage")) {
            sourcesInfoInsides = getImageView("sourcesInfoInsides2.png");
        }
        else{
            sourcesInfoInsides = getImageView("sourcesInfoInsides.png");
        }
        sourcesInfoInsides.setX(UIConstants.INFOMENU_STARTING_X);
        sourcesInfoInsides.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(sourcesInfoInsides);
        Label label=new Label(Integer.toString(storage.getResource()));
        label.relocate(UIConstants.INFO_LABEL_STARTING_X  - 45, Screen.getPrimary().getVisualBounds().getHeight() * 0.355);
        label.setTextFill(Color.BROWN);
        label.setFont(Font.font("Papyrus",18));
        group.getChildren().add(label);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> backSwitchCaseFunction(group, cell));
    }

    public static void makeCampMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> makeCampInfoMenu(group , cell));
        ImageView SoldiersView = getImageView("Soldiers.png");
        SoldiersView.setOnMouseClicked(event -> makeSoldiersMenu(group , cell));
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> backInVillage(group));
        VBox vBox = new VBox(1, infoView, SoldiersView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void makeSoldiersMenu(Group group, Cell cell) {
        Camp camp = (Camp)cell;
        makeSideBar(group,false);
        makeLabels(group,camp.showSoldiers().trim(),0.2 ,10 ,false);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> makeCampMenu(group  ,cell));
    }

    public static void makeCampInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overAllInfoView = getImageView("OverAllInfo.png");
        overAllInfoView.setOnMouseClicked(event -> showOverallInfo(group , cell));
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        UpgradeInfoView.setOnMouseClicked(event -> showUpgradeInfo(group , cell));
        ImageView CapacityInfoView = getImageView("CapacityInfo.png");
        CapacityInfoView.setOnMouseClicked(event -> showCampCapacityInfo(group , cell));
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> makeCampMenu(group , cell));
        VBox vBox = new VBox(1, overAllInfoView, UpgradeInfoView, CapacityInfoView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void showCampCapacityInfo(Group group, Cell cell) {
        Camp camp = (Camp)cell;
        makeSideBar(group,false);
        ImageView campCapacity= getImageView("campCapacity.png");
        campCapacity.setX(UIConstants.INFOMENU_STARTING_X);
        campCapacity.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(campCapacity);
        makeLabels(group,Integer.toString(camp.getCapacity()),0.39, 10,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> makeCampInfoMenu(group  ,cell));
    }

    public static void makeMineMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> makeDefaultInfoMenu(group,cell));
        ImageView mineView = getImageView("Mine.png");
        mineView.setOnMouseClicked(event -> {
            Mine mine = (Mine) cell;
            if (mine.getClass().getSimpleName().equals("GoldMine")) {
                ArrayList<Storage> allGoldStorage = new ArrayList<>(controller.getGame().getVillage().getGoldStorages());
                mine.mine(allGoldStorage);
            } else {
                ArrayList<Storage> allElixirStorage = new ArrayList<>(controller.getGame().getVillage().getElixirStorages());
                mine.mine(allElixirStorage);
            }
            makeMineMenu(group,cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> backInVillage(group));
        VBox vBox = new VBox(1, infoView, mineView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void makeDefaultInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overallInfoView = getImageView("OverAllInfo.png");
        overallInfoView.setOnMouseClicked(event -> showOverallInfo(group, cell));
        ImageView UpgradeInfoView = getImageView("UpgradeInfo.png");
        UpgradeInfoView.setOnMouseClicked(event -> showUpgradeInfo(group, cell));
        ImageView upgradeView = getImageView("Upgrade.png");
        upgradeView.setOnMouseClicked(event -> implementUpgradeBuildings(group, cell));
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> makeBuildingsMenu(group,cell));
        VBox vBox = new VBox(1, overallInfoView, UpgradeInfoView,upgradeView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void implementUpgradeBuildings(Group group, Cell cell) {
        if (cell.getClass().getSimpleName().equals("Barrack")) {
            if (cell.getLevel() + 1 > controller.getGame().getVillage().getMainBuilding().getLevel()) {
                ImageView upgradeError = getImageView("BarrackUpgradeError.png");
                upgradeError.setY(Screen.getPrimary().getVisualBounds().getHeight() * 0.6);
                upgradeError.setX(UIConstants.BUTTON_STARTING_X);
                new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(upgradeError.imageProperty(), null))).play();
                group.getChildren().add(upgradeError);
                return;
            }
        }
        makeSideBar(group,false);
        ImageView upgradeQuestion = getImageView("UpgradeQuestion.png");
        upgradeQuestion.setY(Screen.getPrimary().getVisualBounds().getHeight() * 0.3);
        upgradeQuestion.setX(UIConstants.BUTTON_STARTING_X);
        Label cost = new Label(Integer.toString(cell.getUpgradeCost()));
        cost.setTextFill(Color.BROWN);
        cost.setScaleX(1.2);
        cost.relocate(UIConstants.BUTTON_STARTING_X + 60, Screen.getPrimary().getVisualBounds().getHeight() * 0.375);
        ImageView upgrade = getImageView("Upgrade.png");
        upgrade.setScaleX(0.6);
        upgrade.setX(UIConstants.BUTTON_STARTING_X);
        upgrade.setY(Screen.getPrimary().getVisualBounds().getHeight() * 0.45);
        group.getChildren().add(upgradeQuestion);
        group.getChildren().add(cost);
        group.getChildren().add(upgrade);
        upgrade.setOnMouseClicked(event1 -> {
            if (cell.getUpgradeCost() > controller.getGame().getVillage().getResource().getGold()) {
                NotEnoughResourcesException exception = new NotEnoughResourcesException();
                new Timeline(new KeyFrame(Duration.seconds(2), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                group.getChildren().add(exception.getImageView());
            } else {
                cell.setTimeLeftOfUpgrade(cell.getBuildDuration());
                Resource resource = new Resource(controller.getGame().getVillage().getResource().getGold() - cell.getUpgradeCost(), controller.getGame().getVillage().getResource().getElixir());
                controller.getGame().getVillage().setResource(resource);
                backSwitchCaseFunction(group, cell);
            }
        });
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(event -> backSwitchCaseFunction(group, cell));
        group.getChildren().add(backView);
    }


    private static void showUpgradeInfo(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView UpgradeCost= getImageView("UpgradeCost.png");
        UpgradeCost.setScaleX(0.6);
        UpgradeCost.setX(UIConstants.INFOMENU_STARTING_X);
        UpgradeCost.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(UpgradeCost);
        makeLabels(group,Integer.toString(cell.getUpgradeCost()),0.32 , 10 ,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> backSwitchCaseFunction(group, cell));
    }

    private static void backSwitchCaseFunction(Group group, Cell cell) {
        switch (cell.getClass().getSimpleName()){
            case "GoldStorage" :
            case "ElixirStorage" :
                makeStorageMenu(group , cell);
                break;
            case "Camp":
                makeCampInfoMenu(group ,cell);
                break;
            case "Cannon":
            case "ArcherTower":
            case "WizardTower":
            case "AirDefence":
            case "Trap":
            case "Wall":
            case "GuardianGiant":
                makeDefencesInfoMenu(group , cell);
                break;
            default:
                makeDefaultInfoMenu(group,cell);
                break;
        }
    }

    private static void showOverallInfo(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView overallInfoInsides= getImageView("OverallInfoInsides.png");
        overallInfoInsides.setX(UIConstants.INFOMENU_STARTING_X);
        overallInfoInsides.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(overallInfoInsides);
        makeLabels(group,Integer.toString(cell.getLevel()),0.36 ,10 ,true);
        makeLabels(group,Integer.toString(cell.getStrength()),0.27 ,10 ,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> backSwitchCaseFunction(group, cell));
    }

    public static void makeBarrackMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        infoView.setOnMouseClicked(event -> {
            makeDefaultInfoMenu(group,cell);
        });
        ImageView BuildSoldiersView = getImageView("BuildSoldiers.png");
        ImageView statusView = getImageView("Status.png");
        statusView.setOnMouseClicked(event -> {
            makeStatusBarracks(group, cell);
        });
        ImageView backView = getImageView("Back.png");
        backView.setOnMouseClicked(event -> {
            backInVillage(group);
        });
        VBox vBox = new VBox(1, infoView, BuildSoldiersView, statusView, backView);
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);

        BuildSoldiersView.setOnMouseClicked(event -> barrackBuildSoldierMenu(group, cell));
    }

    private static void makeStatusBarracks(Group group, Cell cell) {
        makeSideBar(group,false);
        makeLabels(group,controller.getGame().getVillage().showBarracksStatus(),0.2 , 10,false);
        ImageView backView = getImageView("back.png");
        backView.setScaleX(0.5);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight() * UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setOnMouseClicked(backEvent -> makeBarrackMenu(group,cell));
        group.getChildren().add(backView);
    }

    public static void makeBuildingsMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        switch (cell.getClass().getSimpleName()) {
            case "MainBuilding":
                makeMainBuildingMenu(group);
                break;
            case "AirDefence":
            case "ArcherTower":
            case "Cannon":
            case "Trap":
            case "WizardTower":
            case "Wall":
            case "GuardianGiant":
                makeDefencesMenu(group, cell);
                break;
            case "Barrack":
                makeBarrackMenu(group, cell);
                break;
            case "GoldMine":
            case "ElixirMine":
                makeMineMenu(group, cell);
                break;
            case "Camp":
                makeCampMenu(group, cell);
                break;
            case "GoldStorage":
            case "ElixirStorage":
                makeStorageMenu(group, cell);
                break;
        }
    }

    private static void makeDefencesMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView infoView = getImageView("info.png");
        ImageView targetView = getImageView("Target.png");
        ImageView backView = getImageView("Back.png");
        ImageView switchOrientation= getImageView("SwitchOrientation.png");
        switchOrientation.setOnMouseClicked(event -> {
            if(((Wall)cell).getVerticalOrientation()){
                ((Wall)cell).setVerticalOrientation(false);
            }else{
                ((Wall)cell).setVerticalOrientation(true);
            }
        });
        VBox vBox;
        if(cell.getClass().getSimpleName().equals("Wall")){
            vBox = new VBox(infoView,switchOrientation, backView);
        }else {
            vBox = new VBox(infoView, targetView, backView);
        }
        vBox.relocate(UIConstants.BUTTON_STARTING_X, UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
        backView.setOnMouseClicked(event -> {
            backInVillage(group);
        });
        infoView.setOnMouseClicked(event -> makeDefencesInfoMenu(group, cell));
        targetView.setOnMouseClicked(event -> makeDefencesAttackInfoMenu(group, cell));
    }

    private static void backInVillage(Group group) {
        if(AttackMapUI.isReturningFromAttack()) {
            SideBarUI.makeLoadEnemyMapMenu(group);
            Thread gameLogic = new Thread(new PassTurnThread(controller , primaryStage));
            gameLogic.start();
        }else {
            SideBarUI.makeStartingMenu(group,primaryStage);
        }
    }

    private static void makeDefencesInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        File overallInfoFile=new File(ADDRESS+"OverAllInfo.png");
        Image overallInfoImage=new Image(overallInfoFile.toURI().toString());
        ImageView overallInfoView=new ImageView(overallInfoImage);
        File upgradeInfoFile=new File(ADDRESS+"UpgradeInfo.png");
        Image upgradeInfoImage=new Image(upgradeInfoFile.toURI().toString());
        ImageView upgradeInfoView=new ImageView(upgradeInfoImage);
        File attackInfoFile=new File(ADDRESS+"Attack Info.png");
        Image attackInfoImage=new Image(attackInfoFile.toURI().toString());
        ImageView attackInfoView=new ImageView(attackInfoImage);
        File upgradeFile=new File(ADDRESS+"upgrade.png");
        Image upgradeImage=new Image(upgradeFile.toURI().toString());
        ImageView upgradeView=new ImageView(upgradeImage);
        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);

        VBox vBox;
        if(cell.getClass().getSimpleName().equals("Wall")){
            vBox=new VBox(overallInfoView, upgradeInfoView, upgradeView, backView);
        }else {
            vBox = new VBox(overallInfoView, upgradeInfoView, attackInfoView, upgradeView, backView);
        }
        vBox.relocate(50,160);
        group.getChildren().add(vBox);

        backView.setOnMouseClicked(event -> makeDefencesMenu(group , cell));
        overallInfoView.setOnMouseClicked(event -> makeDefencesOverallInfoMenu(group, cell));
        upgradeInfoView.setOnMouseClicked(event -> makeDefencesUpgradeInfoMenu(group, cell));
        attackInfoView.setOnMouseClicked(event -> makeDefencesAttackInfoMenu(group, cell));
        upgradeView.setOnMouseClicked(event -> makeDefencesUpgradeMenu(group, cell));
    }

    private static void makeDefencesUpgradeMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        implementUpgradeBuildings(group, cell);
    }

    private static void makeDefencesAttackInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView attacklInfoInsides= getImageView("attackInfoInsides.png");
        attacklInfoInsides.setX(UIConstants.INFOMENU_STARTING_X);
        attacklInfoInsides.setY(UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(attacklInfoInsides);

        String damage = "";
        if (cell.getClass().getSimpleName().equals("ArcherTower") || cell.getClass().getSimpleName().equals("Cannon") || cell.getClass().getSimpleName().equals("Trap")) {
            damage = "Ground \nunits";
        }
        if (cell.getClass().getSimpleName().equals("AirDefence")) {
            damage = "Flying \nunits";
        }
        if (cell.getClass().getSimpleName().equals("Cannon")) {
            damage = "Ground \nunits";
        }
        if (cell.getClass().getSimpleName().equals("WizardTower")) {
            damage = "Ground & \nFlying units";
        }
        if (cell.getClass().getSimpleName().equals("GuardianGiant")) {
            damage = "Ground \n units";
        }
        makeLabels(group,damage,0.26, -20  , true);
        makeLabels(group,Integer.toString(cell.getDamage()),0.326, 0, true);
        makeLabels(group,Integer.toString(cell.getRange()),0.387, 43 ,true);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        group.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> makeDefencesMenu(group , cell));
    }

    private static void makeDefencesUpgradeInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        showUpgradeInfo(group, cell);
    }

    private static void makeDefencesOverallInfoMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        showOverallInfo(group, cell);
    }

    private static void implementBuildATowerCommand(String buildingName, Group group)  {
        Builder builder = null;
        try {
            builder = controller.getGame().getVillage().findFreeBuilder();
        } catch (NotEnoughFreeBuildersException e) {
            group.getChildren().add(e.getImageView());
            return;
        }
        for (Cell cell : Cell.getCellKinds()) {
            if (cell.getClass().getSimpleName().equals(buildingName)) {
                Class spacialBuilding = cell.getClass();
                try {
                    MapUI.setIsInBuildMenu(true);
                    Cell newCell = (Cell) spacialBuilding.getDeclaredConstructor(int.class, int.class).newInstance(0, 0);
                    int goldCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_GOLD_COST");
                    int elixirCost = Config.getDictionary().get(newCell.getClass().getSimpleName() + "_ELIXIR_COST");
                    makeLabels(group,"Do you want to build\n" + buildingName + "\nfor " + goldCost + " gold and " + elixirCost + " elixir?",0.27 , 10,false);
                    makeLabels(group,"if yes,\nplease select the spot you want\nto build your building on",0.39,  10 , false);
                    ImageView buildView = getImageView("Build.png");
                    buildView.setX(UIConstants.BUTTON_STARTING_X);
                    buildView.setY(Screen.getPrimary().getVisualBounds().getHeight()*0.55);
                    group.getChildren().add(buildView);
                    Builder finalBuilder = builder;
                    buildView.setOnMouseClicked(event -> {
                        controller.getGame().getVillage().getMap()[14][14].getImageView().requestFocus();
                        if (goldCost > controller.getGame().getVillage().getResource().getGold() || elixirCost > controller.getGame().getVillage().getResource().getElixir()) {
                            group.getChildren().add(new NotEnoughResourcesException().getImageView());
                        } else {
                            try {
                                newCell.setY(MapUI.getBuildY());
                                newCell.setX(MapUI.getBuildX());
                                controller.getGame().getVillage().buildTower(newCell);
                                newCell.setWorkingBuilder(finalBuilder);
                                finalBuilder.setOccupationState(true);
                                Resource resource = new Resource(controller.getGame().getVillage().getResource().getGold() - newCell.getGoldCost(), controller.getGame().getVillage().getResource().getElixir() - newCell.getElixirCost());
                                controller.getGame().getVillage().setResource(resource);
                                MapUI.setIsInBuildMenu(false);
                                makeMainBuildingMenu(group);
                            } catch (MarginalTowerException e) {
                                new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                                group.getChildren().add(e.getImageView());
                            } catch (BusyCellException e) {
                                new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                                group.getChildren().add(e.getImageView());
                            }

                        }} );
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void makeLabels(Group group, String message,double yCoefficient , int xCoefficient ,boolean isInfoLabel) {
        Label label=new Label(message);
        if(isInfoLabel) {
            label.relocate(UIConstants.INFO_LABEL_STARTING_X + xCoefficient, Screen.getPrimary().getVisualBounds().getHeight() * yCoefficient);
        }else{
            label.relocate(UIConstants.LABELS_STARTING_X + xCoefficient, Screen.getPrimary().getVisualBounds().getHeight() * yCoefficient);
        }
        label.setTextFill(Color.BROWN);
        label.setFont(Font.font("Papyrus",16));
        group.getChildren().add(label);
    }

    private static void barrackBuildSoldierMenu(Group group, Cell cell) {
        makeSideBar(group,false);
        ImageView archerView = new ImageView(MapUI.getGifsOfTowers().get("ArcherPortrait"));
        archerView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        archerView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView dragonView = new ImageView(MapUI.getGifsOfTowers().get("DragonPortrait"));
        dragonView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        dragonView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView giantView = new ImageView(MapUI.getGifsOfTowers().get("GiantPortrait"));
        giantView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        giantView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView guardianView = new ImageView(MapUI.getGifsOfTowers().get("GuardianPortrait"));
        guardianView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        guardianView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView wallBreakerView = new ImageView(MapUI.getGifsOfTowers().get("WallBreakerPortrait"));
        wallBreakerView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        wallBreakerView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);
        ImageView healerView = new ImageView(MapUI.getGifsOfTowers().get("HealerPortrait"));
        healerView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);
        healerView.setFitHeight(Screen.getPrimary().getVisualBounds().getWidth() / 13);

        addBuildSoldier(group, cell, archerView, "Archer");
        addBuildSoldier(group, cell, dragonView, "Dragon");
        addBuildSoldier(group, cell, giantView, "Giant");
        addBuildSoldier(group, cell, guardianView, "Guardian");
        addBuildSoldier(group, cell, wallBreakerView, "WallBreaker");
        addBuildSoldier(group, cell, healerView, "Healer");

        opacityOnHover(archerView);
        opacityOnHover(dragonView);
        opacityOnHover(giantView);
        opacityOnHover(guardianView);
        opacityOnHover(wallBreakerView);
        opacityOnHover(healerView);

        HBox soldiers1 = new HBox(1,archerView, dragonView);
        HBox soldiers2 = new HBox(1,giantView, guardianView);
        HBox soldiers3 = new HBox(1,healerView, wallBreakerView);

        File backFile=new File(ADDRESS+"Back.png");
        Image backImage=new Image(backFile.toURI().toString());
        ImageView backView=new ImageView(backImage);
        backView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeBarrackMenu(group, cell);
            }
        });

        VBox allSoldiers = new VBox(1, soldiers1, soldiers2, soldiers3, backView);
        allSoldiers.relocate(50, 160);

        group.getChildren().addAll(allSoldiers);
    }

    public static void opacityOnHover(ImageView imageView) {
        imageView.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setOpacity(0.6);
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                imageView.setOpacity(1);
            }
        });
    }

    private static void addBuildSoldier(Group group, Cell cell, ImageView imageView, String name) {
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    implementBuildSoldier(group, cell, name);
                    barrackBuildSoldierMenu(group, cell);
                } catch (NotEnoughResourcesException e) {
                    new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                } catch (NotEnoughCapacityInCampsException e) {
                    new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                } catch (UnavailableSoldierException e) {
                    new Timeline( new KeyFrame(Duration.seconds(2), new KeyValue(e.getImageView().imageProperty(), null))).play();
                    group.getChildren().add(e.getImageView());
                }
            }
        });
    }

    private static void implementBuildSoldier(Group group, Cell cell, String playerChoice) throws NotEnoughResourcesException, NotEnoughCapacityInCampsException, UnavailableSoldierException {
        Barrack barrack = (Barrack) cell;
        HashMap<String, Integer> availableSoldiers = barrack.determineAvailableSoldiers(controller.getGame().getVillage().getResource().getElixir());
        if (availableSoldiers.get(playerChoice) == 0) {
            throw new UnavailableSoldierException();
        } else {
            int soldierAmount = 1;
            int totalCapacity = 0;
            for (Camp camp : controller.getGame().getVillage().getCamps()) {
                if (!camp.getUnderConstructionStatus())
                    totalCapacity += camp.getCapacity() - camp.getSoldiers().size();
            }
            if (soldierAmount > totalCapacity) {
                throw new NotEnoughCapacityInCampsException();
            }
            barrack.buildSoldier(soldierAmount, playerChoice, availableSoldiers);
            Resource resource = new Resource(controller.getGame().getVillage().getResource().getGold(), controller.getGame().getVillage().getResource().getElixir() - soldierAmount * Config.getDictionary().get(playerChoice + "_ELEXIR_COST"));
            controller.getGame().getVillage().setResource(resource);
        }
    }
}
