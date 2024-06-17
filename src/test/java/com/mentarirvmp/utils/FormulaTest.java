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
  private String INVALID = "invalid";
  private String UNRECOGNIZED = "unrecognizedFormula"; 
  
  @Test 
  public void getFormulaTest(){
    assertEquals(this.dummyFormulaString, this.dummyFormulaObject.getFormula());
  } 

  @Test 
  public void getEmptyFormula(){
    //testing that the default =SUM() formula emptied out is still =SUM() 
    assertEquals(this.dummyEmptyFormula, dummyFormulaObject.getEmptyFormula());

    //testing that if the Formula doesn't have the parantheses(), it should return with "invalid" 
    assertEquals(this.INVALID, new Formula("d").getEmptyFormula());

    //testing that if the Formula isn't a recognized formula, it should also return invalid 
    assertEquals(this.UNRECOGNIZED, new Formula("DOODLE()").getEmptyFormula());
  }

  @Test 
  public void getContentInsideFormula(){
    //get content if the formula is recognized 
    assertEquals(this.dummyContent, this.dummyFormulaObject.getContentInsideFormula());

  } 

  @Test 
  public void getEmptyFormulaAndContent(){
    // Formula formula1 = new Formula(dummyFormula);
    // assertEquals(dummyContent, formula1.getEmptyFormulaAndContent().get("content"));
    // assertEquals(dummyEmptyFormula, formula1.getEmptyFormulaAndContent().get("emptyFormula"));
  } 


}
