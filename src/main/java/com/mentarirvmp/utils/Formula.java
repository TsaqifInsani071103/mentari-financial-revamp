package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mentarirvmp.servicehandlers.StatementsAndExpenseHandler;

import java.lang.reflect.Method;
// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 
import java.util.HashMap;

//formula is basically just a tool for StatementsAndExpensehandler to use to basically calculate the valus of the formula strings in the expenses 
public class Formula {
  public static class FormulaAnatomy{
    private String formula;
    private int lastIndex;
    private int openingParanthesisIndex;
    public String parentFormula; 
    public String nestedContent; 
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

    public String getFormulaWithoutNestedContent(){
      return this.parentFormula;
    } 
    public String getNestedContent(){
      return this.nestedContent;
    } 

    private boolean formulaParanthesesValid(){
      if(formula.charAt(lastIndex) != ')' || openingParanthesisIndex == -1 || !hasSameOpenAndCloseParantheses(this.formula)) return false;
      return true; 
    }

    public static boolean hasSameOpenAndCloseParantheses(String formula){
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
      this.nestedContent = formula.substring(openingParanthesisIndex + 1, lastIndex);
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

 
  public static boolean isFormulaValid(String formula){
    return isFormulaValid(formula, null);
  }

  //theres too many responsibilities going on here. 
  public static boolean isFormulaValid(String formula, FormulaNode rootNode){
    //edge cases 
    if(formula.equals("")) return true; 
    if(isInteger(formula)){
      rootNode.addChild(new FormulaNode(formula));
      return true;
    }

    FormulaAnatomy analyzedFormula = new FormulaAnatomy(formula);
    if(!analyzedFormula.isValid) return false; 

    FormulaNode emptyFormulaNode = new FormulaNode(analyzedFormula.getFormulaWithoutNestedContent());
    String[] nestedContentArray = analyzedFormula.getNestedContent().split(",");

    if(rootNode == null) {
      rootNode = emptyFormulaNode;
    }else{
      rootNode.addChild(emptyFormulaNode);
    }
    // System.out.println("TIS THE NESTED CONTENT " + Arrays.asList(nestedContentArray).toString());//!!!!!!!!!!!!!!!!!!
    if(!parsedAndAppendedContentIsValid(nestedContentArray, emptyFormulaNode)) return false; 

    System.out.println("==============");
    rootNode.printAllFormulas(0);//!!!!!!!!!!!!!!!!!!

    return true; 
  }

  private static boolean parsedAndAppendedContentIsValid(String[] nestedContentArray, FormulaNode parentNode){
    for(int i = 0; i < nestedContentArray.length; i++){
      String indivContent = nestedContentArray[i].trim();
      if(indivContentIsMissingClosingParanthesis(indivContent)){
        // System.out.println("TIS THE PROBLEM STRING " + indivContent);
        while(!FormulaAnatomy.hasSameOpenAndCloseParantheses(indivContent) && i+1<nestedContentArray.length){
          indivContent = indivContent + "," + nestedContentArray[i+1];
          i++;
          // System.out.println("NEW STRING " + indivContent);//!!!!!!!!!!!!!!!!!!
        }
      }
      // System.out.println("LOOKING AT: " + indivContent);//!!!!!!!!!!!!!!!!!!
      if(!isFormulaValid(indivContent, parentNode)) return false; 
    }
    return true; 
  } 

  private static boolean indivContentIsMissingClosingParanthesis(String indivContent){
    int openingParanthesisIndex = indivContent.indexOf("(");
    int closingParanthesisIndex = indivContent.indexOf(")");
    if(openingParanthesisIndex != -1 && closingParanthesisIndex == -1) return true; 
    return false; 
  }


  //this is practically mutating the nodes so instead of just checking, we'll just get rootNode if formula is valid, else, nothing. 

  private static boolean isInteger(String str){
    try{
      Integer.parseInt(str);
    }catch(NumberFormatException e){
      return false; 
    }
    return true; 
  } 



 


 
  






}
