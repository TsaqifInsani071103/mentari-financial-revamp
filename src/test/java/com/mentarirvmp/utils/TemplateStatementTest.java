package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;

import com.mentarirvmp.statements.Statement;
import com.mentarirvmp.utils.ExpenseStatementHandler;
import com.mentarirvmp.statements.TemplateStatement;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
public class TemplateStatementTest {
  
  @Test
  public void makeTemplateTest(){
    Statement dummyStatement = MockObjects.getDummyStatementObject();
    ArrayList<Expenses> dummyStatementFlattened = MockObjects.getAllContentIntoArray(dummyStatement);

    Statement templateStatement = TemplateStatement.duplicateIntoStatementTemplate(dummyStatement); 
    ArrayList<Expenses> templateStatementFlattened = MockObjects.getAllContentIntoArray(templateStatement);

    assertEquals(dummyStatementFlattened.size(), templateStatementFlattened.size()); 

    //make a better test to ensure that each child has the same parent and same child names 
    for(int i =0; i < dummyStatementFlattened.size(); i++){
      assertEquals(dummyStatementFlattened.get(i).getName(), templateStatementFlattened.get(i).getName());

      // assertNotEquals(dummyStatementFlattened.get(i).getId(), templateStatementFlattened.get(i).getId()); 
    }





  } 

  @Test 
  public void getStatementTemplateTest(){
    //Checks if invalid statement query will just return the default statement. 
    Statement invalidStatement = TemplateStatement.getTemplateByName("12039123ij1i2ub3i1khb23kjb");
    assertEquals("Default", invalidStatement.getName());

    //Checks if you can add statementTemplate and get it back by querying for its name 
    Statement dummyStatement = MockObjects.getDummyStatementObject();

    TemplateStatement.addAsStatementTemplate(dummyStatement);
    Statement dummyStatementDuplicate = TemplateStatement.getTemplateByName(dummyStatement.getName()); 

    assertEquals(dummyStatement.getName(), dummyStatementDuplicate.getName());
    

    
  }


}
