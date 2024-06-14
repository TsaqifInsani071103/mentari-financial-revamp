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
    assertEquals(dummyFormulaObject.getEmptyFormula(), dummyFormulaString);

    //testing that if the Formula doesn't have the parantheses(), it should return with "invalid" 
    assertEquals(new Formula("d").getEmptyFormula(), "invalid");
  }

  @Test 
  public void getContentInsideFormula(){
    
  } 

}
