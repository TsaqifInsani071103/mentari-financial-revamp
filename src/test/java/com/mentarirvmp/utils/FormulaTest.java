package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class FormulaTest {
  //extract this to MockObjects too later. 
  private String dummyContent = "E10, E2, E3";
  private String dummyEmptyFormula = "SUM()";
  private String dummyFormulaString = "SUM(" + dummyContent + ")";
  private Formula dummyFormulaObject = new Formula(dummyFormulaString); 

 

  
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
    assertEquals(dummyContent, this.dummyFormulaObject.getEmptyFormulaAndContent().get(Formula.CONTENT));
    assertEquals(dummyEmptyFormula, this.dummyFormulaObject.getEmptyFormulaAndContent().get(Formula.EMPTY_FORMULA));
  } 

  private void assertInvalidFormulaContentMap(Formula formula){
    assertEquals(Formula.INVALID, formula.getEmptyFormulaAndContent().get(Formula.CONTENT));
    assertEquals(Formula.INVALID, formula.getEmptyFormulaAndContent().get(Formula.EMPTY_FORMULA));
  } 

  //we want the code to work by: we check if the formula is valid, if it does, delete the outer formula SUM(DOODLE()) becomes DOODLE() and we continue with this. This means we need to check from left index to right index for the parsing logic. 

  //if content is valid we calculate the value 
  // @Test 
  // public void getNestedFormula(){
  //   ArrayList<Expenses> dummyExpenses = MockObjects.initializeDummyExpenses(); 
  //   for(Expenses i : dummyExpenses){
  //     System.out.println(i.getName());
  //     System.out.println(i.getValue());
  //   }

    
  // } 

  // //simple test first with only 20, 10, blabla without the complicated structuring first 
  // //we only calculate the contents that are valid basically. 
  @Test 
  public void getValueFromValidExpensesTest(){
   

  } 


}
