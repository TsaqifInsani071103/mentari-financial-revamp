package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaTest {
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
    assertEquals(dummyContent, this.dummyFormulaObject.getEmptyFormulaAndContent().get("content"));
    assertEquals(dummyEmptyFormula, this.dummyFormulaObject.getEmptyFormulaAndContent().get("emptyFormula"));

    //testing when the sum isn't recognized 
    Formula formula2 = new Formula("DOODLE(E10,E243)");
    assertEquals(Formula.INVALID, formula2.getEmptyFormulaAndContent().get("content"));
    assertEquals(Formula.INVALID, formula2.getEmptyFormulaAndContent().get("emptyFormula"));
  } 


}
