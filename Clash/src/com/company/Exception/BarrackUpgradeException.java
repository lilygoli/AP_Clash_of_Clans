package com.company.Exception;

import com.company.View;

public class BarrackUpgradeException extends Exception {
    public void showMessage(){
        View.show("You can not upgrade this barrack anymore until you upgrade your mainBuilding.");
    }
}
