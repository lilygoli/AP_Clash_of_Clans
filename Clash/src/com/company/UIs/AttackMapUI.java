package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Exception.InvalidPlaceForSoldiersException;
import com.company.Models.Soldiers.Archer;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Camp;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Village;
import javafx.animation.*;
import javafx.event.EventHandler;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.company.UIs.MapUI.getImageOfBuildings;
import static com.company.UIs.MapUI.putBuildingImageInMap;
import static com.company.UIs.SideBarUI.makeSideBar;
import static com.company.UIs.SideBarUI.opacityOnHover;

public class AttackMapUI {
    private static final String ADDRESS = "./src/com/company/UIs/SideBarMenuImages/";
    private static int attackX, attackY;
    private static PannableCanvas canvas = new PannableCanvas();
    private static Controller controller;
    private static Stage primaryStage;
    private static HashMap<String ,Image> soldiersGif=new HashMap<>();
    private static String chosenSoldierName = "";
    private static Label winningLabel=new Label("");

    public static HashMap<String, Image> getSoldiersGif() {
        return soldiersGif;
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
                        System.out.println("invalid");
                        InvalidPlaceForSoldiersException exception = new InvalidPlaceForSoldiersException();
                        new Timeline(new KeyFrame(Duration.seconds(4), new KeyValue(exception.getImageView().imageProperty(), null))).play();
                        root.getChildren().add(exception.getImageView());

                    }else if (!chosenSoldierName.equals("")) {
                        for (Soldier soldier : controller.getGame().getTroops()) {
                            if (soldier.getClass().getSimpleName().equals(chosenSoldierName) && soldier.getX() == -1) {
                                putSoldiersImageInMap(attackY, attackX, 32, canvas, soldiersGif.get(chosenSoldierName + "MoveUp"), soldier, root);
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
                        winningLabel.setText("*war ended with " + controller.getGame().getVillage().getGainedResource().getGold() + "gold and\n" + controller.getGame().getVillage().getGainedResource().getElixir() + " elixir and " + controller.getGame().getVillage().getScore() + "scores achieved");
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
        ImageView attackMap= SideBarUI.getImageView("AttackMap.png");
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
        ImageView back= SideBarUI.getImageView("Back.png");
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
        backView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                controller.getGame().healAfterWar();
                controller.getGame().setUnderAttackOrDefense(false);
                returnToVillageUI();
            }
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
        ;
        return label;
    }

    private static void addClickListener(ImageView imageView, String name) {

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                chosenSoldierName = name;
            }
        });
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
                        putBuildingImageInMap(i, j, village,16, canvas);
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
                        putBuildingImageInMap(i, j, village,32, canvas);
                    }
                }
            }
        }
    }

    public static void setOnClickImages(int i, int j, Group root) {
        controller.getGame().getAttackedVillage().getVillage().getMap()[j][i].getImageView().setOnMouseClicked(event -> {
            controller.getGame().getAttackedVillage().getVillage().getMap()[j][i].getImageView().requestFocus();
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
            @Override
            public void handle(long now) {
                addArcherArrows();
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

            private void addArcherArrows() {
                for (ImageView imageView : arrows.keySet()) {
                    if(!controller.getGame().getTroops().contains(arrows.get(imageView))){
                        canvas.getChildren().remove(imageView);
                    }
                }
                for (int i=0;i<controller.getGame().getTroops().size();i++) {
                    Soldier soldier=controller.getGame().getTroops().get(i);
                    if(soldier.getClass().getSimpleName().equals("Archer")){
                        if(((Archer)soldier).getTarget()!=null && soldier.hasReachedDestination(soldier.getTarget())){
                            if(!arrows.values().contains(soldier)) {
                                Path path = new Path();
                                MoveTo moveTo = new MoveTo();
                                moveTo.setX(MapUI.mapCoordinates2PixelX(soldier.getX()) + 12);
                                moveTo.setY(MapUI.mapCoordinates2PixelY(soldier.getY()));
                                LineTo lineTo = new LineTo();
                                lineTo.setX(MapUI.mapCoordinates2PixelX(((Archer) soldier).getTarget().getX())+12);
                                lineTo.setY(MapUI.mapCoordinates2PixelY(((Archer) soldier).getTarget().getY()));
                                path.getElements().add(moveTo);
                                path.getElements().add(lineTo);
                                PathTransition pathTransition = new PathTransition();
                                pathTransition.setCycleCount(10);
                                pathTransition.setDuration(Duration.millis(UIConstants.DELTA_T));
                                ImageView arrow = new ImageView(MapUI.getImageOfBuildings("Arrow", ".png", false));
                                arrow.setScaleX(0.2);
                                arrow.setScaleY(0.2);
                                arrows.put(arrow, soldier);
                                pathTransition.setNode(arrow);
                                pathTransition.setPath(path);

                                //Setting the orientation of the path
                                pathTransition.setOrientation(PathTransition.OrientationType.
                                        ORTHOGONAL_TO_TANGENT);
                                pathTransition.play();
                                canvas.getChildren().add(arrow);
                            }
                        }
                    }
                }
            }
        }.start();
    }

    public static Label getWinningLabel() {
        return winningLabel;
    }
}
