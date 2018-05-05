package com.company.Exception;

import com.company.View.View;

public class InvalidPlaceForSoldiersException extends Exception{
    public void showMessage(){
        View.show("You can't put any soldiers here.please choose another place");
    }
}
