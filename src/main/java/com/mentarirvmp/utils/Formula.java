package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 

public class Formula {
  private String formula = ""; 
  
  public Formula(String initialFormula){
    this.formula = initialFormula; 
  } 

  public String getFormula(){
    return this.formula; 
  } 

  public String getEmptyFormula(){
    //we assume that all formulas has () for it to be a valid formula 
    int startIndex = this.formula.indexOf('(');
    int endIndex = this.formula.indexOf(')', startIndex);
    if(validFormulaFormat(startIndex,endIndex)){
      String emptyFormula = parseIntoEmptyFormula(startIndex, endIndex); 
      return isRecognizedFormula(emptyFormula)? emptyFormula : "unrecognizedFormula";
    }
    return "invalid";
  } 

  private boolean validFormulaFormat(int startIndex, int endIndex){
    startIndex = this.formula.indexOf('(');
    endIndex = this.formula.indexOf(')', startIndex);
    if((startIndex != -1 && endIndex !=-1)) return true; 
    return false; 
  } 

  private boolean isRecognizedFormula(String emptyFormula){
    switch(this.formula){
      case "=SUM()":
        return true; 
      default:
        return false; 
    }
  } 


  private String parseIntoEmptyFormula(int startIndex, int endIndex){
    String emptyString = formula.substring(0, startIndex + 1) + formula.substring(endIndex); 
      return emptyString;
  } 

}
