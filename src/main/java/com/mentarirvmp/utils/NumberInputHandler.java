package com.mentarirvmp.utils;
import java.math.BigDecimal; 
import java.lang.StringBuilder;

//this class just adds commas and decimal points to the final sum calculated in Formula 
public class NumberInputHandler {
  private String sign = "";
  private int indexOfDecimal; 
  private String userInput; 
  private String decimalPoints; 

  private static String validUserInputRegex = "^-?\\d{1,3}(,\\d{3})*(\\.\\d+)?$";

  public static BigDecimal parseInputToBigDecimal(String number){
    String strippedNumber = getCleanedCommas(number);
    return new BigDecimal(strippedNumber);
  } 
  
  //to be used as a standalone 
  private static String getCleanedCommas(String number){
    if(number.contains(",")){
      return number.replaceAll(",", ""); 
    }
    return number; 
  }

  public static boolean validNumberFormat(String userInput){
    if(userInput.matches(validUserInputRegex)) return true; 
    return false; 
  } 

  public String getCalculatedSumAsString(){ //at this point sum as string is in a scientific notation 
    if(checkIfScientificNotation(this.userInput)) {
      String bigDecimalNumber = new BigDecimal(this.userInput).toPlainString();
      this.userInput = bigDecimalNumber;
      parseUserInput(); 
    }
    return this.userInput;
  } 

  //constructor method 
  //This actually takes in a bigDecimal String instead of the original userInput String 
  public NumberInputHandler(String userInput){
    this.userInput = userInput; 
    if(this.userInput.contains("-")){
      this.sign = "-"; 
      this.userInput = this.userInput.substring(1); 
    }
    
    parseUserInput();
  }

  //assigning originalString to sumAs String just resets the whole process. 
  private void parseUserInput(){
    extractDecimalPoints(); 
    addCommas(); 
  } 

  //upon initialization 
  private void extractDecimalPoints(){
    if(this.userInput.contains(".")){
      this.indexOfDecimal = this.userInput.indexOf(".");
      this.decimalPoints = this.userInput.substring(indexOfDecimal); 
      this.userInput = this.userInput.substring(0, indexOfDecimal); 
    }else{
      this.decimalPoints = ".0"; 
    }
  } 

  private void addCommas() {
    if (this.userInput == null || this.userInput.isEmpty() || !moreThanThousand()) {
       this.userInput = numberStitchedBack(); 
    }else{
      addCommasInNumber(); 
      this.userInput = numberStitchedBack(); 
    }

  
  }

  private boolean moreThanThousand(){
    if(Math.pow(10,this.userInput.length()) >= 1000){
      return true; 
    }
    return false; 
  }

  private String numberStitchedBack(){
    if(this.sign.equals("-")){
      return this.sign += this.userInput += this.decimalPoints;
    }
    return this.userInput += this.decimalPoints; 
  } 

  private void addCommasInNumber(){
    String resultingString = "";
    int counter = 0; 
    for(int i = this.userInput.length()-1; i >= 0; i--){
      resultingString += this.userInput.charAt(i);
      counter++; 
      if(counter % 3 == 0 && i != 0){
        resultingString += ",";
      } 
    }
    
    this.userInput = new StringBuilder(resultingString).reverse().toString(); 
  } 

  //because BigDecimal's string representation is with the ScientificNotation. 
  private boolean checkIfScientificNotation(String str) {
    return str.matches("-?\\d\\.\\d+E[+-]?\\d+");
}
  
}
