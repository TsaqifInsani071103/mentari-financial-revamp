package com.mentarirvmp.utils;

import java.util.ArrayList;

//since expenses is just one line of data entry, I think its fine to inject associated statement into this? 

//but hide away the operations of Statement from this class. 

// since Statement and Expenses should just be POJO classes that means I'm going to have to make a service class to define how Statement.java and Expenses interact. 

//I think I could make the formula object to act like a static class that's juggled by the Statements and Expense Handler Test.java. 

public class Expenses {
  private String name; 
  private String description = ""; 
  private String value = ""; 
  public static int uniqueCounter = 0; 
  private int id; 
  private ArrayList<Expenses> childArray = new ArrayList<Expenses>(); 
  
  public static void incrementCounter(){
    uniqueCounter++; 
  } 

  public static void decrementCounter(){
    uniqueCounter--; 
  } 

  public static void resetCounter(){
    uniqueCounter = 0; 
  } 

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

  //set default value to be 0.0 aja 
  public void setValue(String newValue){
    this.value = newValue; 
  }

  public String getDescription(){
    return this.description; 
  } 

  public void setDescription(String newDescription){
    this.description = newDescription; 
  }


  //this needs to be completely unique 
  public int getId(){
    return this.id; 
  } 

  //since this is a POJO and not a like a singular data type we can still include things like addChild and removeChild that is tightly related to this here. 
  public void addChild(Expenses newChild){
    this.childArray.add(newChild); 
  } 

  public void deleteChild(Expenses childToDelete){
    this.childArray.remove(childToDelete); 
    decrementCounter();
  } 

  public ArrayList<Expenses> getChildArray(){
    return this.childArray; 
  } 


}

//Expense objects will have nested children baye.
