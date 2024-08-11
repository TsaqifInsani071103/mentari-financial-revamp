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

  // //hardcoding valid formulas right now: make a MAP of this brodie 
  // private final static HashMap<String, String> validFormulaOperations = new HashMap<>();
  // //static setup operations 
  // static{
  //   //change this to refer to methods later on instead of <String,String> you do <String, Method> or however you're supposed to do it 
  //   validFormulaOperations.put("SUM()", "valid");
  //   validFormulaOperations.put("MULTIPLY()", "valid");
  // } 

  public static boolean isFormulaValid(String formula){
    String parentFormula = getParentFormulaIfStructureValid(formula);

    if(parentFormula == "") return false; 
    return true; 
  }

  private static String getParentFormulaIfStructureValid(String formula){
    int lastIndex = formula.length()-1; 
    int openingParanthesisIndex = formula.indexOf('(');
    if(formula.charAt(lastIndex) != ')' || openingParanthesisIndex == -1) return "";

    String formulaBehindOpenParanthesis = formula.substring(0, openingParanthesisIndex);
    if(!recognizedFormula(formulaBehindOpenParanthesis)) return "";
    return formulaBehindOpenParanthesis;
  } 
  

  //this is practically mutating the nodes so instead of just checking, we'll just get rootNode if formula is valid, else, nothing. 

  private static boolean isInteger(String str){
    try{
      Integer.parseInt(str);
    }catch(Error e){
      return false; 
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
