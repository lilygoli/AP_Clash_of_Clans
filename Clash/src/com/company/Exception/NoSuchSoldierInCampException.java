package com.company.Exception;

import com.company.View;

public class NoSuchSoldierInCampException extends Exception{
    public void showMessage(String unitType) {
        View.show("Not enough " + unitType + " in Camps");
    }
}
