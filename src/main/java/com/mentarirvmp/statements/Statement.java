package com.mentarirvmp.statements;
import java.util.ArrayList;
import com.mentarirvmp.utils.Expenses;

public class Statement {

  //If I want the expense class to just be a single class I don't think I would need an expenseCounter like I did with the original codebase. 
  private String statementName; 
  private ArrayList<Expenses> expenseArrayList = new ArrayList<Expenses>(); 

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

  //In the original codebase, the Statement acts as a manager of the Expense objects, and funnily enough, the expense objects themselves have reference to the Statement objects too, so It's sort of a circular reference. 

  //We can have a handler class the acts as a middle man between Statement and Expenses "POJO"s such that it holds reference to the Statement using it and then also manipulating the Expense POJOs non-destructively and whatnot. 


}
