package com.mentarirvmp.utils;

import java.util.ArrayList;

//since expenses is just one line of data entry, I think its fine to inject associated statement into this? 

//but hide away the operations of Statement from this class. 

// since Statement and Expenses should just be POJO classes that means I'm going to have to make a service class to define how Statement.java and Expenses interact. 

public class Expenses {
  private String name; 
  private String description = ""; 
  private String value = ""; 
  public static int uniqueCounter = 0; 
  private int id; 
  private ArrayList<Expenses> childArray = new ArrayList<Expenses>();  

  public Expenses(String name){
    this.name = name; 
    this.id = uniqueCounter; 
    uniqueCounter++; 
  } 

  public String getName(){
    return this.name; 
  } 

  public void setName(String newName){
    this.name = newName; 
  }

  public String getValue(){
    return this.value; 
  }

  public void setValue(String newValue){
    this.value = newValue; 
  }

  public String getDescription(){
    return this.description; 
  } 

  public void setDescription(String newDescription){
    this.description = newDescription; 
  }


  public int getId(){
    return this.id; 
  } 

}

//Expense objects will have nested children baye.
