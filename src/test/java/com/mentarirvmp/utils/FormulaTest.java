package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;

import com.mentarirvmp.servicehandlers.StatementsAndExpenseHandler;
import com.mentarirvmp.statements.Statement;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class FormulaTest {
  //extract this to MockObjects too later. 

  //use Case: 
  //

  //first thing to test is if the passed formula is valid or not. 
  @Test 
  public void validFormulaFormat(){

    //remember that you need to check for a recognized formula preceeding the opening paranthesis. 
    String dummyFormula = "SUM(MULTIPLY())"; 
    Boolean formulaValidity = Formula.isFormulaValid(dummyFormula);
    //first case SUM(MULTIPLY()) is true  
    assertEquals(true, formulaValidity);

  } 

  //I can extract this into its own class 
  @Test 
  public void recognizedFormula(){

  } 
  // @Test 
  // public void getEmptyFormula(){

  //   assertEquals("SUM()", new Formula("SUM(123,412,41,23)").getEmptyFormula());

  //   //testing that if the Formula doesn't have the parantheses(), it should return with "invalid" 
  //   assertEquals(Formula.INVALID, new Formula("d").getEmptyFormula());

  //   //testing that if the Formula isn't a recognized formula, it should also return invalid 
  //   assertEquals(Formula.UNRECOGNIZED, new Formula("DOODLE()").getEmptyFormula());
  // }

  // @Test 
  // public void getEmptyFormulaAndContent(){
  //   //testing that we're getting the content inside and the empty formula when the formula is right. 
  //   assertValidFormulaContentMap();

  //   //testing when the formula isn't recognized 
  //   Formula formula2 = new Formula("DOODLE(E10,E243)");
  //   assertInvalidFormulaContentMap(formula2);

  //   //testing when the formula doesn't have the right parantheses, thus invalid format 
  //   Formula formula3 = new Formula("SUM(E10E430");
  //   assertInvalidFormulaContentMap(formula3);

  //   //testing when the formula is flipped 
  //   Formula formula4 = new Formula("()SUM");
  //   assertInvalidFormulaContentMap(formula4);
  // } 

  // private void assertValidFormulaContentMap(){
  //   String dummyEmptyFormula = "SUM()";
  //   String dummyContent = "10,20,30,40";
  //   Formula dummyFormula = new Formula("SUM(" + dummyContent + ")");
  //   assertEquals(dummyContent, dummyFormula.getEmptyFormulaAndIdStrings().get(Formula.ID_STRINGS));
  //   assertEquals(dummyEmptyFormula, dummyFormula.getEmptyFormulaAndIdStrings().get(Formula.EMPTY_FORMULA));
  // } 

  // private void assertInvalidFormulaContentMap(Formula formula){
  //   assertEquals(Formula.INVALID, formula.getEmptyFormulaAndIdStrings().get(Formula.ID_STRINGS));
  //   assertEquals(Formula.INVALID, formula.getEmptyFormulaAndIdStrings().get(Formula.EMPTY_FORMULA));
  // }  



  


}
