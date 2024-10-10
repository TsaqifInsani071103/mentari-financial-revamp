package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.NumberInputHandler;

import java.lang.reflect.Method;
// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 
import java.util.HashMap;

//formula is basically just a tool for StatementsAndExpensehandler to use to basically calculate the valus of the formula strings in the expenses 
public class Formula {
  private FormulaNode validFormulaRootNode = null;
  private DataHandler dataHandler = null;
  //valid Formulas 
  private final String FORMULA_SUM = "SUM()";
  private final String FORMULA_MULTIPLY = "MULTIPLY()";



  public Formula(DataHandler dataHandler){
    this.dataHandler = dataHandler;

  } 
 
  
  public class FormulaAnatomy{
    private String formula;
    private int lastIndex;
    private int openingParanthesisIndex;
    public String parentFormula; 
    public String formulaContent; 
    public boolean isValid = false; 


    public FormulaAnatomy(String formula){
      this.formula = formula; 
      this.lastIndex = formula.length()-1; 
      this.openingParanthesisIndex = formula.indexOf('(');
      if(formulaParanthesesValid()){
        initializeParentFormulaAndNestedContent();
        if(recognizedFormula()){
          isValid = true; 
        }
      }
    }

    public String getFormulaWithoutFormulaContent(){
      return this.parentFormula;
    } 
    public String getFormulaContent(){
      return this.formulaContent;
    } 

    private boolean formulaParanthesesValid(){
      if(formula.charAt(lastIndex) != ')' || openingParanthesisIndex == -1 || !hasSameOpenAndClosingParantheses(this.formula)) return false;
      return true; 
    }

    public boolean hasSameOpenAndClosingParantheses(String formula){
      Stack<Character> paranthesesStack = new Stack<>();
      for(int i = 0; i < formula.length(); i++){
        if(formula.charAt(i) == '(') paranthesesStack.push('(');
        try{
          if(formula.charAt(i) == ')') paranthesesStack.pop(); 
        }catch(EmptyStackException error){
          //if this error happens that means theres an imbalance between chronological open parantheses and closing parantheses 
          return false; 
        }
      }
      if(paranthesesStack.size() == 0) return true; 
      return false; 
    }

    private void initializeParentFormulaAndNestedContent(){
      this.parentFormula = formula.substring(0, openingParanthesisIndex) + "()";
      this.formulaContent = formula.substring(openingParanthesisIndex + 1, lastIndex);
    } 

    private boolean recognizedFormula(){
      switch(this.parentFormula){
        case FORMULA_SUM:
          return true; 
        case FORMULA_MULTIPLY: 
          return true; 
      }
      return false; 
    }
  }

  //HARD CODED RIGHT NOW 
  public int getValueIfFormulaValid(String formula){
    if(isFormulaValid(formula)){
      //this is just to check 
      // validFormulaRootNode.printAllFormulas(0);
      //if its only a sole integer, just return the number 
      if(NumberInputHandler.validNumberFormat(validFormulaRootNode.getValue()) && validFormulaRootNode.getChildNodes().isEmpty()) return Integer.parseInt(validFormulaRootNode.getValue());

      return getValueOfNodesRecursively(this.validFormulaRootNode);
    }

    return 0; 
  } 

  //CLEAN THIS UP 
  public int getValueOfNodesRecursively(FormulaNode rootNode){
    int total = 0;
    String formula = rootNode.getValue();
    //if its a formula
    if(!NumberInputHandler.validNumberFormat(rootNode.getValue()) && !rootNode.getChildNodes().isEmpty()){
      if(formula.equals(FORMULA_MULTIPLY)) total = 1; 
      for(FormulaNode child: rootNode.getChildNodes()){
        if(NumberInputHandler.validNumberFormat(child.getValue())){
          total = calculateByFormulaIntoTotal(formula, Integer.parseInt(child.getValue()), total);
        }else{
          int calculatedInnerFormulaValue = getValueOfNodesRecursively(child); 
          total = calculateByFormulaIntoTotal(formula, calculatedInnerFormulaValue, total);
        } 
      }
    }
    return total; 

  } 
  
  //isFormulaValid creates a formulaNode that has sort of this chronology of what formulas need to be fired? 
  public boolean isFormulaValid(String formula){
    return makeValidFormulaNode(formula, null);
  } 


  public boolean makeValidFormulaNode(String formulaSubstring, FormulaNode rootNode){
    //edge cases 
    if(edgeCasesTrue_AddAsNode(formulaSubstring, rootNode)) return true; 

    FormulaAnatomy parsedFormula = new FormulaAnatomy(formulaSubstring);

    if(formulaIsValidId_AddAsNode(formulaSubstring, rootNode)) return true; 
    
    if(!parsedFormula.isValid) return false; 

    FormulaNode emptyFormulaNode = new FormulaNode(parsedFormula.getFormulaWithoutFormulaContent());// e.g SUM() in SUM(1,2,3), or SUM() in SUM(MULTIPLY())
    String[] formulaContentArray = parsedFormula.getFormulaContent().split(",");// e.g 1,2,3 in SUM(1,2,3) or MULTIPLY(1,2,3) in SUM(MULTIPLY(1,2,3))
    
    //handling the emptyFormulaNode 
    if(rootNode == null) {
      rootNode = emptyFormulaNode;
      this.validFormulaRootNode = rootNode;
    }else{
      rootNode.addChild(emptyFormulaNode);
    }

    //handling the formulaContentArray
    for(int i = 0; i < formulaContentArray.length; i++){
      String indivContent = formulaContentArray[i].trim();
      while(!parsedFormula.hasSameOpenAndClosingParantheses(indivContent) && i+1<formulaContentArray.length){
        indivContent = indivContent + "," + formulaContentArray[i+1];
        i++;
      }
      if(!makeValidFormulaNode(indivContent, emptyFormulaNode)) return false; 
    }

    return true; 
  }

  private boolean edgeCasesTrue_AddAsNode(String formulaSubstring, FormulaNode rootNode){
    //edge cases 
    if(rootNode == null){
      //empty user inputs are still valid and defaults to 0 
      if(formulaSubstring.equals("")){
        this.validFormulaRootNode = new FormulaNode("0");
        return true;
      }; 
      //if the user input is just an integer that is also valid. 
      if(NumberInputHandler.validNumberFormat(formulaSubstring)){
        this.validFormulaRootNode = new FormulaNode(formulaSubstring);
        return true; 
      }
    }else{
      //if the child node is empty, its true defaults to 0 
      if(formulaSubstring.equals("")) return true; 
      //if the child node is an integer, thts true 
      if(NumberInputHandler.validNumberFormat(formulaSubstring) && rootNode != null){
        rootNode.addChild(new FormulaNode(formulaSubstring));
        return true;
      }
    }
    return false; 
  }

  //change this next 
  private boolean formulaIsValidId_AddAsNode(String formulaSubstring, FormulaNode rootNode){
    String possibleValueFromId = dataHandler.getValueById(formulaSubstring);
    if(possibleValueFromId == null) return false; 
    if(rootNode != null){
      rootNode.addChild(new FormulaNode(possibleValueFromId));
      return true; 
    }else{
      validFormulaRootNode = new FormulaNode(possibleValueFromId);
      return true; 
    }
  }


  // System.out.println("TIS THE PROBLEM STRING " + indivContent);
  // System.out.println("NEW STRING " + indivContent);//!!!!!!!!!!!!!!!!!!
  // System.out.println("LOOKING AT: " + indivContent);//!!!!!!!!!!!!!!!!!!
  private int calculateByFormulaIntoTotal(String formula, int value, int total){
    switch(formula){
      case FORMULA_SUM:
        return SUM(value, total);
      case FORMULA_MULTIPLY:
        return MULTIPLY(value, total);
      default: 
        //change this to bigDecimal 
        return 0;
    }
  } 

  //change these into BigDecimals too. 
  private int SUM(int value, int total){
    total += value;
    return total;
  }

  private int MULTIPLY(int value, int total){
    total *= value;
    return total;
  }

  //change isInteger to isValidDecimalFormat




 


 
  






}
