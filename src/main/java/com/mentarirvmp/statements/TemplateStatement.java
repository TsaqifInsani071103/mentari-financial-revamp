package com.mentarirvmp.statements;

import java.util.ArrayList;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.LoaderHelper;
import com.mentarirvmp.utils.ExpenseStatementHandler; 
import java.util.HashMap;
import java.util.Map;

//Its fine that this is closely coupled with the inner working of Statement.java since it deals with templating Statement.java
public class TemplateStatement {
  private static ArrayList<Statement> statementTemplatesArray = new ArrayList<>();


  //purpose of template Statement 
  //turnIntoTemplate: Make a copy of the template statement object so as to not pass a reference to the Objects existing in the static arrayList. 
    //error handle duplicate template names here 
    //make a non-destructive duplicate of the Statement object 

  //makeTemplateExpenses: duplicate the expenses and ensures they have the right children 

  public static void addStatementTemplate(Statement statement){
    //checks for statement unique name 
    if(getStatementTemplate(statement.getName()) == null){
      statementTemplatesArray.add(duplicateIntoStatementTemplate(statement)); 
    }else{
      // String warning = "The name you entered for the custom template already exists";
      // LoaderHelper.showPopupModal(warning, 500, 150);
    }
  }

  //statements cannot have the same name either then 
  public static Statement duplicateIntoStatementTemplate(Statement original){
    Statement duplicateStatement = new Statement(original.getName() + "Template"); 
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

  public static Statement getStatementTemplate(String name){
    if(statementTemplatesArray.isEmpty()) addDefaultStatement();
    for(Statement template:statementTemplatesArray){
      if(template.getName().equals(name)){
        return template;
      }
    }

    Statement defaultStatementTemplate = statementTemplatesArray.get(0);
    return duplicateIntoStatementTemplate(defaultStatementTemplate); //default template 
  } 

  private static void addDefaultStatement(){
    Statement defaultStatement = new Statement("Default");
    Expenses expense1 = new Expenses("dummyExpense1"); 
    defaultStatement.addExpense(expense1);
    defaultStatement.addExpenseToParent(new Expenses("child"), expense1);
    defaultStatement.addExpense(new Expenses("dummyExpense2"));
    statementTemplatesArray.add(defaultStatement);
  } 

  public static ArrayList<Statement> getStatementTemplateArray(){
    return statementTemplatesArray; 
  } 

  public static void setTemplateArray(ArrayList<Statement> modelArray){
    statementTemplatesArray = modelArray; 
  }



  
  
}
