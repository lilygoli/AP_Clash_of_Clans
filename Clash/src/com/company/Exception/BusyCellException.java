package com.company.Exception;

import com.company.View.View;

public class BusyCellException  extends Exception{
    public void showMessage(){
        View.show("You can’t build this building here.Please choose another cell.");
    }
}
