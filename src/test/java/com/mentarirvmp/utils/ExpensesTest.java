package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ExpensesTest {
  private String dummyName = "Testing"; 
  private int dummyId = 0; 
  private Expenses dummyExpense; 

  @BeforeEach 
  public void setUp(){
    this.dummyExpense = new Expenses(this.dummyName); 
  } 

  @Test
  public void getExpenseName(){
    assertEquals(this.dummyName, this.dummyExpense.getName());
  } 

  @Test 
  public void setExpenseName(){
    String newName = "testing2"; 
    this.dummyExpense.setName(newName);
    assertEquals(newName, this.dummyExpense.getName()); 
  } 

  @Test 
  public void getId(){
    assertEquals(this.dummyId, dummyExpense.getId());
  } 

  @Test 
  public void uniqueCounterIncremented(){
    //tests that the unique counter increases after every expense initialization. 
    assertEquals(1, dummyExpense.getId()); 
  } 
  
}
