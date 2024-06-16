package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaTest {
  private String dummyFormulaString = "SUM(1,2,3,4)";
  private Formula dummyFormulaObject = new Formula(dummyFormulaString);
  
  @Test 
  public void getFormulaTest(){
    assertEquals(this.dummyFormulaObject.getFormula(), this.dummyFormulaString);
  } 

  @Test 
  public void getEmptyFormula(){
    //testing that the default =SUM() formula emptied out is still =SUM() 
    assertEquals("SUM()", dummyFormulaObject.getEmptyFormula());

    //testing that if the Formula doesn't have the parantheses(), it should return with "invalid" 
    assertEquals("invalid", new Formula("d").getEmptyFormula());

    //testing that if the Formula isn't a recognized formula, it should also return invalid 
    assertEquals("unrecognizedFormula", new Formula("DOODLE()").getEmptyFormula());
  }

  @Test 
  public void getContentInsideFormula(){
    //get content if the formula is recognized 
    String dummyContent = "E10, E2, E3";
    String dummyFormula = "SUM(" + dummyContent+ ")";
    assertEquals("E10, E2, E3", new Formula(dummyFormula).getContentInsideFormula());

  } 

  @Test 
  public void getEmptyFormulaAndContent(){
    String dummyContent = "E10, E2, E3";
    String dummyEmptyFormula = "SUM()";
    String dummyFormula = "SUM(" + dummyContent + ")";
    Formula formula1 = new Formula(dummyFormula);
    assertEquals(dummyContent, formula1.getEmptyFormulaAndContent().get("content"));
    assertEquals(dummyEmptyFormula, formula1.getEmptyFormulaAndContent().get("emptyFormula"));
  } 


}
