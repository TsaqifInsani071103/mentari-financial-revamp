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

//formula is basically just a tool for StatementsAndExpensehandler to use to basically calculate the valus of the formula strings in the expenses 
public class Formula {
  private String formula = ""; 
  private int openParanthesisIndex;
  private int closeParanthesisIndex;
  private StatementsAndExpenseHandler dataHandler; 
  //I can prolly extract this into like FormulaFinals. 
  public static final String INVALID = "invalid";
  public static final String UNRECOGNIZED = "unrecognizedFormula";
  public static final String ID_STRINGS = "content";
  public static final String EMPTY_FORMULA = "EmptyFormula";

  
  
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
    HashMap<String, String> formulaMap = getEmptyFormulaAndIdStrings();
    String idStrings = formulaMap.get(ID_STRINGS); 
    String emptyFormula = formulaMap.get(EMPTY_FORMULA);
    String[] idStringsParsed = idStrings.split(",");

    return getValueFromArrayByEquation(idStringsParsed, emptyFormula); 
  }

  public int getValueFromArrayByEquation(String[] idArray, String equation){
    switch(equation){
      case "SUM()":
        return calculateSumFromIds(idArray);
      default:
        return 0; 
    }
  } 
  
  //legit right now I'm only using data Handler here, so I can make Formula into a static class and extract addToValueIfExpenseValid into dataHandler. 
  private int calculateSumFromIds(String[] idArray) {
    int value = 0;
    for (String id : idArray) {
        if (canBeConvertedToInt(id)) {
          value = dataHandler.getValueIfExpenseIdValid(id); 
        }
    }
    return value;
  } 


  private boolean canBeConvertedToInt(String str){
  if (str == null || str.isEmpty()) {
    return false;
    } 

  try {
      Integer.parseInt(str);
      return true;
  } catch (NumberFormatException e) {
      return false;
  }
  } 

  //this formula will get the empty formula as well as the content inside of the formula
  public HashMap<String, String> getEmptyFormulaAndIdStrings(){
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
    formulaMap.put(ID_STRINGS, getIdStringsInsideFormula());
  } 

  private void populateInvalidFormulaMap(HashMap<String, String> formulaMap){
    formulaMap.put(EMPTY_FORMULA, INVALID);
    formulaMap.put(ID_STRINGS, INVALID); 
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
      case "MULTIPLY()":
        return true; 
      default:
        return false; 
    }
  } 

  private String getIdStringsInsideFormula(){
    String contentInsideFormula = this.formula.substring(this.openParanthesisIndex + 1, closeParanthesisIndex); 
    return contentInsideFormula;
  } 






}
