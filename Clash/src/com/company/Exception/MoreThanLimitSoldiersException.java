package com.company.Exception;

import com.company.View;

public class MoreThanLimitSoldiersException extends Exception {
 public void showMessage(){
     View.show("You can only put 5 of this soldier type in this place.");
 }
}