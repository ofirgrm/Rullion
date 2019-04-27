package com.rullion.exception;

/**
 * Scheduling input exception
 *
 * @author Ofir Germansky
 */
public class SchedulingTaskInputException extends RuntimeException {

    public SchedulingTaskInputException(String msg) {
        super(msg);
    }

    public SchedulingTaskInputException(Exception ex) {
        super(ex);
    }

}
