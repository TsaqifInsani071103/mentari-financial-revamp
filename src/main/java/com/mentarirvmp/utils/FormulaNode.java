package com.mentarirvmp.utils;
import java.util.ArrayList;

//I'll make this class a data structure. 
public class FormulaNode {
  public FormulaNode parentNode; 
  public String formula;
  public ArrayList<FormulaNode> children = new ArrayList<>();
  private boolean hasChild = false; 
  public static FormulaNode INVALID = new FormulaNode("INVALID_FORMULA_123321123321");

  public FormulaNode(String formula){
    this.formula=formula; 
  } 

  public void setParent(FormulaNode parentNode){
    this.parentNode = parentNode; 
  } 

  public void addChild(FormulaNode child){
    this.children.add(child);
    child.setParent(this);
    this.hasChild = true; 
  }

  public boolean hasChild(){
    return this.hasChild;
  } 

  public void printAllFormulas(int counter) {
    for(int i = 0; i<counter;i++){
      if(i + 1 == counter){
        System.out.print(" |-");
      }else{
        System.out.print(" ");
      }
    }
    System.out.println(this.formula);
    counter++;
    for (FormulaNode child : children) {
      child.printAllFormulas(counter);  // Recursively print each child's formulas
    }
  }
}
