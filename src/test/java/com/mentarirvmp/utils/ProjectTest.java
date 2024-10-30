package com.mentarirvmp.utils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class ProjectTest {

    // @Test
    // public void testProjectName() {
    //     String name = "newName";
    //     Project newProject = new Project(name);
    //     assertEquals(name, newProject.getProjectName());
    // }
}

//so the project holds a FilesTree Object which holds a linkedlist of Statement objects 

//I cant make the filesTree into an array because I want it to be able to nest child statements so It's fine that its a linked list, I'll just use a while loop instead of recursion now. 