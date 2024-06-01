package com.mentarirvmp.controllers;


import java.util.ArrayList;
import java.util.HashMap;

// import com.mentari.statements.Statement;
import com.mentarirvmp.utils.ChildControllers;
// import com.mentari.utils.Project;
// import com.mentari.utils.fileoperations.TreeNode;
// import com.mentari.utils.viewcreators.HomeStatementCreator;

import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.Parent;


public class HomeViewController extends ChildControllers {

  // public static class MapPackage{
  //   private HashMap<String, ArrayList<Statement>> nameToStatementArrayMap;
  //   private HashMap<Statement, String> statementToProjectMap;
  //   private HashMap<Statement, String> statementToSheetNameMap;

  //   public MapPackage(HashMap<String, ArrayList<Statement>> nameToStatementArrayMap, HashMap<Statement, String> statementToProjectMap, HashMap<Statement, String> statementToSheetNameMap){
  //     this.nameToStatementArrayMap = nameToStatementArrayMap;
  //     this.statementToProjectMap = statementToProjectMap;
  //     this.statementToSheetNameMap = statementToSheetNameMap;
  //   }

  //   public HashMap<Statement, String> getStatementToProjectMap(){
  //     return this.statementToProjectMap;
  //   }

  //   public HashMap<String, ArrayList<Statement>> getNameToStatementArrayMap(){
  //     return this.nameToStatementArrayMap;
  //   }

  //   public HashMap<Statement, String> getStatementToSheetNameMap(){
  //     return this.statementToSheetNameMap;
  //   }
  // } 

  @FXML
  GridPane projectOverviewContainer;
 
  private MainLayoutController mainController; 
  private int currentRow = 0;
  private int currentColumn = 0; 

  @Override 
  public void postInitialization(){
    // initializeColumnAndRow();
    // getAllStatementsToView(getHashMapsOfStatementTemplates());
  } 

  // private void getAllStatementsToView(MapPackage mapPackage){
  //   mapPackage.getNameToStatementArrayMap().forEach((templateName, statementArray) -> {
  //     HomeStatementCreator creator = new HomeStatementCreator(statementArray, mapPackage);
  //     addView(creator.getTreeView());
  //   });
  // } 
  
  // private MapPackage getHashMapsOfStatementTemplates(){
  //   HashMap<String, ArrayList<Statement>> map = new HashMap<String, ArrayList<Statement>>();
  //   HashMap<Statement, String> statementToProjectMap = new  HashMap<Statement, String>(); 
  //   HashMap<Statement, String> statementToSheetNameMap = new HashMap<Statement, String>(); 

  //   for(Project project: this.mainController.getProjectArrayFromModel()){
  //     project.getNodeTree().traverseTree((node) -> {
  //       if(node.getAttachedStatement() != null){
  //         statementToProjectMap.put(node.getAttachedStatement(), project.getProjectName());
  //         statementToSheetNameMap.put(node.getAttachedStatement(), node.getName());
  //         handleMapInitialization(map, node.getAttachedStatement());
  //       }
  //     });
  //   } 

  //   MapPackage mapPackage = new MapPackage(map, statementToProjectMap, statementToSheetNameMap);
  //   return mapPackage; 
  // } 


  // //makes a hashmap, with key and value that corresponds to Statements that has the same Template. 
  // private void handleMapInitialization(HashMap<String, ArrayList<Statement>> map, Statement statementNode){
  //   Statement statement = statementNode;
  //   ArrayList<Statement> list = new ArrayList<Statement>();
  //   if(map.get(statement.getName()) == null){
  //     list.add(statement);
  //     map.put(statement.getName(), list);
  //   }else{
  //     list = map.get(statement.getName());
  //     list.add(statement);    
  //   }
    
  // } 

  // private void initializeColumnAndRow(){
  //   initializeColumnConstraints(2);
  //   initializeRowConstraints();
  // } 

  // private void initializeColumnConstraints(int numberOfcolumns){
  //   double percentWidth = 100/numberOfcolumns;
  //   for(int i = 0; i < 2; i++){
  //     ColumnConstraints col = new ColumnConstraints();
  //     col.setPercentWidth(percentWidth);
  //     col.setHgrow(Priority.ALWAYS); 
  //     projectOverviewContainer.getColumnConstraints().add(col); 
  //   }
  // } 

  // private void initializeRowConstraints(){
  //   RowConstraints row = new RowConstraints();
  //   row.setVgrow(Priority.ALWAYS);
  //   projectOverviewContainer.getRowConstraints().add(row);
  // } 



  @Override
  public void setMainController(MainLayoutController mainController){
    this.mainController = mainController; 
    //you can get projects array from mainController; 
  } 

  // public void addView(Parent view){
  //   projectOverviewContainer.add(view, currentColumn , currentRow);
  //   currentColumn++; 

  //   if(currentColumn >= 2){
  //     currentColumn = 0; 
  //     currentRow++; 
  //   }
  // }

  // @FXML
  // private Button homeButton; 
  // @FXML
  // private Button projectsButton; 
  // @FXML
  // private Button fundsButton; 

  //make copy expenses so we dont alter the original value of the expense objects 





  
}
