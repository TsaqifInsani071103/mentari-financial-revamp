package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;


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
      Formula frmlObject = new Formula();
      // First case: SUM(MULTIPLY()) is true  
      String dummyFormula1 = "SUM(MULTIPLY(1,2,,), SUM(SUM(1,2,3),2,3), MULTIPLY(1))";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula1));

      // Second case: SUM)( is false 
      String dummyFormula2 = "SUM)(";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula2));

      // Third case: SUM) is false 
      String dummyFormula3 = "SUM)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula3));

      // Fourth case: DUMMY() is false 
      String dummyFormula4 = "DUMMY()";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula4));

      // Fifth case: SUM(MULTIPLY(), SUM()) is false 
      String dummyFormula5 = "SUM(MULTIPLY(1,2), SUM(1,2,3, mult()))"; 
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula5));

      // Sixth case: Nested and valid content
      String dummyFormula6 = "SUM(MULTIPLY(1,2,SUM(3)),SUM(4,MULTIPLY(5,6)),7)";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula6));

      // Seventh case: Unrecognized function ADD() 
      String dummyFormula7 = "SUM(1,2,ADD(3,4))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula7));

      // Eighth case: Extra closing parenthesis
      String dummyFormula8 = "SUM(1,2,3))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula8));

      // Ninth case: Missing closing parenthesis
      String dummyFormula9 = "SUM(MULTIPLY(1,2), SUM(3,4,5)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula9));

      // Tenth case: Empty parentheses
      String dummyFormula10 = "SUM()";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula10));

      // Eleventh case: Consecutive functions without separator
      String dummyFormula11 = "SUM(MULTIPLY(1,2)SUM(3,4))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula11));

      // Twelfth case: Valid nested and sequential formulas
      String dummyFormula12 = "SUM(MULTIPLY(1,2), SUM(3, MULTIPLY(4,5)))";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula12));

      // Thirteenth case: Non-numeric content inside parentheses
      String dummyFormula13 = "SUM(1,2,'text')MULTIPLY())";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula13));

      // Fourteenth case: Empty formula
      String dummyFormula14 = "";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula14));

      // Fifteenth case: Only parentheses with no content
      String dummyFormula15 = "()";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula15));

      String dummyFormula16 = "SUM(())))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula16));

      // Second case: Misplaced characters in the formula
      String dummyFormula17 = "S(U)U(M)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula17));

      // Third case: Valid formula but with unnecessary nested empty parentheses
      String dummyFormula18 = "SUM((()))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula18));

      // Fourth case: Nested empty parentheses that should be invalid
      String dummyFormula19 = "SUM(MULTIPLY(()))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula19));

      // Fifth case: Multiple closing parentheses at the start
      String dummyFormula20 = ")))SUM(1,2,3)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula20));

      // Sixth case: Multiple opening parentheses with no closing pair
      String dummyFormula21 = "SUM(((1,2,3)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula21));

      // Seventh case: Correct functions but separated by incorrect characters
      String dummyFormula22 = "SUM(SUM)MULTIPLY()";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula22));

      // Eighth case: Functions with invalid nested parentheses and no content
      String dummyFormula23 = "SUM((MULTIPLY))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula23));

      // Ninth case: Empty SUM function nested within a valid function
      String dummyFormula24 = "SUM(MULTIPLY(SUM(), 1, 2))";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula24));

      // Tenth case: Multiple valid functions, but directly adjacent without commas
      String dummyFormula25 = "SUM(1,2,3)SUM(4,5,6)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula25));

      // Eleventh case: Parentheses around a function name instead of after it
      String dummyFormula26 = "(SUM)(1,2,3)";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula26));

      // Twelfth case: Nested invalid characters inside a function
      String dummyFormula27 = "SUM(1,2,MULTIPLY(1,@))";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula27));

      String dummyFormula28 = "2";
      assertEquals(true, frmlObject.isFormulaValid(dummyFormula28));

      String dummyFormula29 = "34l";
      assertEquals(false, frmlObject.isFormulaValid(dummyFormula29));

  } 

  @Test
  public void getValueIfFormulaValid(){
    Formula frmlObject = new Formula();

    String dummyFormula2 = "MULTIPLY()";
    assertEquals(0, frmlObject.getValueIfFormulaValid(dummyFormula2));

    String dummyFormula3 = "3";
   assertEquals(3, frmlObject.getValueIfFormulaValid(dummyFormula3));

    String dummyFormula4 = "SUM(1,2,3)";
   assertEquals(6, frmlObject.getValueIfFormulaValid(dummyFormula4));

    String dummyFormula5 = "SUM(1,2,3, SUM(1,2,3))";
   assertEquals(12, frmlObject.getValueIfFormulaValid(dummyFormula5));

    String dummyFormula6 = "MULTIPLY(3,3)";
   assertEquals(9, frmlObject.getValueIfFormulaValid(dummyFormula6));

    String dummyFormula7 = "MULTIPLY(MULTIPLY(3,0), 2,1)";
   assertEquals(0, frmlObject.getValueIfFormulaValid(dummyFormula7));
   
    //SUM(6, 34,7) => 47
   String dummyFormula8 = "SUM(MULTIPLY(1,2,SUM(3)),SUM(4,MULTIPLY(5,6)),7)";
   assertEquals(47, frmlObject.getValueIfFormulaValid(dummyFormula8));

   String dummyFormula9 = "SUM(MULTIPLY(SUM(1,2,3), 10), SUM())";
   assertEquals(60, frmlObject.getValueIfFormulaValid(dummyFormula9));
   String dummyFormula10 = "MULTIPLY(12)";
   assertEquals(12, frmlObject.getValueIfFormulaValid(dummyFormula10));

   String dummyFormula11 = "";
   assertEquals(0, frmlObject.getValueIfFormulaValid(dummyFormula11));

  } 
  

  @Test
  public void getValueIfExpenseIdValid(){
    Statement dummyStatement = new Statement("DummyStatement");
    Formula frmlObject = new Formula(dummyStatement);
    Expenses newExpense1 = new Expenses("Expense1");
    Expenses newExpense2 = new Expenses("Expense2");
    Expenses newExpense3 = new Expenses("Expense3");
    dummyStatement.addExpense(newExpense1);
    dummyStatement.addExpense(newExpense2);
    dummyStatement.addExpense(newExpense3);
    newExpense1.setValue("10");
    newExpense2.setValue("5");
    newExpense3.setValue("");
    String id1 = newExpense1.getId(); 
    String id2 = newExpense2.getId(); 
    String id3 = newExpense3.getId(); 
    String bogusId = "E100000_DummyStatement";


  
    String dummyFormula2 = "MULTIPLY("+id1+ ",10)";
    assertEquals(100, frmlObject.getValueIfFormulaValid(dummyFormula2));
    //the formula isn't valid
    String dummyFormula3 = "SUM("+id1+ "," + id2 +","+id3+")";
    assertEquals(15, frmlObject.getValueIfFormulaValid(dummyFormula3));

    String dummyFormula4 = "SUM("+id1+ "," + id2 +","+"bogusId"+")";
    assertEquals(0, frmlObject.getValueIfFormulaValid(dummyFormula4));
  } 
  
}
