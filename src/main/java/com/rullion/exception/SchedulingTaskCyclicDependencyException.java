package com.rullion.exception;

public class SchedulingTaskCyclicDependencyException extends RuntimeException {

    public SchedulingTaskCyclicDependencyException() {
        super("Error - this is a cyclic dependency");
    }
}
