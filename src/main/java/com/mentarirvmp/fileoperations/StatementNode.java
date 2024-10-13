package com.mentarirvmp.fileoperations;


import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;


public class StatementNode extends TreeNode{
  public StatementNode(String name, TreeNode parent){
    super(name, parent); 
  } 
  
  public StatementNode(String name){
    super(name); 
  } 

  @Override
  public Parent generateView() {
    VBox view = new VBox(5); 
    Label templateName = new Label("Template: " + getAttachedStatement().getName());
    Label name = new Label(getName());
    view.getChildren().addAll(name, templateName);
    return view; 
  }

  @Override
  public void addClickListener(Control container) {
  }

  @Override
  public void handleNodeClick() {}
  
}
