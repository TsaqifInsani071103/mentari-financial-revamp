package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaTest {
  private String dummyFormulaString = "=SUM()";
  private Formula dummyFormulaObject = new Formula(dummyFormulaString);
  
  @Test 
  public void getFormulaTest(){
    assertEquals(this.dummyFormulaObject.getFormula(), this.dummyFormulaString);
  } 

  @Test 
  public void getEmptyFormula(){
    //testing that the default =SUM() formula emptied out is still =SUM() 
    assertEquals(dummyFormulaString, dummyFormulaObject.getEmptyFormula());

    //testing that if the Formula doesn't have the parantheses(), it should return with "invalid" 
    assertEquals("invalid", new Formula("d").getEmptyFormula());

    //testing that if the Formula isn't a recognized formula, it should also return invalid 
    assertEquals("unrecognizedFormula", new Formula("=DOODLE()").getEmptyFormula());
  }

  @Test 
  public void getContentInsideFormula(){
    //get content if the formula is recognized 
    String dummyContent = "E10, E2, E3";
    String dummyFormula = "=SUM(" + dummyContent+ ")";
    assertEquals("E10, E2, E3", new Formula(dummyFormula).getContentInsideFormula());

    //now check content if the formula is not recognized. 
    
  } 

}
