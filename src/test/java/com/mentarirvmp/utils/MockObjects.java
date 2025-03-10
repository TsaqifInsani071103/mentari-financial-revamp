package com.mentarirvmp.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Map;
import com.mentarirvmp.statements.Statement;


//this will hold static mock Expense objects and Statement objects for testing purposes. 
public class MockObjects {
  // public static final String STATEMENT_NAME = "dummyStatement"; 
  // public static final String "Expense" = "Expense";
  // public static final String DUMMY_FORMULA = "SUM()"; 

  //no justification for this code here 
  public static Project getDummyProjectObject(){
    Project project = new Project("dummyProject"); 

    return project; 
  } 

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



    return dummyStatement; 
    
  } 

  public static ArrayList<Expenses> getAllContentIntoArray(Statement dummyStatement){
    ArrayList<Expenses> expenseList = new ArrayList<Expenses>();
    for(Object object: dummyStatement.getAllUnderlyingStatementData()){
      Expenses expense = (Expenses) object;
      expenseList.add(expense);
    }
    return expenseList;
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
