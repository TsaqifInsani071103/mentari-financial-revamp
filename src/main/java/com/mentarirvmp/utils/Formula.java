package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mentarirvmp.statements.Statement;

import java.lang.reflect.Method;
// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 
import java.util.HashMap;

//formula is basically just a tool for StatementsAndExpensehandler to use to basically calculate the valus of the formula strings in the expenses 
public class Formula {
  private FormulaNode validFormulaRootNode = null;
  private Statement dataHandler = null;

  public Formula(Statement dataHandler){
    this.dataHandler = dataHandler;

  } 
  public Formula(){

  } 
  
  public static class FormulaAnatomy{
    private String formula;
    private int lastIndex;
    private int openingParanthesisIndex;
    public String parentFormula; 
    public String formulaContent; 
    public boolean isValid = false; 


    public FormulaAnatomy(String formula){
      this.formula = formula; 
      this.lastIndex = formula.length()-1; 
      this.openingParanthesisIndex = formula.indexOf('(');
      if(formulaParanthesesValid()){
        initializeParentFormulaAndNestedContent();
        if(recognizedFormula()){
          isValid = true; 
        }
      }
    }

    public String getFormulaWithoutFormulaContent(){
      return this.parentFormula;
    } 
    public String getFormulaContent(){
      return this.formulaContent;
    } 

    private boolean formulaParanthesesValid(){
      if(formula.charAt(lastIndex) != ')' || openingParanthesisIndex == -1 || !hasSameOpenAndClosingParantheses(this.formula)) return false;
      return true; 
    }

    public static boolean hasSameOpenAndClosingParantheses(String formula){
      Stack<Character> paranthesesStack = new Stack<>();
      for(int i = 0; i < formula.length(); i++){
        if(formula.charAt(i) == '(') paranthesesStack.push('(');
        try{
          if(formula.charAt(i) == ')') paranthesesStack.pop(); 
        }catch(EmptyStackException error){
          //if this error happens that means theres an imbalance between chronological open parantheses and closing parantheses 
          return false; 
        }
      }
      if(paranthesesStack.size() == 0) return true; 
      return false; 
    }

    private void initializeParentFormulaAndNestedContent(){
      this.parentFormula = formula.substring(0, openingParanthesisIndex) + "()";
      this.formulaContent = formula.substring(openingParanthesisIndex + 1, lastIndex);
    } 

    private boolean recognizedFormula(){
      switch(this.parentFormula){
        case "SUM()":
          return true; 
        case "MULTIPLY()": 
          return true; 
      }
      return false; 
    }
  }

  //HARD CODED RIGHT NOW 
  public int getValueIfFormulaValid(String formula){
    if(isFormulaValid(formula)){
      //this is just to check 
      validFormulaRootNode.printAllFormulas(0);
      //if its only a sole integer, just return the number 
      if(isInteger(validFormulaRootNode.getValue()) && validFormulaRootNode.getChildNodes().isEmpty()) return Integer.parseInt(validFormulaRootNode.getValue());

      return getValueOfNodesRecursively(this.validFormulaRootNode);
    }

    return 0; 
  } 

  public int getValueOfNodesRecursively(FormulaNode rootNode){
    int total = 0;
    String formula = rootNode.getValue();
    //if its a formula
    if(!isInteger(rootNode.getValue()) && !rootNode.getChildNodes().isEmpty()){
      if(formula.equals("MULTIPLY()")) total = 1; 
      for(FormulaNode child: rootNode.getChildNodes()){
        if(isInteger(child.getValue())){
          total = calculateByFormulaIntoTotal(formula, Integer.parseInt(child.getValue()), total);
        }else{
          int calculatedInnerFormulaValue = getValueOfNodesRecursively(child); 
          total = calculateByFormulaIntoTotal(formula, calculatedInnerFormulaValue, total);
        } 
      }
    }
    return total; 

  } 
  
  //isFormulaValid creates a formulaNode that has sort of this chronology of what formulas need to be fired? 
  public boolean isFormulaValid(String formula){
    return isFormulaValid(formula, null);
  }

  //theres too many responsibilities going on here. 
  public boolean isFormulaValid(String formula, FormulaNode rootNode){
    //edge cases 
    if(rootNode == null){
      if(formula.equals("")){
        this.validFormulaRootNode = new FormulaNode("0");
        return true;
      }; 
      if(isInteger(formula)){
        this.validFormulaRootNode = new FormulaNode(formula);
        return true; 
      }
    }else{
      if(formula.equals("")) return true; 
      if(isInteger(formula) && rootNode != null){
        rootNode.addChild(new FormulaNode(formula));
        return true;
      }
    }



    FormulaAnatomy parsedFormula = new FormulaAnatomy(formula);

        //CLEAN THESE UP BUDDY!!! 
    //INSTEAD, REGEXP THE FORMULA, CHECK IF ITS AN ID INSTEAD OF CHECKING ALL THE FORMULAS 
    if(this.dataHandler != null){ //this is making unwanted side effect if we check for datahandler itself 
      String actualExpenseValue = "";
      Expenses checkedExpense; 
      checkedExpense=this.dataHandler.getExpenseById(formula);
      if(checkedExpense != Expenses.INVALID_EXPENSE){
        actualExpenseValue = checkedExpense.getValue(); 
        if(rootNode != null){
          rootNode.addChild(new FormulaNode(actualExpenseValue));
          return true; 
        }else{
          validFormulaRootNode = new FormulaNode(actualExpenseValue);
          return true; 
        }
      }else{
        if(!parsedFormula.isValid) return false; 
      }
    }

    if(!parsedFormula.isValid) return false; 

    FormulaNode emptyFormulaNode = new FormulaNode(parsedFormula.getFormulaWithoutFormulaContent());// e.g SUM() in SUM(1,2,3), or SUM() in SUM(MULTIPLY())
    String[] formulaContentArray = parsedFormula.getFormulaContent().split(",");// e.g 1,2,3 in SUM(1,2,3) or MULTIPLY(1,2,3) in SUM(MULTIPLY(1,2,3))

    if(rootNode == null) {
      rootNode = emptyFormulaNode;
      this.validFormulaRootNode = rootNode;
    }else{
      rootNode.addChild(emptyFormulaNode);
    }

    for(int i = 0; i < formulaContentArray.length; i++){
      String indivContent = formulaContentArray[i].trim();
      while(!FormulaAnatomy.hasSameOpenAndClosingParantheses(indivContent) && i+1<formulaContentArray.length){
        indivContent = indivContent + "," + formulaContentArray[i+1];
        i++;
      }
      if(!isFormulaValid(indivContent, emptyFormulaNode)) return false; 
    }

    return true; 
  }


  // System.out.println("TIS THE PROBLEM STRING " + indivContent);
  // System.out.println("NEW STRING " + indivContent);//!!!!!!!!!!!!!!!!!!
  // System.out.println("LOOKING AT: " + indivContent);//!!!!!!!!!!!!!!!!!!
  private int calculateByFormulaIntoTotal(String formula, int value, int total){
    switch(formula){
      case "SUM()":
        return SUM(value, total);
      case "MULTIPLY()":
        return MULTIPLY(value, total);
      default: 
        return 0;
    }
  } 

  private int SUM(int value, int total){
    total += value;
    return total;
  }

  private int MULTIPLY(int value, int total){
    total *= value;
    return total;
  }

  private boolean isInteger(String str){
    try{
      Integer.parseInt(str);
    }catch(NumberFormatException e){
      return false; 
    }
    return true; 
  } 



 


 
  






}
