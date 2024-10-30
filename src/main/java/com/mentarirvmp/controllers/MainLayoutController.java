package com.mentarirvmp.controllers;


import javafx.fxml.FXML;

import com.mentarirvmp.utils.LoaderHelper;



import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import com.mentarirvmp.utils.Project;

import java.util.ArrayList; 
import com.mentarirvmp.controllers.ChildControllers;


public class MainLayoutController {

  private String fxmlPath = "/com/mentarirvmp/"; 
  // private Model database; 
  private Stage stage; 

  public MainLayoutController(){
    // database = dataWriter.getModelFromJson(); 
  } 

  public void setStage(Stage stage){
    this.stage = stage; 
    // stage.setOnCloseRequest(event ->{
    //   showConfirmCloseModal();
    //   event.consume(); 
    // });
  }

  // private void showConfirmCloseModal(){
  //   LoaderHelper helper = new LoaderHelper(LoaderHelper.CLOSE_MODAL_PATH);
  //   ConfirmCloseController controller = (ConfirmCloseController) helper.getController();
  //   controller.setMainController(this);
  //   controller.setStage(helper.getStage());
  //   helper.showModal(helper.getNextView(), 300, 300);
  // } 

  public void closeApplication(){
    this.stage.close(); 
  } 

  @FXML
  private HBox mainBox; 


  @FXML 
  public void navigateToProjects(){
    // Assuming this handler is for a button defined in FXML
    try{
      loadView("projects.fxml"); 
    }catch(Exception e){
      e.printStackTrace(); 
    }
  } 

  @FXML 
  public void navigateToHome(){
    // Assuming this handler is for a button defined in FXML
    try{
      loadView("home.fxml"); 
    }catch(Exception e){
      e.printStackTrace(); 
    }
  } 

  @FXML 
  public void navigateToFunds(){
    try{
      loadView("projects.fxml"); 
    }catch(Exception e){
      e.printStackTrace(); 
    }
  } 

  // @FXML
  // public boolean saveData(){
  //   if(dataWriter.writeAllProject(database)){
  //     LoaderHelper.showPopupModal("Data updated!", 150, 150);
  //     return true; 
  //   }else{
  //     LoaderHelper.showPopupModal("Data failed to update!", 150, 150);
  //     return false; 
  //   }
  // }  


  public void loadView(String fxmlName) {
    LoaderHelper hub = new LoaderHelper(this.fxmlPath + fxmlName);
    ChildControllers controller = hub.getController(); 
    controller.setMainController(this);
    controller.postInitialization();
    switchView(hub.getNextView()); 
  }

  public void switchView(Parent nextView){
    if (mainBox.getChildren().size() > 1) {
      // Retain the first child and remove all others
      mainBox.getChildren().retainAll(mainBox.getChildren().get(0));
    }
    HBox.setHgrow(nextView, Priority.ALWAYS);
    addView(nextView);
  } 

  public void addView(Parent addedView){
    mainBox.getChildren().add(addedView); 
  } 



  // //METHODS TO ACCESS THE MODEL METHODS AND PASS THEM INTO CHILDREN CONTROLLERS 
  public ArrayList<Project> getProjectArrayFromModel(){
    // return this.database.getProjectsArray(); 

    //DUMMY TEMPORARY ARRAY 
    ArrayList<Project> temporaryArrayList = new ArrayList<>();
    temporaryArrayList.add(new Project("dummyProject1"));
    return temporaryArrayList; 

    
  } 
  

  //ADD HOW TO ACCESS THE TREE STRUCTURE HERE!! 
  
}
