package com.company.Exception;

import com.company.View;

public class MarginalTowerException extends Exception {
    public void showMessage(String message){
        View.show("You can’t build this building here.Please choose another cell.");
    }
}
