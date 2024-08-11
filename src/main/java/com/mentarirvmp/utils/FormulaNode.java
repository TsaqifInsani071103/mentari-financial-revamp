package com.mentarirvmp.utils;
import java.util.ArrayList;

//I'll make this class a data structure. 
public class FormulaNode {
  public FormulaNode parentNode; 
  public String formula;
  public ArrayList<FormulaNode> children = new ArrayList<>();

  public FormulaNode(String formula){
    this.formula=formula; 
  } 

  public void setParent(FormulaNode parentNode){
    this.parentNode = parentNode; 
  } 

  public void addChild(FormulaNode child){
    this.children.add(child);
    child.setParent(this);
  }

  public void printAllFormulas() {
    System.out.println(this.formula);  // Print the current node's formula
    for (FormulaNode child : children) {
      child.printAllFormulas();  // Recursively print each child's formulas
    }
  }
}
