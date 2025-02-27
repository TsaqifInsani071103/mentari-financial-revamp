package com.mentarirvmp.utils;


// import com.mentarirvmp.statements.*;
import com.mentarirvmp.fileoperations.FilesTree;
import com.mentarirvmp.fileoperations.TreeNode;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.statements.TemplateStatement;


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

  private void makeInitialFolder(int index){
    //folderNode 
    TreeNode qFolder = new TreeNode("Q" + String.valueOf(index), TreeNode.NodeType.FOLDER);

    //statementNode 
    TreeNode node1 = new TreeNode("Default Statement", TreeNode.NodeType.STATEMENT);
    
    node1.setAttachedStatement(TemplateStatement.getTemplateByName("Default"));
    
    this.fileTree.addNode(this.fileTree.getRoot(), qFolder);
    this.fileTree.addNode(qFolder, node1); 
  } 

  public FilesTree getNodeTree(){
    return this.fileTree; 
  } 

  public Statement getFirstStatement(){
    TreeNode firstNode = getFirstNodeByRoot(this.fileTree.getRoot()); 
    if(firstNode == this.fileTree.getRoot()){
      addDefaultStatementNode(); //add a default statement Node if there exists no other node 
    }
    return getFirstNodeByRoot(this.fileTree.getRoot()).getAttachedStatement();
  } 

  public TreeNode getFirstNodeByRoot(TreeNode currentNode){
    if(currentNode.isTypeStatement()) return currentNode; 
    for(TreeNode node: currentNode.getChildren()){
      TreeNode foundNode = getFirstNodeByRoot(node);
      if(foundNode == null || foundNode.isTypeFolder()){
        continue;
      }else{
        return foundNode; 
      }
    }


    return this.fileTree.getRoot(); //return the root node if null 
      
  } 

  public void addDefaultStatementNode(){
    TreeNode newNode = new TreeNode("Default Statement", TreeNode.NodeType.STATEMENT, this.fileTree.getRoot());
    newNode.setAttachedStatement(TemplateStatement.getTemplateByName("Default"));
  }

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
