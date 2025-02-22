package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.MockObjects;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.ExpenseStatementHandler;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Map;

public class MockObjectsTest {
  
  @Test
  public void getDummyStatementTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    MockObjects.getAllContentIntoArray(dummyStatement).forEach((expense) -> {
      System.out.println(expense.getName()); 
    });
    //one more expense is the root expense 
    assertEquals(6, MockObjects.getAllContentIntoArray(dummyStatement).size()); 

    
  } 
}
