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

      String dummyFormula16 = "SUM(())))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula16));

      // Second case: Misplaced characters in the formula
      String dummyFormula17 = "S(U)U(M)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula17));

      // Third case: Valid formula but with unnecessary nested empty parentheses
      String dummyFormula18 = "SUM((()))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula18));

      // Fourth case: Nested empty parentheses that should be invalid
      String dummyFormula19 = "SUM(MULTIPLY(()))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula19));

      // Fifth case: Multiple closing parentheses at the start
      String dummyFormula20 = ")))SUM(1,2,3)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula20));

      // Sixth case: Multiple opening parentheses with no closing pair
      String dummyFormula21 = "SUM(((1,2,3)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula21));

      // Seventh case: Correct functions but separated by incorrect characters
      String dummyFormula22 = "SUM(SUM)MULTIPLY()";
      assertEquals(false, Formula.isFormulaValid(dummyFormula22));

      // Eighth case: Functions with invalid nested parentheses and no content
      String dummyFormula23 = "SUM((MULTIPLY))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula23));

      // Ninth case: Empty SUM function nested within a valid function
      String dummyFormula24 = "SUM(MULTIPLY(SUM(), 1, 2))";
      assertEquals(true, Formula.isFormulaValid(dummyFormula24));

      // Tenth case: Multiple valid functions, but directly adjacent without commas
      String dummyFormula25 = "SUM(1,2,3)SUM(4,5,6)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula25));

      // Eleventh case: Parentheses around a function name instead of after it
      String dummyFormula26 = "(SUM)(1,2,3)";
      assertEquals(false, Formula.isFormulaValid(dummyFormula26));

      // Twelfth case: Nested invalid characters inside a function
      String dummyFormula27 = "SUM(1,2,MULTIPLY(1,@))";
      assertEquals(false, Formula.isFormulaValid(dummyFormula27));

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
