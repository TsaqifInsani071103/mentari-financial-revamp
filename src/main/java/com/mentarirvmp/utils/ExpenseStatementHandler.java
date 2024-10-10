package com.mentarirvmp.utils;
import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.Expenses;
public class ExpenseStatementHandler implements DataHandler{
  Statement handledStatement; 
  public ExpenseStatementHandler(Statement statement){
    this.handledStatement = statement;

  }
  @Override
  public String getValueById(String ID) {
    String actualExpenseValue = "";
    Expenses checkedExpense; 
    checkedExpense=this.handledStatement.getExpenseById(ID);
    if(checkedExpense != Expenses.INVALID_EXPENSE){
      actualExpenseValue = checkedExpense.getValue(); 
      return actualExpenseValue; 
    }else{
      return null;
    }
  }

  //SHOULD BE RESPONSIBLE FOR MAKING A NEW EXPENSE AND ADDING IT TO THE STATEMENT 
  //SHOULD BE RESPONSIBLE TO QUERYING AN EXPENSE TO DELETE AND SIGNALING STATEMENT TO DELETE IT. 

  
}
