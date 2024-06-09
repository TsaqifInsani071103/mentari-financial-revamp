package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//REMEMBER THAT IN JUNIT, EACH TEST METHOD S RUNS IN ITS OWN INSTANCE OF THE TEST CLASS, 
//basically every method insantiates the class, making its own scope basically. 
//also the test methods are ran at random so its not chronological to ensure that no method tests depend on one another, that they are independent. 
public class ExpensesTest {
  private String dummyName = "Testing"; 
  private Expenses dummyExpense = new Expenses(this.dummyName); 

  //I think its best if you learn how to make mock objects so you can test it better. 

  @Test
  public void getExpenseName(){
    assertEquals(this.dummyName, this.dummyExpense.getName());
  } 

  @Test 
  public void setNameTest(){
    String newName = "testing2"; 
    this.dummyExpense.setName(newName);
    assertEquals(newName, this.dummyExpense.getName()); 
  } 


  @Test
  public void getIdReturnsInteger() {
    int id = this.dummyExpense.getId(); 
    assertTrue(id >= 0); 
  }

  //testing that each expense have unique Id's 
  @Test
  public void uniqueCounterIncremented() {
    Expenses firstExpense = new Expenses("First");
    Expenses secondExpense = new Expenses("Second");
    Expenses thirdExpense = new Expenses("Third");

    assertEquals(firstExpense.getId() + 1, secondExpense.getId());
    assertEquals(secondExpense.getId() + 1, thirdExpense.getId());
  }

  @Test 
  public void getDescriptionTest(){
    assertEquals("", dummyExpense.getDescription());
  } 

  @Test 
  public void setDescriptionTest(){
    this.dummyExpense.setDescription("newDescription");
    //showing that we've changed the initial description 
    assertNotEquals("", this.dummyExpense.getDescription());
  } 

  @Test 
  public void getValueTest(){
    assertEquals("", dummyExpense.getValue());
  } 

  @Test 
  public void setValueTest(){
    String dummyValue = "100%";
    dummyExpense.setValue(dummyValue); 
    assertEquals(dummyValue, dummyExpense.getValue());
  } 


  
}
