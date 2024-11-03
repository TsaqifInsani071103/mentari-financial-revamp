package com.mentarirvmp.statements;

import java.util.ArrayList;

import com.mentarirvmp.utils.Expenses;
import com.mentarirvmp.utils.LoaderHelper;

public class TemplateStatement {
  private static ArrayList<Statement> statementTemplatesArray = new ArrayList<>();

  public static void addStatementTemplate(Statement statement){
    if(isUniqueTemplateName(statement)){
      statementTemplatesArray.add(turnIntoTemplate(statement)); 
    }else{
      // String warning = "The name you entered for the custom template already exists";
      // LoaderHelper.showPopupModal(warning, 500, 150);
    }
  }


  private static boolean isUniqueTemplateName(Statement newTemplate){
    if(getTemplateByName(newTemplate.getName()) == null) return true; 
    return false; 
  } 

  public static Statement getStatementFromTemplate(String name){
    Statement template = getTemplateByName(name); 
    if(template == null) template = statementTemplatesArray.get(0);
    Statement newStatement = turnIntoTemplate(template); 
    return newStatement; 

  } 

  //were accessing them by name yea? not ID? we can change it to ID later. 
  public static Statement getTemplateByName(String name){
    addDefaultStatement();
    for(Statement template:statementTemplatesArray){
      if(template.getName().equals(name)){
        return template;
      }
    }

    return null; //default template 
  } 

  public static void deleteTemplateByName(String name){
    Statement template = getTemplateByName(name);
    if(template != null){
      statementTemplatesArray.remove(template); 
    }
  } 

  private static void addDefaultStatement(){
    if(statementTemplatesArray.isEmpty()){
      Statement newOne = new Statement("Default");
      // ExpenseLabel makeshiftRoot = new ExpenseLabel("Expenses", newOne);
      // newOne.addAndInitializeExpenseRoot(makeshiftRoot);
      // statementTemplatesArray.add(newOne); 
    }
  } 
  
  //we basically make a copy of the template statemnet object so as to not pass a reference to the Objects existing in the static arrayList. 
  //error handle duplicate template names here 
  //make a non destructive duplicate of the Statement object 
  public static Statement turnIntoTemplate(Statement statement){
    //check if there are duplicate statement instances in the array 
    Statement newTemplate = new Statement(statement.getName()); 

    // for(Expenses expense : statement.getExpenseEntries()){
    //   Expenses newExpense = makeTemplateExpenses(expense, newTemplate);
    //   newTemplate.addAndInitializeExpenseRoot(newExpense); 
    // }

    return newTemplate; 
  } 

  private static Expenses makeTemplateExpenses(Expenses originalExpense, Statement statement){
    Expenses templateExpense = duplicate(originalExpense, statement);

    // for(Expenses child:originalExpense.getChildren()){
    //   templateExpense.addChild(makeTemplateExpenses(child, statement));
    // }

    return templateExpense; 
  } 

  public static Expenses duplicate(Expenses expense, Statement statement){
    // switch(expense.getType()){
    //   case ENTRY:
    //     ExpenseEntry entry = new ExpenseEntry(expense.getName(), statement);
    //     return entry; 
    //   case LABEL:
    //     ExpenseLabel label = new ExpenseLabel(expense.getName(), statement);
    //     return label; 
    //   case FORMULA:
    //     ExpenseFormula originalExpense = (ExpenseFormula) expense;
    //     ExpenseFormula expenseFormula = new ExpenseFormula(originalExpense.getName(), originalExpense.getEquation(), statement); 
    //     return expenseFormula; 
    // }
    return null; 

  }

  public static ArrayList<Statement> getStatementTemplateArray(){
    return statementTemplatesArray; 
  } 

  public static void setTemplateArray(ArrayList<Statement> modelArray){
    statementTemplatesArray = modelArray; 
  }
}
