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
    return getValueFromValidExpensesIn(content); 

  }

  //this one is only for SUM() btw. 
  //make this more efficient by going through the array once and then immediately populating the value field instead of doing this like twice/thrice. 
  public int getValueFromValidExpensesIn(String content){
    String[] contentParsed = content.split(",");
    ArrayList<Expenses> validExpenses = getValidExpensesArray(contentParsed);
    int value = 0;
    for(Expenses expense: validExpenses){
      //what happens if the value is not a number huh?? 
      value += Integer.parseInt(expense.getValue()); 
    }
    //this is a placeholder 
    return value;  
  } 

  public ArrayList<Expenses> getValidExpensesArray(String[] idArray){
    ArrayList<Expenses> validExpensesArray = new ArrayList<Expenses>(); 
    for(String id: idArray){
      populateArrayIfExpenseValid(validExpensesArray, id);
      
    } 
    return validExpensesArray; 
   } 

  private void populateArrayIfExpenseValid(ArrayList<Expenses> validExpensesArray, String id){
    if(!canBeConvertedToInt(id)) return; 
    Expenses currentExpense = dataHandler.getExpenseByID(Integer.parseInt(id));
    if(currentExpense != Expenses.INVALID_EXPENSE){
      validExpensesArray.add(currentExpense);
    }

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

  private String getContentInsideFormula(){
    String contentInsideFormula = this.formula.substring(this.openParanthesisIndex + 1, closeParanthesisIndex); 
    return contentInsideFormula;
  } 






}
