package com.mentarirvmp.fileoperations;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FolderNode extends TreeNode{

  public FolderNode(String name, TreeNode parent){
    super(name, parent); 
  } 
  
  public FolderNode(String name){
    super(name); 
  } 

  @Override
  public Parent generateView() {
    VBox view = new VBox(5); 
    Label name = new Label(getName());
    view.getChildren().add(name);
    return view; 
  }

  //here we see the drawbacks of Inheritance 
  @Override
  public void addClickListener(Control element) {

  }

  @Override
  public void handleNodeClick() {

  }
  
}
