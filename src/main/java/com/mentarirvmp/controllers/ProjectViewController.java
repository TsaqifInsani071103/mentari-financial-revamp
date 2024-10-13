package com.mentarirvmp.controllers;


import com.mentarirvmp.utils.LoaderHelper;
// import com.mentari.utils.Project;
// import com.mentari.utils.expenseoperations.Expenses;
// import com.mentari.utils.fileoperations.DialogControl;
// import com.mentari.utils.viewcreators.ProjectContextMenuHandler;
import com.mentarirvmp.controllers.ChildControllers;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseButton;

public class ProjectViewController extends ChildControllers{
  public static int projectNumber = 0; 
  //constructor method, fires upon view display
  @FXML
  private GridPane gridPaneContainer; 

  @FXML
  private void addAProject(){
    // projectNumber++; 
    // Project newProject = new Project("Project"+ projectNumber); 
    // mainController.getProjectArrayFromModel().add(newProject);
    // postInitialization(); 
  }

  //no access to fxml elements in the constructor method. 
  public ProjectViewController(){
  
  } 

  private MainLayoutController mainController; 


  @Override
  public void setMainController(MainLayoutController mainController){
    this.mainController = mainController; 
  } 

  @FXML
  public void initialize(){
    //CLEAR THE GRID PANE BEFORE DYNAMICALLY ADDING MORE BUTTONS 
    //gridPaneContainer.getChildren().clear(); 
  } 

  public MainLayoutController getMainController(){
    return this.mainController;
  } 

  @Override
  public void postInitialization(){
    // gridPaneContainer.setVgap(10);
    // gridPaneContainer.setHgap(10);
    // projectNumber = 0; 
    // int row = 0; 
    // int column = 0; 
    // for(Project project : mainController.getProjectArrayFromModel()){
    //   projectNumber++; 
    //   if(row >= 4){
    //     row = 0;
    //     column++; 
    //   }
    //   gridPaneContainer.add(createProjectButton(project), column, row++); 
    // }
  } 

  // private Button createProjectButton(Project project){
  //   Button button = new Button(project.getProjectName()); 
  //   setListenerOnProject(button, project);
  //   setContextMenuOnButton(button, project);
  //   //set its own style 
  //   button.getStyleClass().add("project-button"); 
  //   return button; 

  // } 

  // private void setContextMenuOnButton(Button button, Project project){
  //   ContextMenu contextMenu = new ProjectContextMenuHandler(project, this).getProjectContextMenu();

  //   button.setOnMouseClicked(event -> {
  //     if (event.getButton() == MouseButton.SECONDARY) {
  //         contextMenu.show(button, event.getScreenX(), event.getScreenY());
  //     }
  //   });
  // } 

  // public void refreshView(){
  //   gridPaneContainer.getChildren().clear();
  //   postInitialization(); 
  // } 

  // private void setListenerOnProject(Button button, Project project){
  //   button.setOnAction(event -> {
  //     try {
  //       handleProjectButtonAction(project);
  //     } catch (IOException e) {
  //       e.printStackTrace();
  //     }
  //   });
  // }

  // //get view according to projects. also dont forget to add the statements for EVERY PROJECT. 
  // private void handleProjectButtonAction(Project project) throws IOException{
  //     LoaderHelper hub = new LoaderHelper("/com/mentari/indivProject.fxml"); 
  //     IndivProjectViewController projectController = (IndivProjectViewController) hub.getController(); 

  //     projectController.setProjectObject(project);
  //     projectController.setMainController(mainController);
  //     mainController.switchView(hub.getNextView());
  //     projectController.postInitialization();
  // }

  


  // //make the buttons according to the PROJECTS!!! 

}
