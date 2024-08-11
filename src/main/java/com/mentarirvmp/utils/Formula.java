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

    private boolean formulaParanthesesValid(){
      if(formula.charAt(lastIndex) != ')' || openingParanthesisIndex == -1) return false;
      return true; 
    }

    private void initializeParentFormulaAndNestedContent(){
      this.parentFormula = formula.substring(0, openingParanthesisIndex);
      this.nestedContent = formula.substring(openingParanthesisIndex + 1, lastIndex);
    } 

    private boolean recognizedFormula(){
      switch(this.parentFormula){
        case "SUM":
          return true; 
        case "MULTIPLY": 
          return true; 
      }
      return false; 
    }
  }

  public static boolean isFormulaValid(String formula){
    ArrayList<Boolean> truthArray = new ArrayList<>();
    FormulaAnatomy formulaPackage = new FormulaAnatomy(formula);
    if(!formulaPackage.isValid) return false; 

    truthArray.add(true);
    FormulaNode parentFormulaNode = new FormulaNode(formulaPackage.parentFormula);
    String nestedContent = formulaPackage.nestedContent;

    for(String indivContent: nestedContent.split(",")){
      System.out.println(indivContent instanceof String);
    }


    return true; 
    
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



 


 
  






}
