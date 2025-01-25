package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import com.mentarirvmp.statements.Statement;

public class ExpenseStatementHandlerTest {
  
  // @Test 
  // public void getExpenseArrayFromEquationTest(){
  //   Statement dummyStatement = new Statement("dummyStatement"); 
  //   Expenses expense1 = new Expenses("expense1");
  //   Expenses expense2 = new Expenses("expense2");
  //   Expenses expense3 = new Expenses("expense3");
  //   dummyStatement.addExpense(expense1);
  //   dummyStatement.addExpense(expense2);
  //   dummyStatement.addExpense(expense3);

  //   ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
  //   ArrayList<Expenses> actualArray = dataHandler.getExpenseArrayFromEquation("SUM(E1, MULTIPLY(E2, SUM(E3)))");
  //   ArrayList<Expenses> expectedArray = new ArrayList<>();
  //   expectedArray.add(expense1);
  //   expectedArray.add(expense2);
  //   expectedArray.add(expense3);
  //   for(int i = 0; i < actualArray.size(); i++){
  //     assertEquals(expectedArray.get(i), actualArray.get(i));
  //   }

  //   //valid equation but no Expense Id's
  //   ArrayList<Expenses> actualArray2 = dataHandler.getExpenseArrayFromEquation("SUM(1,2,3)");
  //   assertEquals(0, actualArray2.size()); 
    

  // }

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
  // @Test 
  // public void getValueFromFormulaTest(){
  //   Statement dummyStatement = MockObjects.getDummyStatementObject();
  //   ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
   
  //   assertEquals(new BigDecimal("543"),  dataHandler.getDecimalValueFromEquation("SUM(123,420)")); 

  // } 

  @Test
  public void checkUniqueExpenseName(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
    dummyStatement.addExpense(new Expenses("duplicateName"));

    assertFalse(dataHandler.expenseNameUnique("duplicateName"));
    assertTrue(dataHandler.expenseNameUnique("nonDulpicateName"));

  } 

  @Test
  public void setExpenseValueIfValidTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);
    //dummy Expense with default 0.0 value 
    Expenses dummyExpense = new Expenses("Expense1");
    assertEquals("0.0", dummyExpense.getValue()); 

    String validEquation = "MULTIPLY(2,2)";
    dataHandler.ifEquationValidSetExpenseValue(dummyExpense, validEquation);
    assertEquals("4.0", dummyExpense.getValue());

    String falseEquation = "awdasd";
    dataHandler.setExpenseValueByFalseEquation(dummyExpense, falseEquation);
    assertEquals("0.0", dummyExpense.getValue()); 


  } 

  @Test 
  public void expensesRefreshedChronologicallyTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

    Expenses e1 = new Expenses("Expense1");
    Expenses e2 = new Expenses("Expense2");
    e2.setValue("10");
    Expenses e3 = new Expenses("Expense3");
    e3.setValue("20.0");
    dummyStatement.addExpense(e1);
    dummyStatement.addExpense(e2);
    dummyStatement.addExpense(e3);

    //E1 = MULTIPLY(e2,e3)
    String validEquation = String.format("MULTIPLY(%s,%s)", e2.getId(), e3.getId());
    dataHandler.ifEquationValidSetExpenseValue(e1, validEquation);
    assertEquals("200.0", e1.getValue());
    dataHandler.ifEquationValidSetExpenseValue(e3, "1.0");
    dataHandler.ifEquationValidSetExpenseValue(e2, "10.0");
    assertEquals("10.0", e1.getValue());

    

  } 

  @Test
public void expensesRefreshedChronologicallyWithMultipleDependenciesTest() {
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

    Expenses e1 = new Expenses("Expense1");
    Expenses e2 = new Expenses("Expense2");
    Expenses e3 = new Expenses("Expense3");
    Expenses e4 = new Expenses("Expense4");

    e2.setValue("5");
    e3.setValue("3");
    e4.setValue("2");

    dummyStatement.addExpense(e1);
    dummyStatement.addExpense(e2);
    dummyStatement.addExpense(e3);
    dummyStatement.addExpense(e4);

    // E1 = ADD(e2, MULTIPLY(e3, e4))
    String validEquation1 = String.format("SUM(%s, MULTIPLY(%s, %s))", e2.getId(), e3.getId(), e4.getId());

    dataHandler.ifEquationValidSetExpenseValue(e1, validEquation1);
    assertEquals("11.0", e1.getValue());

    // Update e3's value and check E1 is refreshed
    dataHandler.ifEquationValidSetExpenseValue(e3, "4");
    dataHandler.ifEquationValidSetExpenseValue(e2, "5");
    dataHandler.ifEquationValidSetExpenseValue(e4, "2");
    assertEquals("13.0", e1.getValue());

    // Update e4's value and check E1 is refreshed
    dataHandler.ifEquationValidSetExpenseValue(e4, "1");
    assertEquals("9.0", e1.getValue());
}

@Test
public void expensesRefreshedChronologicallyWithCircularDependencyTest() {
    Statement dummyStatement = new Statement("newStatement");
    ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

    Expenses e1 = new Expenses("Expense1");
    Expenses e2 = new Expenses("Expense2");

    e2.setValue("5.0");

    dummyStatement.addExpense(e1);
    dummyStatement.addExpense(e2);

    // E1 = ADD(e2, e1) -> Circular dependency
    String invalidEquation = String.format("ADD(%s, %s)", e2.getId(), e1.getId());
    dataHandler.ifEquationValidSetExpenseValue(e1, invalidEquation);

    assertEquals("0.0",e1.getValue()); // Value of E1 should remain unset due to circular dependency
}

@Test 
public void dependencyResolverTest(){
  Statement dummyStatement = new Statement("newStatement");
  ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

  Expenses E1 = new Expenses("expense1");
  E1.setValue("0.0");
  Expenses E2 = new Expenses("expense2");
  E2.setValue("10.0");
  Expenses E3 = new Expenses("expense3");
  E3.setValue("10.0");

  dummyStatement.addExpense(E1);
  dummyStatement.addExpense(E2);
  dummyStatement.addExpense(E3);

  String equationForE1 = "SUM(E2, E3)";
  dataHandler.ifEquationValidSetExpenseValue(E1, equationForE1);
  //E1 is supposed to be 10 + 20 = 30. 
  String topSortInString = Arrays.toString(dataHandler.dependencyResolver.getTopSortArray());
  boolean validOrder = topSortInString.equals("[expense3, expense2, expense1]") || topSortInString.equals("[expense2, expense3, expense1]");
  // System.out.println(topSortInString);

  assertTrue(validOrder);
  assertEquals("20.0", E1.getValue());
 
  String equationForE2 = "SUM(E3)";
  dataHandler.ifEquationValidSetExpenseValue(E2, equationForE2);
  String topSortInString2 = Arrays.toString(dataHandler.dependencyResolver.getTopSortArray());
  boolean validOrder2 = topSortInString2.equals("[expense3, expense2, expense1]");

  assertTrue(validOrder2);
  assertEquals("20.0", E1.getValue());

  //at this point, this is going to be cyclic 
  String equationForE3 = "SUM(E1)";
  dataHandler.ifEquationValidSetExpenseValue(E3, equationForE3);
  String topSortInString3 = Arrays.toString(dataHandler.dependencyResolver.getTopSortArray());
  //empty array for cyclic topSort
  assertEquals("null", topSortInString3);

  // System.out.println(E1.getValue());
  // System.out.println(E2.getValue());
  // System.out.println(E3.getValue());

  //now im going to break the cycle and make it acyclic again 
  String equationForE3New = "10.0";
  dataHandler.ifEquationValidSetExpenseValue(E3, equationForE3New);

  String topSortInString4 = Arrays.toString(dataHandler.dependencyResolver.getTopSortArray());
  // System.out.println(topSortInString4);
  // System.out.println(E3.getEquation());
  assertNotEquals("null", topSortInString4);




  

} 

@Test 
public void dependencyResolverTest2(){
  Statement dummyStatement = new Statement("newStatement");
  ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

  Expenses E1 = new Expenses("expense1");
  Expenses E2 = new Expenses("expense2");
  Expenses E3 = new Expenses("expense3");

  dummyStatement.addExpense(E1);
  dummyStatement.addExpense(E2);
  dummyStatement.addExpense(E3);

  dataHandler.ifEquationValidSetExpenseValue(E1, "8.0");
  dataHandler.ifEquationValidSetExpenseValue(E2, "10.0");
  dataHandler.ifEquationValidSetExpenseValue(E3, "20.0");


  String equationForE1 = "SUM(E2, E3)";
  dataHandler.ifEquationValidSetExpenseValue(E1, equationForE1); 
  assertTrue(dataHandler.dependencyResolver.getTopSortArray().length == 3 );
  assertEquals("30.0", E1.getValue());
  assertEquals("10.0", E2.getValue());
  assertEquals("20.0", E3.getValue());
  
  String cyclicEquationForE1 = "SUM(E2, E1)";
  dataHandler.ifEquationValidSetExpenseValue(E1, cyclicEquationForE1); 

  assertEquals("0.0", E1.getValue());
  assertEquals("10.0", E2.getValue());
  assertEquals("20.0", E3.getValue());

  dataHandler.ifEquationValidSetExpenseValue(E2, "15.0");
  dataHandler.ifEquationValidSetExpenseValue(E3, "25.0");
  assertEquals("0.0", E1.getValue());
  assertEquals("15.0", E2.getValue());
  assertEquals("25.0", E3.getValue());

  //if they're all integer or decimal values, and not formula values, they dont qualify to be put into topSort. which means topsosrt is null without the formulas
  assertNull(dataHandler.dependencyResolver.getTopSortArray());
} 

@Test 
public void dependencyResolverTest3(){
  Statement dummyStatement = new Statement("newStatement");
  ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

  Expenses E1 = new Expenses("expense1");
  Expenses E2 = new Expenses("expense2");
  Expenses E3 = new Expenses("expense3");
  Expenses E4 = new Expenses("expense4");
  Expenses E5 = new Expenses("expense5");
  dummyStatement.addExpense(E1);
  dummyStatement.addExpense(E2);
  dummyStatement.addExpense(E3);
  dummyStatement.addExpense(E4);
  dummyStatement.addExpense(E5);

  dataHandler.ifEquationValidSetExpenseValue(E1, "8.0");
  dataHandler.ifEquationValidSetExpenseValue(E2, "10.0");
  dataHandler.ifEquationValidSetExpenseValue(E3, "20.0");
  dataHandler.ifEquationValidSetExpenseValue(E4, "25.0");
  dataHandler.ifEquationValidSetExpenseValue(E5, "30.0");
  //we'll start off with two different topSortGraphs and then what happens when the two of them are eventually combined? 

  String equationForE1 = "SUM(E2, E3)";
  String equationForE4 = "MULTIPLY(E5, 2)"; 
  dataHandler.ifEquationValidSetExpenseValue(E1, equationForE1);
  dataHandler.ifEquationValidSetExpenseValue(E4, equationForE4);
  assertEquals("30.0", E1.getValue());
  assertEquals("60.0", E4.getValue());

  String equationForE4New = "SUM(E5, E1)";
  dataHandler.ifEquationValidSetExpenseValue(E4, equationForE4New);
  assertEquals("60.0", E4.getValue());

  dataHandler.ifEquationValidSetExpenseValue(E2, "20");
  //E1 becomes 40 , and therefore E4 needs to be 70.0
  assertEquals("70.0", E4.getValue());

} 

@Test 
public void adjListRefreshValueTest(){
  Statement dummyStatement =  new Statement("dummyStatement"); 
  Expenses expense1 = new Expenses("1");
  dummyStatement.addExpense(expense1);
  expense1.setValue("10");
  Expenses expense2 = new Expenses("2");
  dummyStatement.addExpense(expense2);
  expense2.setValue("10");
  Expenses expense3 = new Expenses("3");
  dummyStatement.addExpense(expense3);
  expense3.setValue("10");
  Expenses expense4 = new Expenses("4");
  dummyStatement.addExpense(expense4);
  expense4.setValue("10");
  Expenses expense5 = new Expenses("5");
  dummyStatement.addExpense(expense5);
  expense5.setValue("10");
  Expenses expense6 = new Expenses("6");
  dummyStatement.addExpense(expense6);
  expense6.setValue("10");
  Expenses expense7 = new Expenses("7");
  dummyStatement.addExpense(expense7);
  expense7.setValue("10");
  Expenses expense8 = new Expenses("8");
  dummyStatement.addExpense(expense8);
  expense8.setValue("10");
  Expenses expense9 = new Expenses("9");
  dummyStatement.addExpense(expense9);
  expense9.setValue("10");

  ExpenseStatementHandler dataHandler = new ExpenseStatementHandler(dummyStatement);

  String equation1 = String.format("SUM(%s, %s)", expense2.getId(), expense3.getId());
  String equation5 = String.format("SUM(%s, 10)", expense1.getId()); 
  String equation6 = String.format("MULTIPLY(%s, 10)", expense5.getId()); 
  String equation7 = String.format("MULTIPLY(%s, 10)", expense5.getId()); 
  String equation8 = String.format("SUM(%s, 10)", expense1.getId());
  String equation9 = String.format("SUM(%s, 10)", expense8.getId()); 

  //this makes it CYCLICAL 
  String equation3 = String.format("SUM(%s, 10)", expense9.getId());



  dataHandler.ifEquationValidSetExpenseValue(expense1, equation1); 
  dataHandler.ifEquationValidSetExpenseValue(expense5, equation5); 
  dataHandler.ifEquationValidSetExpenseValue(expense6, equation6); 
  dataHandler.ifEquationValidSetExpenseValue(expense7, equation7); 
  dataHandler.ifEquationValidSetExpenseValue(expense8, equation8); 
  dataHandler.ifEquationValidSetExpenseValue(expense9, equation9); 

  //THIS IS CYCLICAL 
  // dataHandler.ifEquationValidSetExpenseValue(expense3, equation3);

  //this gives directed edges toward E1 for E2 and E3. E2 -> E1, E3 -> E1. 
  //uncomment this to test cyclical 
  // assertEquals("0.0",expense3.getValue()); 

  //if I refresh the value of E5, then the values of E2, E3, E8, and E9 should not be refreshed. 

  String oldValue1 = expense1.getValue(); 
  String oldValue2 = expense2.getValue(); 
  String oldValue3 = expense3.getValue(); 
  String oldValue4 = expense4.getValue(); 
  String oldValue5 = expense5.getValue(); 
  String oldValue6 = expense6.getValue(); 
  String oldValue7 = expense7.getValue(); 
  String oldValue8 = expense8.getValue(); 
  String oldValue9 = expense9.getValue(); 
  dataHandler.ifEquationValidSetExpenseValue(expense5, "0");
  assertEquals(oldValue1, expense1.getValue()); 
  assertEquals(oldValue2, expense2.getValue()); 
  assertEquals(oldValue3, expense3.getValue()); 
  assertEquals(oldValue4, expense4.getValue()); 
  assertEquals(oldValue8, expense8.getValue()); 
  assertEquals(oldValue9, expense9.getValue()); 
  
  assertNotEquals(oldValue5, expense5.getValue());
  assertNotEquals(oldValue6, expense6.getValue());
  assertNotEquals(oldValue7, expense7.getValue());


  


} 

}