package com.mentarirvmp.utils;
import java.math.BigDecimal; 
import java.lang.StringBuilder;

//this class just adds commas and decimal points to the final sum calculated in Formula 
public class BigDecimalHandler {
  private String sign = "";
  private String userInput; 
  private String decimalPoints; 

  //I kind of don't like how there are so many side effects here, kind of makes it hard to read. But it's not a huge problem right now.

  private static String validUserInputRegex = "^-?\\d{1,3}(,\\d{3})*(\\.\\d+)?$";

  public static BigDecimal parseInputToBigDecimal(String numberString){
    String strippedNumber = getCleanedCommas(numberString);
    return new BigDecimal(strippedNumber);
  } 
  
  //to be used as a standalone 
  private static String getCleanedCommas(String numberString){
    if(numberString.contains(",")){
      return numberString.replaceAll(",", ""); 
    }
    return numberString; 
  }

  public static boolean validNumberFormat(String inputString){
    if(inputString.matches(validUserInputRegex) || isNumber(inputString)) return true; 
    return false; 
  } 

  //change isInteger to isValidDecimalFormat
  private static boolean isNumber(String str){
    try{
      BigDecimal validIfTrue = new BigDecimal(str);
    }catch(NumberFormatException e){
      return false; 
    }
    return true; 
  } 

  //constructor method 
  //This actually takes in a bigDecimal String instead of the original userInput String 
  public BigDecimalHandler(BigDecimal userInput){
    this.userInput = userInput.toString(); 
    if(this.userInput.startsWith("-")){
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
      int indexOfDecimal = this.userInput.indexOf(".");
      this.decimalPoints = this.userInput.substring(indexOfDecimal); 
      if(new BigDecimal(this.decimalPoints).compareTo(new BigDecimal("0.0")) == 0){
        this.decimalPoints = ".0";
      }
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

  public String getCalculatedSumAsString(){ //at this point sum as string is in a scientific notation 
    if(checkIfScientificNotation(this.userInput)) {
      String bigDecimalNumber = new BigDecimal(this.userInput).toPlainString();
      this.userInput = bigDecimalNumber;
      parseUserInput(); 
    }
    return this.userInput;
  } 


  //because BigDecimal's string representation is with the ScientificNotation. 
  protected static boolean checkIfScientificNotation(String str) {
    return str.matches("-?\\d\\.\\d+E[+-]?\\d+");
  }
  
}
