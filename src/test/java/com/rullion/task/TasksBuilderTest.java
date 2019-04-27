package com.rullion.task;

import com.rullion.exception.SchedulingTaskInputException;
import com.rullion.task.TasksBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TasksBuilderTest {

    private TasksBuilder tasksBuilder;

    @Before
    public void setUp() throws Exception {
        this.tasksBuilder = TasksBuilder.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        TasksBuilder.reset();
    }

    @Test
    public void testAddTask() {
        TasksBuilder.getInstance().addTask("a");
        // assert no exception
    }

    @Test(expected = SchedulingTaskInputException.class)
    public void testAddDuplicateTask() {
        this.tasksBuilder.addTask("a");
        this.tasksBuilder.addTask("a");
    }

    @Test
    public void testAddDependency() {
        this.tasksBuilder.addTask("a");
        this.tasksBuilder.addTask("b");
        this.tasksBuilder.addDependency("a","b");
        // assert no exception
    }

    @Test(expected = SchedulingTaskInputException.class)
    public void testAddMissingDependency(){
        this.tasksBuilder.addTask("a");
        this.tasksBuilder.addDependency("a","b");
    }

    @Test(expected = SchedulingTaskInputException.class)
    public void testAddSameDependency() {
        this.tasksBuilder.addTask("a");
        this.tasksBuilder.addDependency("a","a");
    }

}