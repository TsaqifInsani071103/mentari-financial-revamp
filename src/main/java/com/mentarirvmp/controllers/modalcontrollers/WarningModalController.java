package com.mentarirvmp.controllers.modalcontrollers;

import com.mentarirvmp.controllers.ChildControllers;
import com.mentarirvmp.controllers.MainLayoutController;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;

public class WarningModalController extends ChildControllers{
  @FXML
  private Label messageLabel; 

  public void setWarningMessage(String message){
    messageLabel.setText(message); 
  } 
  
  @Override 
  public void setMainController(MainLayoutController mainController){
    
  }

  @Override
  public void postInitialization(){

  } 
}
