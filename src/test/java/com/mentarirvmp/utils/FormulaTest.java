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
    // String dummyFormula = "SUM(MULTIPLY(12, 32, SUM(32,32)), SUM(32, 43,SUM(3,3)))"; 
    String dummyFormula = "SUM(MULTIPLY())";
    //first case SUM(MULTIPLY()) is true  
    assertEquals(true, Formula.isFormulaValid(dummyFormula));

    //second case SUM)( is false 
    String dummyFormula2= "SUM)(";
    assertEquals(false, Formula.isFormulaValid(dummyFormula2));

    //third case SUM) is false 
    String dummyFormula3 = "SUM)";
    assertEquals(false, Formula.isFormulaValid(dummyFormula3));

    //fourth case DUMMY() is false 
    String dummyFormula4 = "DUMMY()";
    assertEquals(false, Formula.isFormulaValid(dummyFormula4));

    //fifth case SUM(MULTIPLY(), SUM()) is true 
    String dummyFormula5 = "SUM(MULTIPLY(), SUM())";
    assertEquals(true, Formula.isFormulaValid(dummyFormula5));
    

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
