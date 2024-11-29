package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.ExpenseStatementHandler;
import com.mentarirvmp.statements.TemplateStatement;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
public class TemplateStatementTest {
  
  @Test
  public void makeTemplateTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    int dummyStatementSize = MockObjects.getAllContentIntoArray(dummyStatement).size();

    Statement statementTemplate = TemplateStatement.duplicateIntoStatementTemplate(dummyStatement); 
    int templateStatementSize = MockObjects.getAllContentIntoArray(statementTemplate).size();

    assertEquals(dummyStatementSize, templateStatementSize); 



  } 
}
