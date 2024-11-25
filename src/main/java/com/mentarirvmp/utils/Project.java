package com.mentarirvmp.utils;

// import com.mentarirvmp.statements.*;
import com.mentarirvmp.fileoperations.FilesTree;
import com.mentarirvmp.fileoperations.TreeNode;
import com.mentarirvmp.statements.Statement;


public class Project {
  private String projectName; 
  //private array of funds 
  //We can definitely abtract FilesTree and Statements to be Data instead, and use polymorphism, 
  private FilesTree fileTree; 

  public Project(String projectName){
    this.projectName = projectName; 
    this.fileTree = new FilesTree();
    addDummyFilesTree(); // initialize the folder shit here. 

  } 

  private void addDummyFilesTree(){
    for(int i = 1; i <= 1; i++){
      makeInitialFolder(i);
    }
  } 

  //TEMPORARY DUMMY STATEMENT!!! UNTIL WE FIGURE OUT THE UI IMPLEMENTATION 
  private Statement getDummyStatement(){
    Statement defaultStatement = new Statement("DefaultSheet");
    Expenses expense1 = new Expenses("dummyExpense1"); 
    defaultStatement.addExpense(expense1);
    defaultStatement.addExpenseToParent(new Expenses("child"), expense1);
    defaultStatement.addExpense(new Expenses("dummyExpense2"));
    return defaultStatement;
  } 

  private void makeInitialFolder(int index){
    //folderNode 
    TreeNode qFolder = new TreeNode("Q" + String.valueOf(index), TreeNode.NodeType.FOLDER);

    //statementNode 
    TreeNode node1 = new TreeNode("Default Statement", TreeNode.NodeType.STATEMENT);
    
    //CHANGE THIS LATER ON 
    node1.setAttachedStatement(getDummyStatement());
    
    this.fileTree.addNode(this.fileTree.getRoot(), qFolder);
    this.fileTree.addNode(qFolder, node1); 
  } 

  public FilesTree getNodeTree(){
    return this.fileTree; 
  } 

  public TreeNode getFirstStatementNode(){
    TreeNode firstNode = getFirstNode(this.fileTree.getRoot()); 
    if(firstNode == this.fileTree.getRoot()){
      // addDefaultStatement(); //add a default statement if there exists no one else
    }
    return getFirstNode(this.fileTree.getRoot());
  } 

  public TreeNode getFirstNode(TreeNode currentNode){
    if(currentNode.isTypeStatement()) return currentNode; 
    for(TreeNode node: currentNode.getChildren()){
      TreeNode foundNode = getFirstNode(node);
      if(foundNode == null || foundNode.isTypeFolder()){
        continue;
      }else{
        return foundNode; 
      }
    }


    return this.fileTree.getRoot(); //return the root node if null 
      
  } 

  // public void addDefaultStatement(){
  //   TreeNode newNode = new TreeNode("Default Statement", TreeNode.NodeType.STATEMENT, this.fileTree.getRoot());
  //   newNode.setAttachedStatement(getDummyStatement());
  //   // newNode.setAttachedStatement(TemplateStatement.getStatementFromTemplate("Default"));
  // }

  public String getProjectName(){
    return this.projectName; 
  }

  public void setName(String newName){
    this.projectName = newName; 

  } 

  // public TreeNode getNodeByStatement(Statement statement){
  //   TreeNode[] foundNode = new TreeNode[1]; 
  //   this.fileTree.traverseTree(node -> {
  //     if(node.getAttachedStatement() == statement){
  //       foundNode[0] = node; 
  //     }
  //   });
  //   return foundNode[0]; 

  // } 

 

}
