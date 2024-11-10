package com.mentarirvmp.viewcreators;

//I dont need context menus in homeStatement view Creator 

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.mentarirvmp.utils.DataHandler;
import com.mentarirvmp.controllers.ChildControllers;
import com.mentarirvmp.controllers.IndivProjectViewController;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.ViewCreator;
import com.mentarirvmp.utils.ExpenseStatementHandler;
// import com.mentarirvmp.utils.fileoperations.ModalMenu;


import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;

import javafx.scene.control.MenuItem;


public class StatementViewCreator implements ViewCreator {

  private Statement currentStatement; 
  //always creating a new treeView upon constructing this class 
  private TreeView<Expenses> treeView = new TreeView<>();
  private ChildControllers controller; 


  public StatementViewCreator(Statement currentStatement){
    this.currentStatement = currentStatement;
  }

  @Override
  public Parent getView() {
    return getTreeView();
  }

  //BEWARE OF COUPLING HERE!!! 
  @Override
  public void setParentController(ChildControllers viewController){
    this.controller = (IndivProjectViewController) viewController; 
  } 

  //we get the statement view here in tree structure 
  public TreeView<Expenses> getTreeView(){
    populateTreeView();
    // restoreExpandedState(treeView.getRoot(), currentStatement.expandedState);
    return this.treeView; 
  }

  public void populateTreeView(){
    initializeStatementExpandedState();
    Expenses rootExpense = currentStatement.getRoot(); 
    TreeItem<Expenses> rootItem = createTreeItem(rootExpense);
    createTree(rootItem);
    this.treeView.setRoot(rootItem);
    initializeTreeView();
  } 

  public TreeItem<Expenses> createTreeItem(Expenses expense) {
    checkForUpdatedExpenses(expense);
    TreeItem<Expenses> treeItem = new TreeItem<>(expense);
    treeItem.setGraphic(expense.getViewCreator().getView());
    treeItem.setExpanded(true);
    return treeItem;
  } 
  
  public void createTree(TreeItem<Expenses> parentNode){
    Expenses expense = parentNode.getValue(); 
    for (Map.Entry<String, Expenses> entry : expense.getChildMap().entrySet()) {
      TreeItem<Expenses> childNode =  createTreeItem(entry.getValue());
      parentNode.getChildren().add(childNode); 
      if(entry.getValue().hasChildren()){
        createTree(childNode);
      }
    }
  } 

 





  private void initializeStatementExpandedState(){
    // if(currentStatement.expandedState == null){
    //   currentStatement.expandedState = new HashMap<Expenses, Boolean>();
    // }
    traverseAndAddStates();
  } 

  private void traverseAndAddStates(){
    int[] nth = new int[1]; 
    nth[0] = 0; 
    // if(currentStatement.expandedState.isEmpty()){
    //   currentStatement.traverseExpenses((expense) -> {
    //     putExpandedValue(expense, nth);
    //   });
    // }
  } 

  private void putExpandedValue(Expenses expense, int[] nth){
    if(nth[0] < 1){
      // currentStatement.expandedState.put(expense, true); 
    }else{
      // currentStatement.expandedState.put(expense, false); 
    }
    nth[0]++; 
  } 




  //if there are new expenses basically. 
  private void checkForUpdatedExpenses(Expenses expense){
    // if(currentStatement.expandedState.get(expense) == null){
    //   currentStatement.expandedState.put(expense, false); 
    // }
  } 



  private void initializeTreeView(){
    addExpandCollapseListeners(treeView.getRoot());
    treeView.setCellFactory(tv -> createTreeCell());
  } 

  //this doesnt duplicate the entries, just updates their values. when a user clicks on it so we change the value of whether its expanded or not. 
  private void addExpandCollapseListeners(TreeItem<Expenses> rootItem) {
    rootItem.expandedProperty().addListener((observable, oldValue, newValue) -> {
      if (rootItem != null) {
        // currentStatement.expandedState.put(rootItem.getValue(), rootItem.isExpanded());
      }
    });

    for (TreeItem<Expenses> childItem : rootItem.getChildren()) {
        addExpandCollapseListeners(childItem); // Recursively add listeners to children
    }
  }

  // private void restoreExpandedState(TreeItem<Expenses> item, Map<Expenses, Boolean> stateMap) {
  //   if (item != null && stateMap.containsKey(item.getValue())) {
  //     item.setExpanded(stateMap.get(item.getValue()));
  //     for (TreeItem<Expenses> child : item.getChildren()) {
  //         restoreExpandedState(child, stateMap);
  //     }
  //   }
  // }


  private TreeCell<Expenses> createTreeCell(){
    TreeCell<Expenses> newCell = new TreeCell<Expenses>(){
      @Override
      protected void updateItem(Expenses itemExpense, boolean empty){
        super.updateItem(itemExpense, empty);
        if(empty){
          clearCell(this);
        }else{
          updateCell(this, itemExpense);
        }
      }
    };

    return newCell; 
  } 


  private void clearCell(TreeCell<Expenses> cell) {
    cell.setText(null);
    cell.setGraphic(null);
    cell.setStyle(""); // Reset style
    // reference.setContextMenu(null);
  }


  private void updateCell(TreeCell<Expenses> cell, Expenses item) {
    if (item != null) {
        cell.setGraphic(item.getViewCreator().getView()); // Setting custom view
        // cell.setContextMenu(getContextMenuItems(item));
    }
  }


  // public ContextMenu getContextMenuItems(Expenses expense){
  //   ContextMenu menu = new ContextMenu(); 
  //   if(expense != this.treeView.getRoot().getValue()){
  //     menu.getItems().addAll(changeExpense(expense), addExpense(expense), deleteExpense(expense));
  //   }else{
  //     menu.getItems().add(addExpense(expense)); 
  //   }
  //   return menu; 
  // }

  // private MenuItem changeExpense(Expenses expenseNode){
  //   MenuItem item = new MenuItem("Change this expense's type");
  //   item.setOnAction(event -> {
  //     ModalMenu.changeTypeExpenseModal(expenseNode); 
  //     currentStatement.restartAndRefreshIdCounter();
  //     currentStatement.updateFormulas(); 
  //     currentStatement.updateFormulaTextFields();
  //     this.controller.refreshStatementView();
  //   });
  //   return item; 
  // } 

  // private MenuItem addExpense(Expenses expenseNode){
  //   MenuItem item = new MenuItem("Add new expense here");
  //   item.setOnAction(event -> {
  //     ModalMenu.addExpenseModal(expenseNode, new Runnable(){
  //       @Override
  //       public void run(){
  //         currentStatement.restartAndRefreshIdCounter();
  //         controller.refreshStatementView();
  //       }
  //     }); 
  //   });
  //   return item; 
  // } 

  // private MenuItem deleteExpense(Expenses expenseNode){
  //   MenuItem item = new MenuItem("Delete this expense");
  //   item.setOnAction(event -> {
  //     ModalMenu.deleteExpenseModal(expenseNode); 
  //     currentStatement.restartAndRefreshIdCounter();
  //     this.controller.refreshStatementView();
  //   });
  //   return item; 
  // } 




} 


  

