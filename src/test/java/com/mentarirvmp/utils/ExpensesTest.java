package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//REMEMBER THAT IN JUNIT, EACH TEST METHOD S RUNS IN ITS OWN INSTANCE OF THE TEST CLASS, 
//basically every method insantiates the class, making its own scope basically. 
public class ExpensesTest {
  private String dummyName = "Testing"; 
  private int dummyId = 0; 
  private Expenses dummyExpense = new Expenses(this.dummyName); 


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
    System.out.println(dummyExpense.getName());
  } 

  @Test 
  public void uniqueCounterIncremented(){
    //tests that the unique counter increases after every expense initialization. 
    int oldCounter = Expenses.uniqueCounter;
    Expenses newExpense = new Expenses("testExpense"); 
    int newCounter = Expenses.uniqueCounter; 
    assertEquals(oldCounter + 1, newCounter); 
  } 


  
}
