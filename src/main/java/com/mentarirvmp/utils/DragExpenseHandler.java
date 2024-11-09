package com.mentarirvmp.utils;
import java.util.HashMap;

import com.mentarirvmp.controllers.IndivProjectViewController;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Expenses;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DragExpenseHandler {
  private TreeCell<Expenses> cell; 
  private IndivProjectViewController controller; 
  private Statement currentStatement; 
  private TreeView<Expenses> treeView; 


  
  public DragExpenseHandler(TreeCell<Expenses> cell, TreeView<Expenses> treeView){
    this.cell = cell; 
    this.treeView = treeView;
  } 

  public void setProjectControllerAndStatement(IndivProjectViewController controller, Statement currentStatement){
    this.controller = controller; 
    this.currentStatement = currentStatement;

  } 

  public void setDragListener(TreeCell<Expenses> cell){
    cell.setOnDragDetected((event) -> {
      if(cell.getItem() == null) return;  
      if(cell.getItem() == treeView.getRoot().getValue()) return; 
      onDragDetected(cell, event);
    });
    cell.setOnDragOver(event -> {
      if(cell.getItem() == null) return;  
      if(cell.getItem() == treeView.getRoot().getValue()) return; 
      onDragOver(cell, event);
  });
    cell.setOnDragDropped(event -> {
      if(cell.getItem() == null) return;  
      if(cell.getItem() == treeView.getRoot().getValue()) return; 
      onDragDropped(cell, event);
  });
    cell.setOnDragExited(event -> {
      cell.setStyle("");
    });

  } 

  private void onDragDetected(TreeCell<Expenses> cell, MouseEvent event){
    makeDbOnDetected(cell, event);
  } 

  private void makeDbOnDetected(TreeCell<Expenses> cell, MouseEvent event){
    Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
    ClipboardContent content = new ClipboardContent();
    content.putString(cell.getItem().getName()); 
    db.setContent(content); 
    db.setDragView(cell.snapshot(null,null));
    event.consume(); 
  }

    private void onDragOver(TreeCell<Expenses> cell, DragEvent event){
    Dragboard db = event.getDragboard();
    if(event.getGestureSource() != cell && db.hasString()){
      handleHoverMeasurements(cell, event);
      event.acceptTransferModes(TransferMode.MOVE);
    }
    event.consume(); 
  }

  private void handleHoverMeasurements(TreeCell<Expenses> cell, DragEvent event){
    double y = event.getY();
    double cellHeight = cell.getBoundsInLocal().getHeight(); 
    if (y < cellHeight * 0.33) {
        cell.setStyle("-fx-border-color: blue; -fx-border-width: 2 0 0 0;"); 
    } else if (y > cellHeight * 0.66) {
        cell.setStyle("-fx-border-color: blue; -fx-border-width: 0 0 2 0;"); 
    } else {
        cell.setStyle("-fx-border-color: blue; -fx-border-width: 2;");
    }
  } 

   private void onDragDropped(TreeCell<Expenses> cell, DragEvent event){
    Dragboard db = event.getDragboard();
    if(event.getGestureSource() != cell && db.hasString()){
      // handleDroppedMeasurements(cell, event, db);
      controller.refreshStatementView();
    }
    event.consume();
  }

  // private void handleDroppedMeasurements(TreeCell<Expenses> cell, DragEvent event, Dragboard db){
  //   HashMap<String, Expenses> referencePackage = getExpenseReferencePackage(cell, db);
  //   if(!referencePackage.get("draggedExpense").getChildMap().contains(referencePackage.get("targetExpense"))){
  //    confirmDroppedHandling(cell, event, referencePackage);
  //   }
  // } 

  // private HashMap<String, Expenses> getExpenseReferencePackage(TreeCell<Expenses> cell, Dragboard db){
  //   HashMap<String, Expenses> referencePackage = new HashMap<String, Expenses>();
  //   referencePackage.put("targetExpense", cell.getItem());
  //   referencePackage.put("targetExpenseParent", currentStatement.getParentNodeByChild(referencePackage.get("targetExpense")));
  //   referencePackage.put("draggedExpense", currentStatement.findExpenseByName(db.getString()));
  //   referencePackage.put("draggedExpenseParent", currentStatement.getParentNodeByChild(referencePackage.get("draggedExpense")));
  //   return referencePackage;
  // } 

  // private void confirmDroppedHandling(TreeCell<Expenses> cell, DragEvent event, HashMap<String, Expenses> referencePackage){
  //   double cellHeight = cell.getHeight();
  //   double mouseY = event.getY();
  //   referencePackage.get("draggedExpenseParent").getChildren().remove(referencePackage.get("draggedExpense")); 
  //   if (mouseY < cellHeight * 0.33) {
  //     handlePeripheralInsert(referencePackage, 0);
  //   } else if (mouseY > cellHeight * 0.66) {
  //     handlePeripheralInsert(referencePackage, 1);
  //   } else {
  //     referencePackage.get("targetExpense").addChild(referencePackage.get("draggedExpense"));
  //   }
  // } 

  // private void handlePeripheralInsert(HashMap<String, Expenses> referencePackage, int insertion){
  //   Expenses targetExpense = referencePackage.get("targetExpense");
  //   Expenses targetExpenseParent = referencePackage.get("targetExpenseParent");
  //   Expenses draggedExpense = referencePackage.get("draggedExpense"); 
  //   int targetIndex = targetExpenseParent.getChildren().indexOf(targetExpense); 
  //   targetExpenseParent.getChildren().add(targetIndex+insertion, draggedExpense); 
  // } 



  
}

