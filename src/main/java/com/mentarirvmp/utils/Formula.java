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
    //hard coded testing right now.
    int firstOpenParanthesisIndex = formula.indexOf("("); 
    if(firstOpenParanthesisIndex == 0) return false; 
    String substringToParanthesis = formula.substring(0, firstOpenParanthesisIndex); 
    if(validFormulaOperations.get(substringToParanthesis) instanceof String){
      //search for a closing parantehsis whose next index is either a comma or a space cuz those are the only valid ones for the outer parantehses
    } 

    // int lettersCounted = 0; 
    // while(firstOpenParanthesisIndex > 0){
    //   for(String validFormulaOperation: validFormulaOperations){
    //     int lastIndex = validFormulaOperation.length() - 1; 
    //     if(validFormulaOperation.charAt(lastIndex - lettersCounted) == formula.charAt(firstOpenParanthesisIndex - 1 - lettersCounted)){
    //       lettersCounted++; 
    //       continue;
    //     }
    //   }
    // }
    return true; 
  } 

 


 
  






}
