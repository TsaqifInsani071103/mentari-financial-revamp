package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ExpensesTest {
  private String dummyName = "Testing"; 
  private Expenses dummyExpense; 

  @BeforeEach 
  public void setUp(){
    dummyExpense = new Expenses(dummyName); 
  } 

  @Test
  public void getExpenseName(){
    assertEquals(dummyName, dummyExpense.getName());
  } 

  @Test 
  public void setExpenseName(){
    String newName = "testing2"; 
    dummyExpense.setName(newName);
    assertEquals(newName, dummyExpense.getName()); 
  } 
  
}
