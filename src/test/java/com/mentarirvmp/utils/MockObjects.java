package com.mentarirvmp.utils;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import com.mentarirvmp.statements.Statement;


//this will hold static mock Expense objects and Statement objects for testing purposes. 
public class MockObjects {
  // public static final String STATEMENT_NAME = "dummyStatement"; 
  // public static final String "Expense" = "Expense";
  // public static final String DUMMY_FORMULA = "SUM()"; 

  public static Statement getDummyStatementObject(){
    Statement dummyStatement = new Statement("Dummy Statement");
    //use Random to make different random values 

    for(int i = 0; i < 5; i++){
      Expenses expenseDummy = new Expenses("Expense" + i);
      Random randObj = new Random(); 
      int randValue = randObj.nextInt(80);
      expenseDummy.setValue("" + randValue);

      dummyStatement.addExpense(expenseDummy);
      // System.out.println(expenseDummy.getValue());
    }

    //adding one child to each expenses to try and see if we can check nested children's ID. 
    int counter = 101; 
    for(Map.Entry<String, Expenses> mapElement : dummyStatement.getExpensesMap().entrySet()){
      //we're gonna add children 
      Expenses expenseDummy = new Expenses("Expense" + counter);
      Random randObj = new Random(); 
      int randValue = randObj.nextInt(80);
      expenseDummy.setValue("" + randValue);
      counter++; 

      dummyStatement.addExpenseToParent(expenseDummy, mapElement.getValue());
    }

    return dummyStatement; 
    
  } 




  // public static Statement initializeDummyStatement(){
  //   Statement dummyStatement = new Statement(STATEMENT_NAME);
  //   dummyStatement.setExpenseArray(getDummyExpenseArray());

  //   return dummyStatement;
  // } 

  // public static Statement initializeDummyStatement(ArrayList<Expenses> dummyExpenseArray){
  //   Statement dummyStatement = new Statement(STATEMENT_NAME);
  //   dummyStatement.setExpenseArray(dummyExpenseArray);

  //   return dummyStatement;
  // } 


  // // //make dummy Formula from dummyStatement; 
  // // public static Formula makeDummyFormulaFrom(Statement dummyStatement){
  // //   String content = ""; 
  // //   for(Expenses expense:dummyStatement.getExpenseArray()){
  // //     content += expense.getId(); 
  // //     content += ",";
  // //   }
  // //   String fullFormula = putContentInsideFormulaString(content); 
  // //   return new Formula(fullFormula); 
  // // } 

  // private static String putContentInsideFormulaString(String content){
  //   int firstParanthesisIndex = DUMMY_FORMULA.indexOf("(");
  //   int secondParanthesisIndex = DUMMY_FORMULA.indexOf(")");
  //   String fullFormula = DUMMY_FORMULA.substring(0, firstParanthesisIndex+1) + content + DUMMY_FORMULA.substring(secondParanthesisIndex);
  //   return fullFormula; 
  // } 

}
