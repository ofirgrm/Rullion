package com.rullion.director;

import com.rullion.exception.SchedulingTaskCyclicDependencyException;
import com.rullion.exception.SchedulingTaskInputException;
import com.rullion.task.TasksBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ScheduleDirectorInputTest {

    private ScheduleDirector director;

    @Before
    public void setUp() throws Exception {
        this.director = new ScheduleDirectorImpl();
    }

    @After
    public void tearDown() throws Exception {
        TasksBuilder.reset();
    }

    @Test
    public void testNoInput() {
        this.director.execute();
        // assert no exception
    }

    @Test
    public void testInput() {
        this.director.setTasks(List.of("a", "b", "c"));
        this.director
                .addDependency("a", "b")
                .addDependency("b", "c");

        this.director.execute();
    }

    @Test(expected = SchedulingTaskInputException.class)
    public void testDuplicateTasks() {
        this.director.setTasks(List.of("a", "b", "b"));
        this.director
                .addDependency("a", "b")
                .addDependency("b", "c");

        this.director.execute();
    }

    @Test(expected = SchedulingTaskInputException.class)
    public void testMissingDependency() {
        this.director.setTasks(List.of("a", "b"));
        this.director
                .addDependency("a", "b")
                .addDependency("b", "c");

        this.director.execute();
    }

    @Test(expected = SchedulingTaskInputException.class)
    public void testAddSameDependency() {
        this.director.setTasks(List.of("a"));
        this.director
                .addDependency("a", "a");

        this.director.execute();
    }

    @Test(expected = SchedulingTaskCyclicDependencyException.class)
    public void testCyclicDependencyNoRoot() {
        this.director.setTasks(List.of("a", "b"));

        this.director
                .addDependency("a", "b")
                .addDependency("b", "a");

        this.director.execute();
    }
}