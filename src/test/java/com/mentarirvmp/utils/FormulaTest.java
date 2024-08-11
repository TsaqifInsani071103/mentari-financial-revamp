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

      // First case: SUM(MULTIPLY()) is true  
      String dummyFormula1 = "SUM(MULTIPLY(1,2,,), SUM(SUM(1,2,3),2,3), MULTIPLY(1))";
      assertEquals(true, Formula.isFormulaValid(dummyFormula1));

      // Second case: SUM)( is false 
      String dummyFormula2 = "SUM)(";
      assertEquals(false, Formula.isFormulaValid(dummyFormula2));

      // Third case: SUM) is false 
      String dummyFormula3 = "SUM)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula3));

      // Fourth case: DUMMY() is false 
      String dummyFormula4 = "DUMMY()";
      assertEquals(false, Formula.isFormulaValid(dummyFormula4));

      // Fifth case: SUM(MULTIPLY(), SUM()) is false 
      String dummyFormula5 = "SUM(MULTIPLY(1,2), SUM(1,2,3, mult()))"; 
      assertEquals(false, Formula.isFormulaValid(dummyFormula5));

      // Sixth case: Nested and valid content
      String dummyFormula6 = "SUM(MULTIPLY(1,2,SUM(3)),SUM(4,MULTIPLY(5,6)),7)";
      assertEquals(true, Formula.isFormulaValid(dummyFormula6));

      // Seventh case: Unrecognized function ADD() 
      String dummyFormula7 = "SUM(1,2,ADD(3,4))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula7));

      // Eighth case: Extra closing parenthesis
      String dummyFormula8 = "SUM(1,2,3))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula8));

      // Ninth case: Missing closing parenthesis
      String dummyFormula9 = "SUM(MULTIPLY(1,2), SUM(3,4,5)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula9));

      // Tenth case: Empty parentheses
      String dummyFormula10 = "SUM()";
      assertEquals(true, Formula.isFormulaValid(dummyFormula10));

      // Eleventh case: Consecutive functions without separator
      String dummyFormula11 = "SUM(MULTIPLY(1,2)SUM(3,4))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula11));

      // Twelfth case: Valid nested and sequential formulas
      String dummyFormula12 = "SUM(MULTIPLY(1,2), SUM(3, MULTIPLY(4,5)))";
      assertEquals(true, Formula.isFormulaValid(dummyFormula12));

      // Thirteenth case: Non-numeric content inside parentheses
      String dummyFormula13 = "SUM(1,2,'text')MULTIPLY())";
      assertEquals(false, Formula.isFormulaValid(dummyFormula13));

      // Fourteenth case: Empty formula
      String dummyFormula14 = "";
      assertEquals(true, Formula.isFormulaValid(dummyFormula14));

      // Fifteenth case: Only parentheses with no content
      String dummyFormula15 = "()";
      assertEquals(false, Formula.isFormulaValid(dummyFormula15));

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
