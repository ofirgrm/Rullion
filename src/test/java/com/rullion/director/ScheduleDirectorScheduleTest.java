package com.rullion.director;

import com.rullion.exception.SchedulingTaskCyclicDependencyException;
import com.rullion.task.TasksBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Director schedule tests
 *
 * @author Ofir Germansky
 */
public class ScheduleDirectorScheduleTest {

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
    public void testSimpleSchedule() {
        this.director.setTasks(List.of("a", "b", "c"));
        this.director.addDependency("a", "b").addDependency("b", "c");

        this.director.execute();

        List<String> expected = List.of("c", "b", "a");
        assertThat(this.director.getResult(), is(expected));
    }

    @Test
    public void testMediumSchedule() {
        this.director.setTasks(List.of("a", "b", "c", "d", "e", "f", "g"));
        this.director
                .addDependency("a", "b")
                .addDependency("d", "b")
                .addDependency("e", "b")
                .addDependency("e", "c")
                .addDependency("b", "c")
                .addDependency("b", "f")
                .addDependency("g", "b")
                .addDependency("g", "f");

        this.director.execute();

        List<String> expected = List.of("c", "f", "b", "a", "d", "e", "g");
        assertThat(this.director.getResult(), is(expected));
    }

    @Test(expected = SchedulingTaskCyclicDependencyException.class)
    public void testCyclicDependency() {
        this.director.setTasks(List.of("a", "b", "c", "d", "e"));
        this.director
                .addDependency("a", "b")
                .addDependency("b", "c")
                .addDependency("c", "a")
                .addDependency("d", "e");

        this.director.execute();
        System.out.println(this.director.getResult());
    }
}