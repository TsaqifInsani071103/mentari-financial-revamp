package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.mentarirvmp.statements.Statement;

public class ExpenseStatementHandlerTest {
  
  @Test 
  public void getExpenseArrayFromEquationTest(){
    Statement dummyStatement = new Statement("dummyStatement"); 
    Expenses expense1 = new Expenses("expense1");
    Expenses expense2 = new Expenses("expense2");
    Expenses expense3 = new Expenses("expense3");
    dummyStatement.addExpense(expense1);
    dummyStatement.addExpense(expense2);
    dummyStatement.addExpense(expense3);

    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
    ArrayList<Expenses> actualArray = dataHandler.getExpenseArrayFromEquation("SUM(E1, MULTIPLY(E2, SUM(E3)))");
    ArrayList<Expenses> expectedArray = new ArrayList<>();
    expectedArray.add(expense1);
    expectedArray.add(expense2);
    expectedArray.add(expense3);
    for(int i = 0; i < actualArray.size(); i++){
      assertEquals(expectedArray.get(i), actualArray.get(i));
    }

    //valid equation but no Expense Id's
    ArrayList<Expenses> actualArray2 = dataHandler.getExpenseArrayFromEquation("SUM(1,2,3)");
    assertEquals(0, actualArray2.size()); 
    

  }

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

  @Test
  public void checkUniqueExpenseName(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
    dummyStatement.addExpense(new Expenses("duplicateName"));

    assertFalse(dataHandler.expenseNameUnique("duplicateName"));
    assertTrue(dataHandler.expenseNameUnique("nonDulpicateName"));

  } 

  // @Test 
  // public void checkFormulaSideEffectsTest(){
  //   //there are two main side effect operations in ExpenseStatementHandler.java 
  //   //verifyEquationMakeFormulaNode, verifies the validity of the equation and makes a formula Node as a field in ExpenseStatementHandler to be used as a class instance by all the Formula related methods in ExpenseStatementHandler.java 
  //   //the concern here is if I call for getDecimalValueFromEquation() and then call setExpenseValueByValidEquation in another place, the formula node will be the one left over by getDecimalValueFromEquation(). 

  //   //So, the use case here is, We always need to pair up checking for the equation, and making the valid formula node. 
  // } 

}
