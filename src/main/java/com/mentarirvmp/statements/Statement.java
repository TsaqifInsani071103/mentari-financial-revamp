package com.mentarirvmp.statements;
import java.util.ArrayList;
import com.mentarirvmp.utils.Expenses;

public class Statement {

  //If I want the expense class to just be a single class I don't think I would need an expenseCounter like I did with the original codebase. 
  private String statementName; 
  private int uniqueCounter = 0; 
  //
  //make like an array of hashmaps basically. 
  private ArrayList<Expenses> expenseArrayList = new ArrayList<Expenses>(); 

  public Statement(String statementName){
    this.statementName = statementName; 
  } 

  public String getName(){
    return this.statementName; 
  } 

  public void setName(String newName){
    this.statementName = newName; 
  } 

  //this should be addExpense(String) 
  public void addExpense(Expenses newExpense){
    this.expenseArrayList.add(newExpense);
    
  } 

  public void setExpenseArray(ArrayList<Expenses> expenseArray){
    this.expenseArrayList = expenseArray;

  }

  private void setIdFor(Expenses expense){
    String newId = "E" + this.uniqueCounter + "_" + this.statementName;
    expense.setId(newId);
    this.uniqueCounter++; 
  }

  //I think right now the Statement object should take care of its own data, not that it meddles with the expenses data, but to make it a useless data structure, I would want searching the ID's and whatnot to transpire here
  public ArrayList<Expenses> getExpenseArray(){
    return this.expenseArrayList; 
  } 

  //Might have to change this into a linked HashMap Anyways.
  public Expenses getExpenseById(String ID){
    return recursiveGetExpenseById(this.expenseArrayList, ID);
  } 

  private Expenses recursiveGetExpenseById(ArrayList<Expenses> expenseArray, String ID){
    for(Expenses expense: expenseArray){
      if(expense.getId() == ID){
        return expense; 
      } else if(expense.hasChildren()){
        Expenses foundExpense = recursiveGetExpenseById(expense.getChildArray(), ID); 
        if(foundExpense != Expenses.INVALID_EXPENSE) return foundExpense;
      }
    }
    return Expenses.INVALID_EXPENSE; 


  } 


  //In the original codebase, the Statement acts as a manager of the Expense objects, and funnily enough, the expense objects themselves have reference to the Statement objects too, so It's sort of a circular reference. We dont want this 

  //We can have a handler class the acts as a middle man between Statement and Expenses "POJO"s such that it holds reference to the Statement using it and then also manipulating the Expense POJOs non-destructively and whatnot. 

  //if we're traversing the expenses by name we could just use a hashmap btw. 




}
