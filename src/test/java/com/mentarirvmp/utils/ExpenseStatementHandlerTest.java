package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import com.mentarirvmp.statements.Statement;

public class ExpenseStatementHandlerTest {
  
  @Test
  public void getValueByIdTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

    for(Expenses expense: MockObjects.getAllContentIntoArray(dummyStatement)){
      assertEquals(expense.getValue(), dataHandler.getValueById(expense.getId()));
    }

    assertNull(dataHandler.getValueById("awdafdgsfdew"));
  } 

  //testing formula in DataHandler 
  @Test 
  public void getValueFromFormulaTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
   
    assertEquals(new BigDecimal("543"),  dataHandler.getDecimalValueFromEquation("SUM(123,420)")); 

  } 

}
