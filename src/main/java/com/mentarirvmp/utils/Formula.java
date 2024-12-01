package com.mentarirvmp.utils;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.mentarirvmp.utils.BigDecimalHandler;

import java.lang.reflect.Method;
// import com.mentari.statements.Statement;
// import com.mentari.utils.InputHandler;
import java.math.BigDecimal; 
import java.util.HashMap;

//formula is basically just a tool for StatementsAndExpensehandler to use to basically calculate the valus of the formula strings in the expenses 
public class Formula {
  private FormulaNode validFormulaRootNode = null;
  private DataHandler dataHandler;
  //valid Formulas 
  private final String FORMULA_SUM = "SUM()";
  private final String FORMULA_MULTIPLY = "MULTIPLY()";
  public final BigDecimal DEFAULT_VALUE = new BigDecimal("0"); 


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

  public BigDecimal getValueIfFormulaValid(String formula){
    if(isFormulaValid(formula)){
      //This checks if the rootNode is just an integer or a proper decimal 
      return getValueWhenFormulaValid();
    }else{
      return DEFAULT_VALUE; 
    }
  } 

  public BigDecimal getValueWhenFormulaValid(){
    if(isIntegerOrProperNumberFormat(validFormulaRootNode.getValue()) && validFormulaRootNode.getChildNodes().isEmpty()){ 
      return BigDecimalHandler.parseInputToBigDecimal(validFormulaRootNode.getValue());
      //This checks if its a FORMULA and not an empty FORMULA at that. unlike SUM() 
    }else if(!isIntegerOrProperNumberFormat(this.validFormulaRootNode.getValue()) && !this.validFormulaRootNode.getChildNodes().isEmpty()){
      return getValueOfNodesRecursively(this.validFormulaRootNode);
    }else{
      return DEFAULT_VALUE; 
    }


  }

  //CLEAN THIS UP 
  public BigDecimal getValueOfNodesRecursively(FormulaNode rootNode){
    BigDecimal total = new BigDecimal("0");
    String formula = rootNode.getValue();

    if(formula.equals(FORMULA_MULTIPLY)) total = new BigDecimal("1"); 
    for(FormulaNode child: rootNode.getChildNodes()){
      if(isIntegerOrProperNumberFormat(child.getValue())){
        total = calculateByFormulaIntoTotal(formula, BigDecimalHandler.parseInputToBigDecimal(child.getValue()), total);
      }else{
        BigDecimal calculatedInnerFormulaValue = getValueOfNodesRecursively(child); 
        total = calculateByFormulaIntoTotal(formula, calculatedInnerFormulaValue, total);
      } 
    }


    return total; 

  } 
  
  //isFormulaValid creates a formulaNode that has sort of this chronology of what formulas need to be fired? 
  public boolean isFormulaValid(String formula){
    return isFormulaNodeValid(formula, null);
  } 


  private boolean isFormulaNodeValid(String formulaSubstring, FormulaNode rootNode){
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
      if(!isFormulaNodeValid(indivContent, emptyFormulaNode)) return false; 
    }

    return true; 
  }

  //can Either be purely an integer, or in a proper Number format. 
  private boolean edgeCasesTrue_AddAsNode(String formulaSubstring, FormulaNode rootNode){
    if(isEmptyString(formulaSubstring)){
      if(rootNode == null){
        this.validFormulaRootNode = new FormulaNode(DEFAULT_VALUE.toString());
      }
      return true; 
    }

    if(isIntegerOrProperNumberFormat(formulaSubstring)){
      if(rootNode == null){
        this.validFormulaRootNode = new FormulaNode(formulaSubstring);
      }else{
        rootNode.addChild(new FormulaNode(formulaSubstring));
      }
      return true; 
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
  private BigDecimal calculateByFormulaIntoTotal(String formula, BigDecimal value, BigDecimal total){
    switch(formula){
      case FORMULA_SUM:
        return SUM(value, total);
      case FORMULA_MULTIPLY:
        return MULTIPLY(value, total);
      default: 
        //change this to bigDecimal 
        return DEFAULT_VALUE; 
    }
  } 

  //change these into BigDecimals too. 
  private BigDecimal SUM(BigDecimal value, BigDecimal total){
    total = total.add(value);
    return total;
  }

  private BigDecimal MULTIPLY(BigDecimal value, BigDecimal total){
    total = total.multiply(value);
    return total;
  }

  //change isInteger to isValidDecimalFormat
  private boolean isInteger(String str){
    try{
      Integer.parseInt(str);
    }catch(NumberFormatException e){
      return false; 
    }
    return true; 
  } 

  public boolean checkAllEdgeCases(String formula){
    if(isIntegerOrProperNumberFormat(formula) && isEmptyString(formula)){
      return true; 
    }
    return false; 

  } 

  private boolean isIntegerOrProperNumberFormat(String formula){
    if(isInteger(formula) || BigDecimalHandler.validNumberFormat(formula)) return true; 
    return false; 
  } 

  private boolean isEmptyString(String formula){
    if(formula.equals(""))return true;
    return false; 
  } 


 


 
  






}
