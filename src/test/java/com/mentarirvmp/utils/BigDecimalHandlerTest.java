package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class BigDecimalHandlerTest {

  @Test
  public void cleanCommasTest(){
    String numba = "12,000";
    assertEquals("12000", BigDecimalHandler.parseInputToBigDecimal(numba).toString());

  } 

  @Test 
  public void scientificNotationTest(){
    //only turns into big decimal when the adjusted exponent is less than -6
    String bigNumber = "0.0000000000321";
    BigDecimal potentialSN = BigDecimalHandler.parseInputToBigDecimal(bigNumber);
    assertTrue(BigDecimalHandler.checkIfScientificNotation(potentialSN.toString())); 

    //now we account for scientific notation and get the string by having it parsed by BigDecimal instead of directly calling BigDecimal.toString. 

    BigDecimalHandler DH = new BigDecimalHandler(potentialSN.toString());
    assertFalse(BigDecimalHandler.checkIfScientificNotation(DH.getCalculatedSumAsString()));
    


  } 



  
}
