package com.mentarirvmp.fileoperations;
//These are the annotations 
import org.junit.jupiter.api.Test;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.MockObjects;
import com.mentarirvmp.fileoperations.FilesTree;
import com.mentarirvmp.fileoperations.TreeNode;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class FilesTreeAndNodeTest {
  FilesTree dummyFilesTree = new FilesTree(); 
  Set<TreeNode> filesTreeAsSet = new HashSet<TreeNode>(); 

  @BeforeEach
  public void initializeDummyFilesTree(){
    TreeNode node1 = new TreeNode("1", TreeNode.NodeType.STATEMENT);
    this.dummyFilesTree.addNode(this.dummyFilesTree.getRoot(), node1);
    TreeNode node1Child = new TreeNode("1Child", TreeNode.NodeType.STATEMENT, node1);

    TreeNode node2 = new TreeNode("2", TreeNode.NodeType.STATEMENT);
    this.dummyFilesTree.addNode(this.dummyFilesTree.getRoot(), node2);
    TreeNode node2Child = new TreeNode("2Child", TreeNode.NodeType.STATEMENT, node2);

  } 

  //Documentation sake, I only want to add new nodes only if their parent nodes exist 
  // add child node to parent node basically. 
  
  public void populateFilesTreeSet(){
    this.dummyFilesTree.traverseTree(node -> {
      // System.out.println("THESE ARE THE NODES: " + node.getName());
      filesTreeAsSet.add(node);
    });
  }

  @Test 
  public void FilesTreeNameDuplicate(){
    //if I add a node to filesTree, I want the code to give me a warning that I can't add duplicate names and tell me to do it again 
    populateFilesTreeSet();
    int originalLength = this.filesTreeAsSet.size(); 

    //Testing duplicate name add 
    this.dummyFilesTree.addNode(this.dummyFilesTree.getRoot(), new TreeNode("2", TreeNode.NodeType.STATEMENT)); 
    populateFilesTreeSet();

    assertEquals(originalLength, this.filesTreeAsSet.size());

    this.dummyFilesTree.addNode(this.dummyFilesTree.getRoot(), new TreeNode("uniqueNameNode", TreeNode.NodeType.STATEMENT)); 
    populateFilesTreeSet();

    assertEquals(originalLength + 1, filesTreeAsSet.size()); 

  } 


}
