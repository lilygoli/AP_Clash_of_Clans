package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Exception.InvalidPlaceForSoldiersException;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Camp;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Towers.Cell;
import com.company.Models.Village;
import com.company.Multiplayer.Server;
import com.company.Multiplayer.liveStreamingMessage;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import static com.company.UIs.MapUI.getImageOfBuildings;
import static com.company.UIs.MapUI.isIsInDefense;
import static com.company.UIs.MapUI.putBuildingImageInMap;
import static com.company.UIs.SideBarUI.*;

public class AttackMapUI {
    public static DatagramSocket udpSocket;
    public static Server server;
    public static Socket clientSocket;
    public static Socket leaderBoardSocket;
    public static ObjectOutputStream clientObjectOutput;
    public static ObjectInputStream clientObjectInput;
    public static InetAddress attackedIP;
    public static ObjectOutputStream leaderBoardOutput;
    public static ObjectInputStream leaderBoardInput;
    public static String attackedPort;
    public static String clientName;
    private static final String ADDRESS = "./src/com/company/UIs/SideBarMenuImages/";
    private static int attackX, attackY;
    public static PannableCanvas canvas = new PannableCanvas();
    private static Controller controller;

    private static Stage primaryStage;

    private static HashMap<String ,Image> soldiersGif=new HashMap<>();
    private static String chosenSoldierName = "";
    private static Label winningLabel=new Label("");
    private static boolean returningFromAttack = false;

    public static HashMap<String, Image> getSoldiersGif() {
        return soldiersGif;
    }

    public static Controller getController() {
        return controller;
    }

    public static boolean isReturningFromAttack() {
        return returningFromAttack;
    }
    public static void setReturningFromWar(boolean war){
        returningFromAttack = war;
    }

    static {
        for (Soldier soldier : Soldier.getSoldierSubClasses()) {
            File moveDownFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Move/Down.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "MoveDown", new Image(moveDownFile.toURI().toString()));
            File moveUpFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Move/Up.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "MoveUp", new Image(moveUpFile.toURI().toString()));
            File moveRightFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Move/Right.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "MoveRight", new Image(moveRightFile.toURI().toString()));
            File moveLeftFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Move/Left.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "MoveLeft", new Image(moveLeftFile.toURI().toString()));

            File attackDownFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Attack/Down.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "AttackDown", new Image(attackDownFile.toURI().toString()));
            File attackUpFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Attack/Up.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "AttackUp", new Image(attackUpFile.toURI().toString()));
            File attackRightFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Attack/Right.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "AttackRight", new Image(attackRightFile.toURI().toString()));
            File attackLeftFile = new File("./src/com/company/ImagesAndGifs/Soldiers/" + soldier.getClass().getSimpleName() + "/Attack/Left.gif");
            soldiersGif.put(soldier.getClass().getSimpleName() + "AttackLeft", new Image(attackLeftFile.toURI().toString()));


        }

    }


    public static void makeAttackGameBoard( Stage stage, Controller controller) throws FileNotFoundException {
        AttackMapUI.controller = controller;
        primaryStage = stage;
        Group root = new Group();
        winningLabel.setFont(Font.font("Papyrus", FontWeight.BOLD,15));
        winningLabel.setTextFill(Color.NAVY);
        winningLabel.relocate(270,80);
        root.getChildren().add(winningLabel);
        Scene scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        primaryStage.setScene(scene);
        File file = new File("./src/com/company/UIs/MapResources/MapBackGroundAttack.jpg");
        Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
        ImageView backGroundView = new ImageView(backGround);
        backGroundView.setOpacity(0.7);
        root.getChildren().add(backGroundView);
        Random random = new Random();
        boolean flag;
        FileInputStream fileInputStream;

        for (int i = 33; i >= 0; i--) {
            for (int j = 0; j < 32; j++) {
                flag = random.nextBoolean();
                fileInputStream = makeGrasses(flag, i, j);
                Image image = new Image(fileInputStream);
                ImageView imageView = new ImageView(image);
                imageView.relocate(scene.getWidth() - ((i + 1) * Screen.getPrimary().getVisualBounds().getHeight() / 32), j * Screen.getPrimary().getVisualBounds().getHeight() / 32);
                imageView.setFitHeight(scene.getHeight() / 32);
                imageView.setFitWidth(scene.getHeight() / 32);
                canvas.getChildren().add(imageView);

                int finalI = i;
                int finalJ = j;
                imageView.setOnMouseClicked(event -> {
                    attackX = finalI - 2;
                    attackY = finalJ - 1;
                    if(!(attackX==29 || attackX==0 || attackY==29 || attackY==0) || attackX<0 || attackX>29 || attackY<0 || attackY>29){
                        InvalidPlaceForSoldiersException exception = new InvalidPlaceForSoldiersException();
                        new Timeline(new KeyFrame(Duration.seconds(4), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                        root.getChildren().add(exception.getImageView());

                    }else
                    if (!chosenSoldierName.equals("")) {
                        for (Soldier soldier : controller.getGame().getTroops()) {
                            if (soldier.getClass().getSimpleName().equals(chosenSoldierName) && soldier.getX() == -1 && !MapUI.isIsInDefense()) {
                                putSoldiersImageInMap(attackY, attackX, 32, canvas, soldiersGif.get(chosenSoldierName + "MoveUp"), soldier, root);

                                liveStreamingMessage lsm = new liveStreamingMessage();
                                lsm.setSoldier(soldier);
                                ArrayList<Integer> healths = new ArrayList<>();
                                for (int r = 0; r <30 ; r++) {
                                    for (int t = 0; t <30 ; t++) {
                                        healths.add(MapUI.getController().getGame().getVillage().getMap()[r][t].getStrength());
                                    }
                                }
                                lsm.setHealths(healths);
                                ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                                try {
                                    ObjectOutput oo = new ObjectOutputStream(bStream);
                                    oo.writeObject(lsm);
                                    oo.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                byte[] buf = bStream.toByteArray();
                                DatagramPacket gamePacket = new DatagramPacket(buf, buf.length, AttackMapUI.attackedIP, 12346);
                                try {
                                    AttackMapUI.udpSocket.send(gamePacket);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                break;
                            }
                        }
                    }
                });
            }
        }


        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (controller.getGame().isUnderAttackOrDefense()) {
                    if (controller.getGame().isWarFinished()) {
                        controller.getGame().healAfterWar();
                        controller.getGame().setUnderAttackOrDefense(false);
                        controller.getGame().getAttackedVillage().setUnderAttackOrDefense(false);
                        winningLabel.setText("*war ended with " + controller.getGame().getVillage().getGainedResource().getGold() + "gold and\n" + controller.getGame().getVillage().getGainedResource().getElixir() + " elixir and " + controller.getGame().getVillage().getScore() + "scores achieved");
                        if (!isIsInDefense()) {
                            SideBarUI.allGainedGoldsResources += controller.getGame().getVillage().getGainedResource().getGold();
                            SideBarUI.allGainedElixirResources += controller.getGame().getVillage().getGainedResource().getElixir();
                        }
                        isIsInDefense(false);
                        returnToVillageUI();
                    }
                }
            }

        }.start();

        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        root.getChildren().add(canvas);
        makeAttackStartingSideBar(root);
        showMapInAttack(root);


    }

    private static void makeAttackStartingSideBar(Group group) {
        SideBarUI.makeSideBar(group,true);
        ImageView attackMap= getImageView("AttackMap.png");
        attackMap.setOnMouseClicked(event -> {
//            MainMenuUI.getGameLogic().stop();
//            controller.getGame().setUnderAttackOrDefense(true);

//            Task task = new Task() {
//                @Override
//                protected Object call() throws Exception {
//                    MainMenuUI.getGameLogic().start();
//                    return null;
//                }
//            };
//            new Thread(task).start();
            addTroops();

            showAttackSideBar(group);
        });
        ImageView back= getImageView("Back.png");
        back.setOnMouseClicked(event -> returnToVillageUI());
        VBox vBox= new VBox(attackMap,back);
        vBox.relocate(UIConstants.BUTTON_STARTING_X,UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void showAttackSideBar(Group group) {
        makeSideBar(group , true);
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

        addClickListener(archerView,  "Archer");
        addClickListener(dragonView,  "Dragon");
        addClickListener(giantView,  "Giant");
        addClickListener(guardianView, "Guardian");
        addClickListener(wallBreakerView,  "WallBreaker");
        addClickListener(healerView, "Healer");

        HBox label1 = new HBox(100, makeTroopsLabel(group, "Archer"), makeTroopsLabel(group, "Dragon"));
        HBox label2 = new HBox(100, makeTroopsLabel(group, "Giant"), makeTroopsLabel(group, "Guardian"));
        HBox label3 = new HBox(100, makeTroopsLabel(group, "Healer"), makeTroopsLabel(group, "WallBreaker"));

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
        backView.setOnMouseClicked(event -> {
            controller.getGame().healAfterWar();
            controller.getGame().setUnderAttackOrDefense(false);
            returnToVillageUI();
        });

        VBox allSoldiers = new VBox(1, soldiers1, label1, soldiers2, label2, soldiers3, label3, backView);
        allSoldiers.relocate(50, 160);

        group.getChildren().addAll(allSoldiers);
    }

    public static void returnToVillageUI() {

        try {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++) {
                    controller.getGame().getVillage().getMap()[j][i].setIsEventSet(false);
                }
            }
            UIConstants.DELTA_T=1000;
            MapUI.getShowMapAnimationTimer().stop();
            clientsComboBox.getItems().clear();
            if(SideBarUI.isInSinglePlayer){
                SideBarUI.isInSinglePlayer = false;
            }else {
                returningFromAttack = true;
            }
            MapUI.start(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTroops() {
        if (controller.getGame().getTroops() == null){
            ArrayList<Soldier> troops = new ArrayList<>();
            controller.getGame().setTroops(troops);
        }
        controller.getGame().setUnderAttackOrDefense(true);
        for (Camp camp : controller.getGame().getVillage().getCamps()) {
            controller.getGame().getTroops().addAll(camp.getSoldiers());
        }
    }

    private static Label makeTroopsLabel(Group group, String name) {
        Label label = new Label("X" + Integer.toString(findNumberOfTroops(name)));
        label.setFont(Font.font("Papyrus"));
        return label;
    }

    private static void addClickListener(ImageView imageView, String name) {

        imageView.setOnMouseClicked(event -> chosenSoldierName = name);
    }

    private static int findNumberOfTroops(String name) {
        int counter = 0;
        if (controller.getGame().getTroops() == null) {
            return 0;
        }
        synchronized (controller.getGame().getTroops()) {
            for (Soldier soldier : controller.getGame().getTroops()) {
                if (soldier.getClass().getSimpleName().equals(name) && soldier.getX() == -1 && soldier.getY() == -1) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public static void showMapInAttack(Group root) {
        Village village = controller.getGame().getAttackedVillage().getVillage();

        int flag = 0;
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (village.getMap()[j][i].getClass() == Grass.class) {
                    continue;
                } else if (village.getMap()[j][i].getClass() == MainBuilding.class) {
                    System.out.println("x main building"+j+"y"+i);
                    if (flag == 0) {
                        flag = 1;
                        village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png" , true));
                        if(village.getMap()[j][i].isRuined()){
                            root.getChildren().remove(village.getMap()[j][i].getImageView());
                        }else if(!village.getMap()[j][i].getEventSet()){
                            setOnClickImages(14, 14, root);
                            village.getMap()[j][i].setIsEventSet(true);
                        }
                        putBuildingImageInMap(i, j, village,16, canvas,0);
                    }

                } else {
                    if(village.getMap()[j][i].isRuined()){
                        root.getChildren().remove(village.getMap()[j][i].getImageView());
                    }else {
                        village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png" , true));
                        if(!village.getMap()[j][i].getEventSet()){
                            AttackMapUI.setOnClickImages(i, j, root);
                            village.getMap()[j][i].setIsEventSet(true);
                        }
                        putBuildingImageInMap(i, j, village,32, canvas,-1);
                    }
                }
            }
        }
    }

    public static void setOnClickImages(int i, int j, Group root) {
        controller.getGame().getAttackedVillage().getVillage().getMap()[j][i].getImageView().setOnMouseClicked(event -> {
            controller.getGame().getAttackedVillage().getVillage().getMap()[j][i].getImageView().requestFocus();
            Cell tower = controller.getGame().getAttackedVillage().getVillage().getMap()[j][i];
            makeStatusTowerSidebar(root, tower);
        });

    }

    private static void makeStatusTowerSidebar(Group root, Cell tower) {
        SideBarUI.makeSideBar(root, true);
        makeLabels(root, tower.getClass().getSimpleName(), 0.2, 20, false);
        makeLabels(root, "Level : " + tower.getLevel(), 0.27, 20, false);
        makeLabels(root, "Health : " + tower.getStrength(), 0.36, 20, false);
        ImageView backView = getImageView("Back.png");
        backView.setX(UIConstants.BUTTON_STARTING_X);
        backView.setY(Screen.getPrimary().getVisualBounds().getHeight()*UIConstants.BACK_BUTTON_Y_COEFFICIENT);
        root.getChildren().add(backView);
        backView.setOnMouseClicked(backEvent -> {
            showAttackSideBar(root);
        });
    }

    public static FileInputStream makeGrasses(boolean flag, int i, int j) throws FileNotFoundException {
        FileInputStream fileInputStream;
        if (i <= 1 || i >= 32 || j == 0 || j == 31){
            fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/Brown.png");
        }
        else if (flag){
            fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/darkGreen.png");
        }
        else{
            fileInputStream = new FileInputStream("./src/com/company/ImagesAndGifs/lightGreen.png");
        }
        return fileInputStream;
    }

    public static void putSoldiersImageInMap(int i, int j, int size, PannableCanvas canvas, Image image, Soldier soldier,Group group) {
        soldier.getImageView().setImage(image);
//        soldier.getImageView().setX(MapUI.mapCoordinates2PixelX(j));
//        soldier.getImageView().setY(mapCoordinates2PixelY(i));
        soldier.getImageView().setFitWidth(Screen.getPrimary().getVisualBounds().getHeight() / size);
        soldier.getImageView().setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() / size);
        soldier.getAllHealth().setWidth(Screen.getPrimary().getVisualBounds().getHeight() / size);
        if (canvas.getChildren().contains(soldier.getImageView())) {
            canvas.getChildren().remove(soldier.getImageView());
        }
        System.out.println("gotten x o y"+j+" "+i);
        soldier.setX(j);
        soldier.setY(i);

        canvas.getChildren().add(soldier.getImageView());
        canvas.getChildren().add(soldier.getAllHealth());
        canvas.getChildren().add(soldier.getLeftHealth());

        updateHealthBarAndArrows();
        showAttackSideBar(group);
    }
    public static void updateHealthBarAndArrows(){
        new AnimationTimer() {
            HashMap<ImageView,Soldier> arrows= new HashMap<>();
            HashMap<ImageView,LineTo>  paths= new HashMap<>();
            HashMap<Circle,Soldier> healCircles= new HashMap<>();
            HashMap <Circle,LineTo> circlePaths= new HashMap<>();
            @Override
            public void handle(long now) {
                addArcherArrows();
                if(controller.getGame().isWarFinished()){
                    super.stop();
                }
            }


            private void addArcherArrows() {
                Iterator<ImageView> iterator= arrows.keySet().iterator();
                while (iterator.hasNext()) {
                    ImageView imageView= iterator.next();
                    if(!controller.getGame().getTroops().contains(arrows.get(imageView))){
                        canvas.getChildren().remove(imageView);
                    }
                    if(!(MapUI.mapCoordinates2PixelX(arrows.get(imageView).getTarget().getX())+12==paths.get(imageView).getX() && MapUI.mapCoordinates2PixelY(arrows.get(imageView).getTarget().getY())+12==paths.get(imageView).getY())){
                        System.out.println("target"+MapUI.mapCoordinates2PixelX(arrows.get(imageView).getTarget().getX())+12+"image"+paths.get(imageView).getX());
                        canvas.getChildren().remove(imageView);
                        iterator.remove();
                    }
                }
                for (int i=0;i<controller.getGame().getTroops().size();i++) {
                    Soldier soldier=controller.getGame().getTroops().get(i);
                    if(soldier.getClass().getSimpleName().equals("Archer")){
                        if(soldier.getTarget()!=null && soldier.hasReachedDestination(soldier.getTarget())){
                            if(!arrows.values().contains(soldier)) {
                                ImageView arrow = new ImageView(MapUI.getImageOfBuildings("Arrow", ".png", false));
                                arrow.setScaleX(0.2);
                                arrow.setScaleY(0.2);
                                arrows.put(arrow, soldier);
                                paths.put(arrow,makePath(soldier,arrow,1));

                            }
                        }
                    }
                }
            }

            private LineTo makePath(Soldier soldier,Node node,double rate) {
                Path path = new Path();
                MoveTo moveTo = new MoveTo();
                moveTo.setX(MapUI.mapCoordinates2PixelX(soldier.getX()) + 12);
                moveTo.setY(MapUI.mapCoordinates2PixelY(soldier.getY()) + 12);
                LineTo lineTo = new LineTo();
                lineTo.setX(MapUI.mapCoordinates2PixelX((soldier).getTarget().getX()) + 12);
                lineTo.setY(MapUI.mapCoordinates2PixelY((soldier).getTarget().getY())+12);
                path.getElements().add(moveTo);
                path.getElements().add(lineTo);
                PathTransition pathTransition = new PathTransition();
                pathTransition.setCycleCount(Animation.INDEFINITE);
                pathTransition.setDuration(Duration.INDEFINITE);
                pathTransition.setNode(node);
                pathTransition.setPath(path);
                pathTransition.setRate(rate);

                pathTransition.setOrientation(PathTransition.OrientationType.
                        ORTHOGONAL_TO_TANGENT);
                pathTransition.play();
                canvas.getChildren().add(node);
                return lineTo;
            }


        }.start();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                ArrayList<Rectangle> rectangles=new ArrayList<>();
                for (Soldier soldier : controller.getGame().getTroops()) {
                    rectangles.add(soldier.getLeftHealth());
                    rectangles.add(soldier.getAllHealth());
                }
                ArrayList<Node> removedNodes=new ArrayList<>();
                for (Node node : canvas.getChildren()) {
                    if(node.getClass()== Rectangle.class){
                        if(!rectangles.contains((Rectangle)node)){
                            removedNodes.add(node);
                        }
                    }
                }
                canvas.getChildren().removeAll(removedNodes);
                if(controller.getGame().isWarFinished()){
                    super.stop();
                }
            }
        }.start();
    }


    public static Label getWinningLabel() {
        return winningLabel;
    }
}
