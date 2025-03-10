package com.mentarirvmp.controllers;

import java.util.HashMap;
import java.util.Map;

import com.mentarirvmp.controllers.ChildControllers;
import com.mentarirvmp.utils.Project;
import com.mentarirvmp.utils.ViewCreator;
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

  private Map<Statement, StatementViewCreator> statementToViewMap = new HashMap<>();

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
    this.currentStatement = this.projectObject.getFirstStatement();
  }

  //if Folder node is the only node, or is the only FIRST NODE, then it doesnt have an attachedStatement dong 
  // private Statement getFirstStatement(){
  //   return this.projectObject.getFirstStatement();
  // } 

  public void refreshStatementView(){
    // this.currentStatement.setController(this);
    if(this.statementToViewMap.get(this.currentStatement) == null){
      StatementViewCreator viewCreator = new StatementViewCreator(this.currentStatement);
      this.statementToViewMap.put(this.currentStatement, viewCreator); 
      viewCreator.setParentController(this);
      numbersContainer.setContent(viewCreator.getView());
    }else{
      numbersContainer.setContent(this.statementToViewMap.get(this.currentStatement).getView());
    }
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

