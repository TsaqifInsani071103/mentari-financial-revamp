package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;


import java.lang.reflect.Method;
// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 
import java.util.HashMap;

//formula is basically just a tool for StatementsAndExpensehandler to use to basically calculate the valus of the formula strings in the expenses 
public class Formula {
  private FormulaNode validFormulaRootNode = null;
  
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
      validFormulaRootNode.printAllFormulas(0);
      // System.out.println("VALID FORMULA HERE: " + validFormulaRootNode);
      return 10;
    }
    return 0; 
  } 
  
  //isFormulaValid creates a formulaNode that has sort of this chronology of what formulas need to be fired? 
  public boolean isFormulaValid(String formula){
    return isFormulaValid(formula, null);
  }

  //theres too many responsibilities going on here. 
  // System.out.println("TIS THE NESTED CONTENT " + Arrays.asList(formulaContentArray).toString());//!!!!!!!!!!!!!!!!!!
  // System.out.println("==============");
  // rootNode.printAllFormulas(0);//!!!!!!!!!!!!!!!!!!
  public boolean isFormulaValid(String formula, FormulaNode rootNode){
    //edge cases 
    if(formula.equals("") && rootNode != null) return true; 
    if(formula.equals("") && rootNode == null) return false; 
    if(isInteger(formula) && rootNode != null){
      rootNode.addChild(new FormulaNode(formula));
      return true;
    }
    //checking if the formula is just a sole number 
    //this is just to anticipate that we want sole numbers in our inputs to work without having to be in formula form?? 
    if(isInteger(formula) && rootNode == null){
      return true; 
    }

    FormulaAnatomy parsedFormula = new FormulaAnatomy(formula);
    if(!parsedFormula.isValid) return false; 

    FormulaNode emptyFormulaNode = new FormulaNode(parsedFormula.getFormulaWithoutFormulaContent());// e.g SUM() in SUM(1,2,3)
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


  private boolean isInteger(String str){
    try{
      Integer.parseInt(str);
    }catch(NumberFormatException e){
      return false; 
    }
    return true; 
  } 



 


 
  






}
