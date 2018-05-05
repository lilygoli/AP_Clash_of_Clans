package com.company.Exception;

import com.company.View.View;

public class MoreThanLimitSoldiersException extends Exception {
 public void showMessage(){
     View.show("You can only put 5 of this soldier type in this place.please select another place");
 }
}
