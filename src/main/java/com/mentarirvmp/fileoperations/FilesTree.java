package com.mentarirvmp.fileoperations;

import java.util.function.Consumer;


public class FilesTree {
  private TreeNode root; 

  public FilesTree(){
    // root = new TreeNode("Root", null); 
    root = new TreeNode("Statements Folder", null); 
    root.setTreeReference(this);
  }

  public TreeNode getRoot(){
    return this.root; 
  } 
 
  // Method to add a node
  public void addNode(TreeNode parent, TreeNode child) {
    parent.addChild(child);
  }

  // Method to remove a node
  public void removeNode(TreeNode parent, TreeNode child) {
      parent.removeChild(child);
  }

  // Method to modify a node's data
  public void modifyNodeData(TreeNode node, String newData) {
  }


// Method to find a node by its name
public TreeNode findNodeByName(String name) {
    return findNodeByName(root, name);
}

private TreeNode findNodeByName(TreeNode currentNode, String name) {
    if (currentNode.getName().equals(name)) {
        return currentNode;
    }
    
    for (TreeNode child : currentNode.getChildren()) {
        TreeNode foundNode = findNodeByName(child, name);
        if (foundNode != null) {
            return foundNode;
        }
    }
    return null;
}

//Method to traverse all nodes in the tree
public void traverseTree(Consumer<TreeNode> action) {
    traverseTree(root, action);
}

private void traverseTree(TreeNode node, Consumer<TreeNode> action) {
    if(node == null) return; 

    action.accept(node); // Apply the action to the current node

    // Recursively traverse the children
    for (TreeNode child : node.getChildren()) {
        traverseTree(child, action);
    }
}





  
}
