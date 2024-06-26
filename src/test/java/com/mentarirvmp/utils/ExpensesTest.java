package com.mentarirvmp.utils;
//These are the annotations 
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//REMEMBER THAT IN JUNIT, EACH TEST METHOD S RUNS IN ITS OWN INSTANCE OF THE TEST CLASS, 
//basically every method insantiates the class, making its own scope basically. 
//also the test methods are ran at random so its not chronological to ensure that no method tests depend on one another, that they are independent. 

//this class was good for TDD practice but I don't think all the unit tests here are important except for the uniqueCounterIncremented method. 
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
    
    //testing incrementing and decrementing counter with respect to Expense Id's 
    Expenses.decrementCounter(); 
    Expenses fourthExpense = new Expenses("Fourth"); 
    assertEquals(thirdExpense.getId(), fourthExpense.getId()); 

    Expenses.incrementCounter(); 
    Expenses fifthExpense = new Expenses("Fifth");
    assertEquals(fourthExpense.getId()+ 2, fifthExpense.getId());

    Expenses.resetCounter(); 

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

  @Test 
  public void addChildTest(){
    String newName = "childOne"; 
    insertDummyChild(newName);
    assertEquals(newName, this.dummyExpense.getChildArray().get(0).getName());
  } 

  @Test 
  public void deleteChildTest(){
    insertDummyChild("child1");
    insertDummyChild("child2");
    Expenses dummyChild = this.dummyExpense.getChildArray().get(0);
    int dummyChildId = dummyChild.getId(); 
    this.dummyExpense.deleteChild(dummyChild);

    assertEquals(this.dummyExpense.getChildArray().size(), 1);
    
    //check the decrementing of delete child test 
    assertEquals(Expenses.getCurrentCounterValue(), dummyChildId); 
    assertEquals(Expenses.getCurrentCounterValue() + 1, this.dummyExpense.getChildArray().get(0).getId()); //the child2
  } 

  private void insertDummyChild(String newName){
    Expenses dummyChild = new Expenses(newName);
    this.dummyExpense.addChild(dummyChild);
  } 

  @Test 
  public void hasChildTest(){
    assertEquals(this.dummyExpense.hasChildren(), false);
    insertDummyChild("dummyName");
    assertEquals(this.dummyExpense.hasChildren(), true); 
  } 

  


  

  
}
