package com.mentari.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mentarirvmp.utils.Project; 

public class ProjectTest {

    @Test
    public void testProjectName() {
        String name = "newName";
        Project newProject = new Project(name);
        assertEquals(name, newProject.getProjectName());
    }
}