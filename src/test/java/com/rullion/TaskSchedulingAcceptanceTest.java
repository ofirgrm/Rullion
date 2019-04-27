package com.rullion;

import com.rullion.director.ScheduleDirector;
import com.rullion.director.ScheduleDirectorImpl;
import com.rullion.exception.SchedulingTaskCyclicDependencyException;
import com.rullion.task.TasksBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Acceptance criteria tests
 *
 * @author Ofir Germansky
 */
public class TaskSchedulingAcceptanceTest {

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
    public void testEmpty() {
        this.director.execute();

        assertTrue(this.director.getResult().isEmpty());
    }

    @Test
    public void testNonDependenceTasks() {
        this.director.setTasks(List.of("a", "b"));

        this.director.execute();

        assertThat(this.director.getResult(), is(List.of("a", "b")));
    }

    @Test
    public void testDependenceTasks() {
        this.director.setTasks(List.of("a", "b"));
        this.director.addDependency("a", "b");

        this.director.execute();

        assertThat(this.director.getResult(), is(List.of("b", "a")));
    }

    @Test
    public void testDualDependenceTasks() {
        this.director.setTasks(List.of("a", "b", "c", "d"));
        this.director
                .addDependency("a", "b")
                .addDependency("c", "d");

        this.director.execute();

        assertThat(this.director.getResult(), is(List.of("b", "a", "d", "c")));
    }

    @Test
    public void testTripleDependenceTasks() {
        this.director.setTasks(List.of("a", "b", "c"));
        this.director
                .addDependency("a", "b")
                .addDependency("b", "c");

        this.director.execute();

        assertThat(this.director.getResult(), is(List.of("c", "b", "a")));
    }

    @Test(expected = SchedulingTaskCyclicDependencyException.class)
    public void testCyclicDependency() {
        this.director.setTasks(List.of("a", "b", "c", "d"));
        this.director
                .addDependency("a", "b")
                .addDependency("b", "c")
                .addDependency("c", "a");

        this.director.execute();
    }

}
