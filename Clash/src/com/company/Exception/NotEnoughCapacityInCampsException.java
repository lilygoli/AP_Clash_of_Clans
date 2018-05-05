package com.company.Exception;

import com.company.View.View;

public class NotEnoughCapacityInCampsException extends Exception {
    public void showMessage(){
        View.show("There is not enough space in camps,please build a new camp or upgrade a camp before making a new soldier.");
    }
}
