package com.mentarirvmp.statements;

import java.util.ArrayList;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.LoaderHelper;
import com.mentarirvmp.utils.ExpenseStatementHandler; 
import java.util.HashMap;
import java.util.Map;

//Its fine that this is closely coupled with the inner working of Statement.java since it deals with templating Statement.java
public class TemplateStatement {
  //I might just use DataHandler for this? 

  //purpose of template Statement 
  //turnIntoTemplate: Make a copy of the template statement object so as to not pass a reference to the Objects existing in the static arrayList. 
    //error handle duplicate template names here 
    //make a non-destructive duplicate of the Statement object 

  //makeTemplateExpenses: duplicate the expenses and ensures they have the right children 

  //statements cannot have the same name either then 
  public static Statement duplicateIntoStatementTemplate(Statement original){
    Statement duplicateStatement = new Statement(original.getName() + "Copy"); 
    ExpenseStatementHandler expenseStatementHandler = new ExpenseStatementHandler(original);
    Map<String, Expenses> expenseCopiesMap = new HashMap<String, Expenses>(); 

    expenseStatementHandler.traverseThroughAllData((expense, parentExpense) -> {
      Expenses expenseCopy = new Expenses(expense.getName()); 
      if(parentExpense == original.getRoot()){
        duplicateStatement.addExpense(expenseCopy);
        expenseCopiesMap.put(expenseCopy.getName(), expenseCopy); 
      }else if(parentExpense != null){
        Expenses parentExpenseCopy = expenseCopiesMap.get(parentExpense.getName());
        duplicateStatement.addExpenseToParent(expenseCopy, parentExpenseCopy);
        expenseCopiesMap.put(expenseCopy.getName(), expenseCopy); 
      }
    });

    return duplicateStatement;  

  } 


  
  
}
