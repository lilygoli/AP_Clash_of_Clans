package com.company.UIs;

import com.company.Controller.Controller;
import com.company.Models.Soldiers.Soldier;
import com.company.Models.Towers.Buildings.Camp;
import com.company.Models.Towers.Buildings.Grass;
import com.company.Models.Towers.Buildings.MainBuilding;
import com.company.Models.Village;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.company.UIs.MapUI.getImageOfBuildings;
import static com.company.UIs.MapUI.mapCoordinates2PixelY;
import static com.company.UIs.MapUI.putBuildingImageInMap;
import static com.company.UIs.SideBarUI.makeSideBar;
import static com.company.UIs.SideBarUI.opacityOnHover;

public class AttackMapUI {
    private static final String ADDRESS = "./src/com/company/UIs/SideBarMenuImages/";
    private static int attackX, attackY;
    private static PannableCanvas canvas = new PannableCanvas();
    private static Controller controller;
    private static AnimationTimer showEnemyMapAnimationTimer;
    private static Stage primaryStage;
    private static HashMap<String ,Image> soldiersGif=new HashMap<>();
    private static String chosenSoldierName = "";

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
        AttackMapUI.controller=controller;
        primaryStage=stage;
        Group root= new Group();
        Scene scene= new Scene(root,Screen.getPrimary().getVisualBounds().getWidth(),Screen.getPrimary().getVisualBounds().getHeight());
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
                imageView.relocate(scene.getWidth() - ((i + 1) * Screen.getPrimary().getVisualBounds().getHeight() / 32) , j * Screen.getPrimary().getVisualBounds().getHeight() / 32);
                imageView.setFitHeight(scene.getHeight() / 32);
                imageView.setFitWidth(scene.getHeight() / 32);
                canvas.getChildren().add(imageView);

                int finalI = i;
                int finalJ = j;
                imageView.setOnMouseClicked(event -> {
                    attackX = finalI - 2;
                    attackY = finalJ - 1;
                    if (!chosenSoldierName.equals("")){
                        for (Soldier soldier : controller.getGame().getTroops()) {
                            if (soldier.getClass().getSimpleName().equals(chosenSoldierName)  && soldier.getX() == -1){
                                putSoldiersImageInMap(attackY , attackX , 32 , canvas , soldiersGif.get(chosenSoldierName + "MoveUp"), soldier, root);
                                break;
                            }
                        }
                    }
                });
            }

        }
        SceneGestures sceneGestures = new SceneGestures(canvas);
        scene.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        root.getChildren().add(canvas);
        showMapInAttack(root);
        makeAttackStartingSideBar(root);
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

            implementPutUnit(group);
        });
        ImageView back= SideBarUI.getImageView("Back.png");
        back.setOnMouseClicked(event -> {
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
        });
        VBox vBox= new VBox(attackMap,back);
        vBox.relocate(UIConstants.BUTTON_STARTING_X,UIConstants.MENU_VBOX_STARTING_Y);
        group.getChildren().add(vBox);
    }

    private static void implementPutUnit(Group group) {
        makeSideBar(group , true);
        if (controller.getGame().getTroops() == null){
            ArrayList<Soldier> troops = new ArrayList<>();
            controller.getGame().setTroops(troops);
        }
        controller.getGame().setUnderAttackOrDefense(true);
        for (Camp camp : controller.getGame().getVillage().getCamps()) {
            controller.getGame().getTroops().addAll(camp.getSoldiers());
        }
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
                // TODO: 6/13/18 Exit
            }
        });

        VBox allSoldiers = new VBox(1, soldiers1, label1, soldiers2, label2, soldiers3, label3, backView);
        allSoldiers.relocate(50, 160);

        group.getChildren().addAll(allSoldiers);
    }

    private static Label makeTroopsLabel(Group group, String name) {
        Label label = new Label("X" + Integer.toString(numberOfTroops(name)));
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

    private static int numberOfTroops(String name) {
        int counter = 0;
        if (controller.getGame().getTroops() == null) {
            return 0;
        }
        for (Soldier soldier : controller.getGame().getTroops()) {
            if (soldier.getClass().getSimpleName().equals(name)) {
                counter++;
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
                    if (flag == 0) {
                        flag = 1;
                        village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png"));
                        if(!village.getMap()[j][i].getEventSet()){
                            setOnClickImages(14, 14, root);
                            village.getMap()[j][i].setIsEventSet(true);
                        }
                        putBuildingImageInMap(i, j, village,16, canvas);
                    }

                } else {
                    if(village.getMap()[j][i].getUnderConstructionStatus()){
                        village.getMap()[j][i].setImage( getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".gif"));
                        if(!village.getMap()[j][i].getEventSet()){
                            AttackMapUI.setOnClickImages(i, j, root);
                            village.getMap()[j][i].setIsEventSet(true);
                        }
                        putBuildingImageInMap(i, j, village,32, canvas);
                    }else {
                        village.getMap()[j][i].setImage(getImageOfBuildings(village.getMap()[j][i].getClass().getSimpleName(),".png"));
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

    public static void putSoldiersImageInMap(int i, int j, int size, PannableCanvas canvas, Image image, Soldier soldier, Group group) {
        soldier.getImageView().setImage(image);
        soldier.getImageView().setX(MapUI.mapCoordinates2PixelX(j));
        soldier.getImageView().setY(mapCoordinates2PixelY(i));
        //addGlowToBuildings(village.getMap()[j][i].getImageView());
        soldier.getImageView().setFitWidth(Screen.getPrimary().getVisualBounds().getHeight() / size);
        soldier.getImageView().setFitHeight(Screen.getPrimary().getVisualBounds().getHeight() / size);

        if (canvas.getChildren().contains(soldier.getImageView())) {
            canvas.getChildren().remove(soldier.getImageView());
        }

        soldier.setX(j);
        soldier.setY(i);

//        implementPutUnit(group);

        canvas.getChildren().add(soldier.getImageView());
    }
}
