package com.company.Exception;

import com.company.View;

public class NotEnoughSoldierInTroopsException extends Exception {
    public void showMessage(){
        View.show("you don't have enough of these soldiers in your troops.please choose another soldier type");
    }
}
