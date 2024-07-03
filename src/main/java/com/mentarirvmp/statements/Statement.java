package com.mentarirvmp.statements;
import java.util.ArrayList;
import com.mentarirvmp.utils.Expenses;

public class Statement {

  //If I want the expense class to just be a single class I don't think I would need an expenseCounter like I did with the original codebase. 
  private String statementName; 
  private Expenses INVALID_EXPENSE_ID = new Expenses("invalid"); 
  //
  //make like an array of hashmaps basically. 
  private ArrayList<Expenses> expenseArrayList = new ArrayList<Expenses>(); //these are top level sibling expenses 
  //but remember we need a default root expense so we could make nestable expense files and whatnot. 

  // private transient IndivProjectViewController projectViewController; for generating view purposes maybe, or I could just extract this into its own class instead. 

  //Think of statements as the piece of paper and the expenses as the writings on it. 
  //The projects hold several sheets of these papers. 

  public Statement(String statementName){
    this.statementName = statementName; 
  } 

  public String getName(){
    return this.statementName; 
  } 

  public void setName(String newName){
    this.statementName = newName; 
  } 

  public void addExpense(Expenses newExpense){
    this.expenseArrayList.add(newExpense);
    
  } 

  public void setExpenseArray(ArrayList<Expenses> expenseArray){
    this.expenseArrayList = expenseArray;

  }

  //I think right now the Statement object should take care of its own data, not that it meddles with the expenses data, but to make it a useless data structure, I would want searching the ID's and whatnot to transpire here?? 

  public ArrayList<Expenses> getExpenseArray(){
    return this.expenseArrayList; 
  } 

  public Expenses getExpenseById(int ID){
    return recursiveGetExpenseById(this.expenseArrayList, ID);
  } 

  
  private Expenses recursiveGetExpenseById(ArrayList<Expenses> expenseArray, int ID){
    Expenses foundExpense = checkSingleExpenseArrayByID(expenseArray, ID);
    return foundExpense; 
  } 

  private Expenses checkSingleExpenseArrayByID(ArrayList<Expenses> expenseArray, int ID){
    for(Expenses expense: expenseArray){
      if(expense.getId() == ID){
        return expense; 
      }else if(expense.hasChildren()){
        return recursiveGetExpenseById(expense.getChildArray(), ID);
      }
    }
    return this.INVALID_EXPENSE_ID;

  } 

  //In the original codebase, the Statement acts as a manager of the Expense objects, and funnily enough, the expense objects themselves have reference to the Statement objects too, so It's sort of a circular reference. 

  //We can have a handler class the acts as a middle man between Statement and Expenses "POJO"s such that it holds reference to the Statement using it and then also manipulating the Expense POJOs non-destructively and whatnot. 

  //if we're traversing the expenses by name we could just use a hashmap btw. 




}
