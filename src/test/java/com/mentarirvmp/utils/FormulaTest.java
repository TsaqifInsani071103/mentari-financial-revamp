package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class FormulaTest {
  private String dummyContent = "E10, E2, E3";
  private String dummyEmptyFormula = "SUM()";
  private String dummyFormulaString = "SUM(" + dummyContent + ")";
  private Formula dummyFormulaObject = new Formula(dummyFormulaString); 
  private ArrayList<Expenses> dummyExpensesArray = initializeDummyExpenses(); 
 
  private ArrayList<Expenses> initializeDummyExpenses(){
    Expenses expense1 = new Expenses("Expense1");
    expense1.setValue("10");
    Expenses expense2 = new Expenses("Expense2");
    expense2.setValue("5");
    Expenses expense3 = new Expenses("Expense3");
    expense3.setValue("2");
    Expenses expense4 = new Expenses("Expense4");
    expense4.setValue("20");
    Expenses expense5 = new Expenses("Expense5");
    expense5.setValue("25");
    Expenses expense6 = new Expenses("Expense6");
    expense6.setValue("30");
    ArrayList<Expenses> dummyExpenses = new ArrayList<Expenses>();
    dummyExpenses.add(expense1);
    dummyExpenses.add(expense2);
    dummyExpenses.add(expense3);
    dummyExpenses.add(expense4);
    dummyExpenses.add(expense5);
    dummyExpenses.add(expense6);
    return dummyExpenses;
  } 
  
  @Test 
  public void getFormulaTest(){
    assertEquals(this.dummyFormulaString, this.dummyFormulaObject.getFormula());
  } 

  @Test 
  public void getEmptyFormula(){
    //testing that the default =SUM() formula emptied out is still =SUM() 
    assertEquals(this.dummyEmptyFormula, dummyFormulaObject.getEmptyFormula());

    //testing that if the Formula doesn't have the parantheses(), it should return with "invalid" 
    assertEquals(Formula.INVALID, new Formula("d").getEmptyFormula());

    //testing that if the Formula isn't a recognized formula, it should also return invalid 
    assertEquals(Formula.UNRECOGNIZED, new Formula("DOODLE()").getEmptyFormula());
  }

  @Test 
  public void getContentInsideFormula(){
    //get content if the formula is recognized 
    assertEquals(this.dummyContent, this.dummyFormulaObject.getContentInsideFormula());

  } 

  @Test 
  public void getEmptyFormulaAndContent(){
    //testing that we're getting the content inside and the empty formula when the formula is right. 
    assertValidFormulaContentMap();

    //testing when the formula isn't recognized 
    Formula formula2 = new Formula("DOODLE(E10,E243)");
    assertInvalidFormulaContentMap(formula2);

    //testing when the formula doesn't have the right parantheses, thus invalid format 
    Formula formula3 = new Formula("SUM(E10E430");
    assertInvalidFormulaContentMap(formula3);

    //testing when the formula is flipped 
    Formula formula4 = new Formula("()SUM");
    assertInvalidFormulaContentMap(formula4);
  } 

  private void assertValidFormulaContentMap(){
    assertEquals(dummyContent, this.dummyFormulaObject.getEmptyFormulaAndContent().get("content"));
    assertEquals(dummyEmptyFormula, this.dummyFormulaObject.getEmptyFormulaAndContent().get("emptyFormula"));
  } 

  private void assertInvalidFormulaContentMap(Formula formula){
    assertEquals(Formula.INVALID, formula.getEmptyFormulaAndContent().get("content"));
    assertEquals(Formula.INVALID, formula.getEmptyFormulaAndContent().get("emptyFormula"));
  } 

  //we want the code to work by: we check if the formula is valid, if it it, delete the outer formula SUM(DOODLE()) becomes DOODLE() and we continue with this. 

  //if content is valid we calculate the value 
  @Test 
  public void getNestedFormula(){

  } 

  //simple test first with only 20, 10, blabla without the complicated structuring first 
  @Test 
  public void calculateFormulaValue(){
    int firstExpenseId = this.dummyExpensesArray.get(0).getId();
    int secondExpenseId = this.dummyExpensesArray.get(1).getId();
    int thirdExpenseId = this.dummyExpensesArray.get(2).getId();

    String firstFormulaString = String.format("SUM(%d,%d,%d)", firstExpenseId, secondExpenseId, thirdExpenseId);
    Formula firstFormulaObject = new Formula(firstFormulaString); 
    

  } 


}
