package com.mentarirvmp.utils;
import java.util.Map;
import java.util.function.Consumer;

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

// public void traverseThroughAllData(Consumer<Expenses> expenseConsumer) {
//     traverse(handledStatement.getRoot(), expenseConsumer); 
// }

// private void traverse(Expenses expense, Consumer<Expenses> expenseConsumer) {

//     expenseConsumer.accept(expense); // Process the current expense
    
//     for(Map.Entry<String, Expenses> mapElement : expense.getChildMap().entrySet()){
//       Expenses childExpense = mapElement.getValue(); 
//       traverse(childExpense, expenseConsumer); 
//     }
// }


  //SHOULD BE RESPONSIBLE FOR MAKING A NEW EXPENSE AND ADDING IT TO THE STATEMENT 
  //SHOULD BE RESPONSIBLE TO QUERYING AN EXPENSE TO DELETE AND SIGNALING STATEMENT TO DELETE IT. 

  
}
