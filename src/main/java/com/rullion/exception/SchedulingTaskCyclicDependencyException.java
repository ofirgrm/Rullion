package com.rullion.exception;

/**
 * Scheduling Cyclic dependency exception
 *
 * @author Ofir Germansky
 */
public class SchedulingTaskCyclicDependencyException extends RuntimeException {

    public SchedulingTaskCyclicDependencyException() {
        super("Error - this is a cyclic dependency");
    }
}
