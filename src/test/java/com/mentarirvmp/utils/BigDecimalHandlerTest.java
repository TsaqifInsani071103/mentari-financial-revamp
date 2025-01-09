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
    BigDecimalHandler DH = new BigDecimalHandler(potentialSN);
    assertFalse(BigDecimalHandler.checkIfScientificNotation(DH.getCalculatedSumAsString()));

  } 


  @Test
  public void ValidNumberFormatTest() {
      // Valid numbers without commas
      assertTrue(BigDecimalHandler.validNumberFormat("123")); // Whole number
      assertTrue(BigDecimalHandler.validNumberFormat("123.45")); // With decimals
      assertTrue(BigDecimalHandler.validNumberFormat("-123.45")); // Negative with decimals
  
      // Valid numbers with commas
      assertTrue(BigDecimalHandler.validNumberFormat("1,234")); // Single comma
      assertTrue(BigDecimalHandler.validNumberFormat("12,345,678")); // Multiple commas
      assertTrue(BigDecimalHandler.validNumberFormat("12,345.678")); // Comma with decimals
      assertTrue(BigDecimalHandler.validNumberFormat("-12,345.678")); // Negative with comma and decimals
  
      // Edge cases
      assertTrue(BigDecimalHandler.validNumberFormat("0")); // Single zero
      assertTrue(BigDecimalHandler.validNumberFormat("-0")); // Negative zero
      assertTrue(BigDecimalHandler.validNumberFormat("0.00")); // Decimal-only zero
      assertTrue(BigDecimalHandler.validNumberFormat("123.0000")); // Trailing zeros in decimal
  
      // Invalid numbers
      assertFalse(BigDecimalHandler.validNumberFormat("123,45")); // Invalid comma placement
      assertFalse(BigDecimalHandler.validNumberFormat("1,23,456")); // Incorrect grouping of commas
      assertFalse(BigDecimalHandler.validNumberFormat("123..45")); // Multiple decimal points
      assertFalse(BigDecimalHandler.validNumberFormat("123.45.67")); // Multiple decimal points
      assertFalse(BigDecimalHandler.validNumberFormat("-")); // Only a negative sign
      assertFalse(BigDecimalHandler.validNumberFormat(".")); // Only a decimal point
      assertFalse(BigDecimalHandler.validNumberFormat("1,234.")); // Trailing comma without decimals
      assertFalse(BigDecimalHandler.validNumberFormat("abc123")); // Alphanumeric input
      assertFalse(BigDecimalHandler.validNumberFormat("")); // Empty string
      assertFalse(BigDecimalHandler.validNumberFormat(" ")); // Whitespace
  
      // Valid edge cases with decimal points
      assertTrue(BigDecimalHandler.validNumberFormat("0.123")); // Leading decimal (interpreted as 0.123)
      assertTrue(BigDecimalHandler.validNumberFormat("-0.123")); // Negative with leading decimal
  }
  



  
}
