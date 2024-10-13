package com.mentarirvmp.fileoperations;
import java.util.ArrayList;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.LoaderHelper;

import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;

public abstract class TreeNode {
  private String name; 
  protected String iconPath; //make this a static property in the child nodes 
  private TreeNode parent; 
  private ArrayList<TreeNode> children; 
  private Statement attachedStatement; 
  private FilesTree treeReference;

  public TreeNode(String name){
    this.name = name; 
    this.parent = null; 
    this.children = new ArrayList<TreeNode>(); 
  } 

  public TreeNode(String name, TreeNode parent){
    if(parent != null){
      parent.addChild(this);
    }
    this.name = name; 
    this.parent = parent; 
    this.children = new ArrayList<TreeNode>(); 

  } 

  //All tree nodes cannot have the same name, add duplicate handler here 
//add buttons next to the folder views to either add another folder or add another file 
  public void addChild(TreeNode childNode){
    if(!isNameDuplicate(childNode.getName())){
      childNode.setParent(this); 
      children.add(childNode); 
      childNode.setTreeReference(treeReference);
    }else{
      // LoaderHelper.showPopupModal("a folder/file with this name already exists", 300, 150);
    }
  } 

  public void removeChild(TreeNode childNode){
    children.remove(childNode); 
    childNode.removeParent(); 
    if(!childNode.getChildren().isEmpty()){
      realignGrandchildNodes(childNode);
    }
    childNode = null; 
  } 

  public void removeAllChildren(TreeNode childNode){
    children.remove(childNode); 
    childNode.removeParent(); 
  } 

  private void realignGrandchildNodes(TreeNode node){
    for(TreeNode child : node.getChildren()){
      child.removeParent(); 
      this.addChild(child);
    }
  }

  public void setParent(TreeNode parentNode){
    this.parent = parentNode;
  } 

  public void removeParent(){
    this.parent = null; 
  } 

  public String getName(){
    return this.name; 
  }

  public TreeNode getParent(){
    return this.parent; 
  } 

  public ArrayList<TreeNode> getChildren(){
    return this.children; 
  }

  public void setName(String newName){
    if(!isNameDuplicate(newName)){
      this.name = newName; 
    }else{
      // LoaderHelper.showPopupModal("a folder/file with this name already exists", 500, 150);
    }
  }

  public void setAttachedStatement(Statement statement){
    this.attachedStatement = statement; 
  } 

  public Statement getAttachedStatement(){
    return this.attachedStatement;
  } 

  public void setTreeReference(FilesTree tree){
    this.treeReference = tree; 
  };

  public FilesTree getTreeReference(){
    return this.treeReference;
  }

  //there is an opportunity to make this quicker. instead of traversing through all of the nodes without breaking regardless of a found duplicate node 
  private boolean isNameDuplicate(String newName){
    boolean[] duplicateNode = new boolean[1]; 
    FilesTree tree = getTreeReference();
    tree.traverseTree((node) -> {
      if(node.getName().equals(newName)) duplicateNode[0] = true;
    });
    return duplicateNode[0]; 
  }

  public abstract Parent generateView();
  public abstract void addClickListener(Control element); 
  public abstract void handleNodeClick(); 

}

