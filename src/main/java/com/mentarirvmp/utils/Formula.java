package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Set;
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

  //hardcoding valid formulas right now: make a MAP of this brodie 
  private final static HashMap<String, String> validFormulaOperations = new HashMap<>();
  //static setup operations 
  static{
    //change this to refer to methods later on instead of <String,String> you do <String, Method> or however you're supposed to do it 
    validFormulaOperations.put("SUM()", "valid");
    validFormulaOperations.put("MULTIPLY()", "valid");
  } 
  

  //this is practically mutating the nodes so instead of just checking, we'll just get rootNode if formula is valid, else, nothing. 
  //get formulaStructure if all formulas are valid. 
  public static boolean isFormulaValid(String formula){
    //base case 
    //check if the formula can be split into array elements by using split(",") and then iterate through that. 
    int lastIndex = formula.length()-1; 
    int openingParanthesisIndex = formula.indexOf('(');
    if(formula.charAt(lastIndex) != ')') return false; 
    if(openingParanthesisIndex == -1) return false; 

    String formulaBehindOpenParanthesis = formula.substring(0, openingParanthesisIndex);
    if(!recognizedFormula(formulaBehindOpenParanthesis)) return false; 

    //put the formulaBehindOpenParanthesis into a parent node in the tree. 
    FormulaNode parentNode = new FormulaNode(formulaBehindOpenParanthesis);
    String nestedContent = formula.substring(openingParanthesisIndex + 1, lastIndex);

    // if(!isNestedFormulaValid(parentNode, nestedContent)) return false;

    return true; 
  } 

  private static boolean isNestedFormulaValid(FormulaNode parentFormula, String formula){
    //base case 
    if(formula.equals("")){ 
      parentFormula.addChild(new FormulaNode("0"));
      return true; 
    }

    String[] splitFormula = formula.split(",");
    for(String nestedFormula:splitFormula){
      try{
        //check if its a number or a formula, if its a string its invalid 
        int number = Integer.parseInt(nestedFormula);
        System.out.println(number);
        parentFormula.addChild(new FormulaNode(nestedFormula)); //add the number as is
      }catch(Error e){
        parentFormula.addChild(new FormulaNode(nestedFormula));
        return isFormulaValid(nestedFormula); 
      }
    }

    return false; 

  }

  private static boolean recognizedFormula(String formula){
    switch(formula){
      case "SUM":
        return true; 
      case "MULTIPLY": 
        return true; 
    }
    return false; 

  }

 


 
  






}
