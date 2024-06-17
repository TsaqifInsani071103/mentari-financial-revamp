package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 
import java.util.HashMap;

public class Formula {
  private String formula = ""; 
  private int openParanthesisIndex;
  private int closeParanthesisIndex;
  public static String INVALID = "invalid";
  public static String UNRECOGNIZED = "unrecognizedFormula"; 
  
  public Formula(String initialFormula){
    this.formula = initialFormula; 
    refreshParanthesesIndex();
  } 

  private void refreshParanthesesIndex(){
    this.openParanthesisIndex = this.formula.indexOf('('); 
    this.closeParanthesisIndex = this.formula.indexOf(')', this.openParanthesisIndex);
  }


  public String getFormula(){
    return this.formula; 
  } 

  //this formula will get the empty formula as well as the content inside of the formula
  public HashMap<String, String> getEmptyFormulaAndContent(){
    HashMap<String, String> formulaSynthesized = new HashMap<>();
    String emptyFormula = getEmptyFormula();
    if(emptyFormula != UNRECOGNIZED && emptyFormula != INVALID){
      formulaSynthesized.put("emptyFormula", emptyFormula);
      formulaSynthesized.put("content", getContentInsideFormula());
    }else{
      formulaSynthesized.put("emptyFormula", INVALID);
      formulaSynthesized.put("content", INVALID); 
    }

    return formulaSynthesized; 
  } 

  public String getEmptyFormula(){
    //we assume that all formulas has () for it to be a valid formula 
    if(validFormulaFormat()){
      String emptyFormula = parseIntoEmptyFormula(); 
      return isRecognizedFormula(emptyFormula)? emptyFormula : UNRECOGNIZED;
    }
    return INVALID;
  } 

  public String getContentInsideFormula(){
    String contentInsideFormula = this.formula.substring(this.openParanthesisIndex + 1, closeParanthesisIndex); 
    return contentInsideFormula;
  } 

  private boolean validFormulaFormat(){
    if((this.openParanthesisIndex != -1 && this.closeParanthesisIndex !=-1)) return true; 
    return false; 
  } 

  private String parseIntoEmptyFormula(){
    String emptyString = formula.substring(0, this.openParanthesisIndex+ 1) + formula.substring(this.closeParanthesisIndex); 
    return emptyString;
  } 

  private boolean isRecognizedFormula(String emptyFormula){
    switch(emptyFormula){
      case "SUM()":
        return true; 
      default:
        return false; 
    }
  } 


}
