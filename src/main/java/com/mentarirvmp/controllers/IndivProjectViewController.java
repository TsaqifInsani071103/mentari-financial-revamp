package com.mentarirvmp.controllers;

import com.mentarirvmp.controllers.ChildControllers;
import com.mentarirvmp.utils.Project;
import com.mentarirvmp.viewcreators.StatementViewCreator;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.LoaderHelper; 

import javafx.scene.control.Button;

import javafx.fxml.FXML;

public class IndivProjectViewController extends ChildControllers {

  private MainLayoutController mainController; 
  private Project projectObject; 
  private Statement currentStatement;  

  @FXML
  private Label projectName; 

  @FXML
  private ScrollPane numbersContainer; 

  @FXML
  private Button editView; 
  @FXML
  private Button addExpensesButton; 

  //there is only one instance of IndivProjectViewController right now, thats why toggle edit is behaving this way. 

  @Override
  public void setMainController(MainLayoutController mainController){
    this.mainController = mainController; 
  } 

  @Override 
  public void postInitialization(){
    setDefaultStatement();
    refreshStatementView();
    // generateFileView();
  } 

  public void setDefaultStatement(){
    this.currentStatement = getFirstStatement();
  }

  //if Folder node is the only node, or is the only FIRST NODE, then it doesnt have an attachedStatement dong 
  private Statement getFirstStatement(){
    return this.projectObject.getFirstStatementNode().getAttachedStatement();
  } 

  public void refreshStatementView(){
    // this.currentStatement.setController(this);
    numbersContainer.setContent(this.currentStatement.getViewCreator().getView());
    setProjectName(); 
  } 

  private void setProjectName(){
    // projectName.setText(projectObject.getProjectName() + " " + projectObject.getNodeByStatement(currentStatement).getName()); 
  } 

  private void generateFileView(){
    LoaderHelper hub = new LoaderHelper("/com/mentari/filingLayout.fxml");
    // FilingLayoutViewController filingController = (FilingLayoutViewController) hub.getController(); 
    // initializeFilingController(filingController);
    // mainController.addView(hub.getNextView());
  } 

  // private void initializeFilingController(FilingLayoutViewController filingController){
  //   filingController.setProjectObject(projectObject);
  //   filingController.setProjectController(this);
  //   filingController.postInitialization(); 
  // } 

  public void setProjectObject(Project project){
    this.projectObject = project; 
  }

  public Project getProjectObject(){
    return this.projectObject;
  } 

  public ScrollPane getContainer(){
    return this.numbersContainer;
  }

  public void changeCurrentStatement(Statement newStatement){
    this.currentStatement = newStatement;
  } 



}

