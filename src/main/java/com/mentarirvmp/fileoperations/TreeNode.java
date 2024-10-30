package com.mentarirvmp.fileoperations;
import java.util.ArrayList;

import com.mentarirvmp.statements.Statement;


public class TreeNode {
  enum NodeType{
    STATEMENT,
    FOLDER 

  }
  private String name; 
  protected String iconPath; //make this a static property in the child nodes 
  private TreeNode parent; 
  private ArrayList<TreeNode> children; 
  private Statement attachedStatement; 
  private FilesTree treeReference;
  //to be used later 
  private NodeType nodeType; 

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
      //this shall be handled by the view handlers 
      // LoaderHelper.showPopupModal("a folder/file with this name already exists", 500, 150);
    }
  }

  public void setAttachedStatement(Statement statement){
    this.attachedStatement = statement; 
  } 

  public Statement getAttachedStatement(){
    return this.attachedStatement;
  } 

  //tree reference dependency injection 
  //good for separation of concerns and whatnot 
  public void setTreeReference(FilesTree tree){
    this.treeReference = tree; 
  };

  public FilesTree getTreeReference(){
    return this.treeReference;
  }

  //I dont think I want the isNameDuplicate to be here and instead be handled by the handler between the user input and whatever will handle FilesTree.java and duplicate names. Much like the loadHelper modal poppwer will be handled by that guy too. 
  private boolean isNameDuplicate(String newName){
    FilesTree tree = getTreeReference();
    if(tree.findNodeByName(newName) != null) return true; 
    return false; 
  }



}

