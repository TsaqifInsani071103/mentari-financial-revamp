package com.mentarirvmp.utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.mentarirvmp.App;



public class LoaderHelper {
  public static String WARNING_MODAL_PATH = "/com/mentari/modals/warningModal.fxml";
  public static String CLOSE_MODAL_PATH = "/com/mentari/modals/confirmClose.fxml";
  
  private FXMLLoader loader; 
  private Parent nextView; 
  private ChildControllers controller; 
  private Stage stage; 
  
  public LoaderHelper(String path){
    initialize(path); 
  } 

  private void initialize(String path){
    try{
      this.stage = new Stage(); 
      this.loader = new FXMLLoader(App.class.getResource(path));
      this.nextView = loader.load(); 
      this.controller = loader.getController(); 
    }catch(Exception e){
      e.printStackTrace();
    }
  } 

  public ChildControllers getController(){
    return this.controller; 
  } 

  public Parent getNextView(){
    return this.nextView; 
  } 

  public FXMLLoader getLoader(){
    return this.loader; 
  } 


  public Stage getStage(){
    return this.stage;
  } 


  //showing modal 
  public void showModal(Parent nextView, int width, int height){
    this.stage.initModality(Modality.APPLICATION_MODAL);
    Scene scene = new Scene(nextView, width, height);
    this.stage.setScene(scene); 
    this.stage.showAndWait(); 
  } 


  // public static void showPopupModal(String message,int width, int height){
  //   LoaderHelper hub = new LoaderHelper(LoaderHelper.WARNING_MODAL_PATH);
  //   WarningModalController modalController = (WarningModalController) hub.getController(); 
  //   modalController.setWarningMessage(message);
  //   hub.showModal(hub.getNextView(), width, height);
  // } 


  

  
}
