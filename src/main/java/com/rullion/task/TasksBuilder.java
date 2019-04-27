package com.rullion.task;

import com.rullion.exception.SchedulingTaskInputException;

import java.util.*;

public class TasksBuilder {

    private static TasksBuilder builder = null;

    private List<Task> taskList;

    private Map<String, Task> taskNameMap;

    private TasksBuilder() {
        this.taskList = new ArrayList<>();
        this.taskNameMap = new HashMap<>();
    }

    public static TasksBuilder getInstance() {
        if (builder == null) {
            builder = new TasksBuilder();
        }

        return builder;
    }

    public static void reset() {
        builder = new TasksBuilder();
    }

    public void addTask(String name) {
        validateTask(name);

        Task task = new Task(name);
        this.taskList.add(task);
        this.taskNameMap.put(name, task);
    }

    private void validateTask(String name) {
        if (this.taskNameMap.containsKey(name)) {
            throw new SchedulingTaskInputException(String.format("A task with the name: %s already exists.", name));
        }
    }

    public void addDependencies(Map.Entry<String, Set<String>> depEntry) {
        this.addDependencies(depEntry.getKey(), depEntry.getValue());
    }

    public void addDependencies(String slave, Set<String> masters) {
        masters.stream()
                .forEach(master -> this.addDependency(slave, master));
    }

    public void addDependency(String slave, String master) {
        this.validateDependency(slave, master);

        Task slaveTask = this.taskNameMap.get(slave);
        Task masterTask = this.taskNameMap.get(master);

        slaveTask.addDependency(masterTask);
        masterTask.addDependant(slaveTask);
    }

    private void validateDependency(String slave, String master) {
        if (!this.taskNameMap.containsKey(slave) || !this.taskNameMap.containsKey(master)) {
            throw new SchedulingTaskInputException(
                    String.format("Task doesn't exists. Please ensure both %s and %s are created", slave, master));
        }
        if (slave.equals(master)) {
            throw new SchedulingTaskInputException(String.format("The task %s can not be dependent on itself", slave));
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
