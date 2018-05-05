package com.company.Exception;

import com.company.View.View;

public class unAvailableSoldierException extends Exception {
    public void showMessage(){
        View.show("You can't build this soldier.");
    }
}
