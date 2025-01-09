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
    String bigNumber = "-100000000000.0000000";
    BigDecimal potentialSN = BigDecimalHandler.parseInputToBigDecimal(bigNumber);
    System.out.println("POTENTIAL SN: " + potentialSN.toString());
  } 



  
}
