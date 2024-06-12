package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class FormulaTest {
  
  @Test 
  public void getFormulaTest(){
    assertEquals(new Formula("=SUM()").getFormula(), "=SUM()");
  } 

}
