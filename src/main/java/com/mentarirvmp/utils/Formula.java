package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import com.mentarirvmp.servicehandlers.StatementsAndExpenseHandler;

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
  public static String CONTENT = "content";
  public static String EMPTY_FORMULA = "EmptyFormula";
  private StatementsAndExpenseHandler dataHandler; 
  
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

  public void setHandler(StatementsAndExpenseHandler dataHandler){
    this.dataHandler = dataHandler; 

  } 

  public int calculateFormulaValue(){
    HashMap<String, String> formulaMap = getEmptyFormulaAndContent();
    String content = formulaMap.get(CONTENT); 

    return 0; 

  }



  private int getValueFromValidExpensesIn(String content){
    String[] contentParsed = parseContentIntoArray(content);
    String[] validExpenses = getValidExpenses(contentParsed);
    //this is a placeholder 
    return 0;  
  } 

  private String[] parseContentIntoArray(String content){
    String[] contentParsed = content.split(",");
    for(String item: contentParsed){
      item = item.replace(" ", ""); 
    }
    return contentParsed; 

  } 

  // private String[] getValidExpenses(String[] idArray){
  //   for(String id: idArray){
  //     if(dataHandler.getExpenseByID(id).valid()){

  //     }

  //   } 


  // } 

  //this formula will get the empty formula as well as the content inside of the formula
  public HashMap<String, String> getEmptyFormulaAndContent(){
    HashMap<String, String> formulaMap = new HashMap<>();
    initializeFormulaSynthesized(formulaMap);
    return formulaMap; 
  } 

  private void initializeFormulaSynthesized(HashMap<String, String> formulaMap){
    String emptyFormula = getEmptyFormula();
    if(emptyFormula != UNRECOGNIZED && emptyFormula != INVALID){
      populateValidFormulaMap(formulaMap, emptyFormula);
    }else{
      populateInvalidFormulaMap(formulaMap); 
    }
  } 

  public String getEmptyFormula(){
    //we assume that all formulas has () for it to be a valid formula 
    if(validFormulaFormat()){
      String emptyFormula = parseIntoEmptyFormula(); 
      return isRecognizedFormula(emptyFormula)? emptyFormula : UNRECOGNIZED;
    }
    return INVALID;
  } 

  private void populateValidFormulaMap(HashMap<String, String> formulaMap, String emptyFormula){
    formulaMap.put(EMPTY_FORMULA, emptyFormula);
    formulaMap.put(CONTENT, getContentInsideFormula());
  } 

  private void populateInvalidFormulaMap(HashMap<String, String> formulaMap){
    formulaMap.put(EMPTY_FORMULA, INVALID);
    formulaMap.put(CONTENT, INVALID); 
  } 

  private boolean validFormulaFormat(){
    if((this.openParanthesisIndex != -1 && this.closeParanthesisIndex !=-1)) return true; 
    return false; 
  } 

  private String parseIntoEmptyFormula(){
    String emptyString = formula.substring(0, this.openParanthesisIndex+ 1) + formula.substring(this.closeParanthesisIndex); 
    return emptyString;
  } 

  //()SUM is automatically ruled out because when empty its not recognized as SUM() 
  private boolean isRecognizedFormula(String emptyFormula){
    switch(emptyFormula){
      case "SUM()":
        return true; 
      default:
        return false; 
    }
  } 

  public String getContentInsideFormula(){
    String contentInsideFormula = this.formula.substring(this.openParanthesisIndex + 1, closeParanthesisIndex); 
    return contentInsideFormula;
  } 






}
