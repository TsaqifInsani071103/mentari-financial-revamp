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

  public void printAllFormulas(int counter) {
    counter++;
    for(int i = 0; i<counter;i++){
      System.out.print("-");
    }
    System.out.println(this.formula);
    for (FormulaNode child : children) {
      child.printAllFormulas(counter);  // Recursively print each child's formulas
    }
  }
}
