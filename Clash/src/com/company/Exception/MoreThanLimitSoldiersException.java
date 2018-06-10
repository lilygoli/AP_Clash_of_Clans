package com.company.Exception;

import com.company.View.View;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;

import java.io.File;

public class MoreThanLimitSoldiersException extends Exception {
    ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void showMessage(){
     File file = new File("./src/com/company/UIs/SideBarMenuImages/MoreThanLimitSoldiersException.png");
     Image backGround = new Image(file.toURI().toString(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight(), false, true);
     imageView = new ImageView(backGround);
     imageView.relocate(50 , 55 * Screen.getPrimary().getVisualBounds().getHeight() / 100);
 }
}
