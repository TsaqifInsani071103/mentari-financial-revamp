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

  
}
