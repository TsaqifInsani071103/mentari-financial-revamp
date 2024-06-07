package com.mentarirvmp.utils;

//since expenses is just one line of data entry, I think its fine to inject associated statement into this? 

//but hide away the operations of Statement from this class. 

// since Statement and Expenses should just be POJO classes that means I'm going to have to make a service class to define how Statement.java and Expenses interact. 

public class Expenses {
  private String name; 
  private String description; 



  public Expenses(String name){
    this.name = name; 
  } 

  public String getName(){
    return this.name; 
  } 

  public void setName(String newName){
    this.name = newName; 
  }
}

//Expense objects will have nested children baye.
