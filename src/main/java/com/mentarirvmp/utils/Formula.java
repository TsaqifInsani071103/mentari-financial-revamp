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
  

  //I want to backtrack and check the string preceeding a "(" when I find that opening paranthesis, and check for the string's validity. 
  //then I want to ensure that there is enough number of opening and closing paranthesis, each valid enclosing formula and paranthesis will then be extracted into a stack that I will in the end pop to calculate the formula. 
  //I want to check that the 
  public static boolean isFormulaValid(String formula){
    //check if the formula can be split into array elements by using split(",") and then iterate through that. 
    int lastIndex = formula.length()-1; 
    int openingParanthesisIndex = formula.indexOf('(');
    if(formula.charAt(lastIndex) != ')') return false; 
    if(openingParanthesisIndex == -1) return false; 
    String formulaBehindOpenParanthesis = formula.substring(0, openingParanthesisIndex);
    if(!recognizedFormula(formulaBehindOpenParanthesis)) return false; 
    //put the formulaBehindOpenParanthesis into a parent node in the tree. 
    FormulaNode rootNode = new FormulaNode(formulaBehindOpenParanthesis);
    String nestedContent = formula.substring(openingParanthesisIndex + 1, lastIndex);
    return isNestedFormulaValid(rootNode, nestedContent);
  } 

  private static boolean isNestedFormulaValid(FormulaNode parentFormula, String formula){
    String[] splitFormula = formula.split(",");
    for(String nestedFormula:splitFormula){
      try{
        //check if its a number or a formula, if its a string its invalid 
        int number = Integer.parseInt(nestedFormula);
        System.out.println(number);
      }catch(Error e){
        
      }
    }
    return true;

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
