package com.rullion.director;

import com.rullion.exception.SchedulingTaskCyclicDependencyException;
import com.rullion.task.Task;
import com.rullion.task.TasksBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.function.Predicate.not;

public class ScheduleDirectorImpl extends ScheduleDirector {

    private static final int TASK_SIZE = 50;

    private List<Task> taskList;

    @Override
    protected void prepareInput() {
        TasksBuilder builder = TasksBuilder.getInstance();
        this.tasks.stream().forEach(builder::addTask);
        this.dependencies.entrySet().stream().forEach(builder::addDependencies);
        this.taskList = builder.getTaskList();
    }

    @Override
    public void schedule() {
        this.taskList.stream().forEach(this::processTask);
    }

    private void processTask(Task task) {
        Set<Task> predecessors = new HashSet<>(TASK_SIZE);
        this.processTaskRec(task, predecessors);
    }

    private void processTaskRec(Task task, Set<Task> predecessors) {
        if (predecessors.contains(task)) {
            throw new SchedulingTaskCyclicDependencyException();
        }
        predecessors.add(task);
        if (!task.isResolved()) {
            // recursively resolve previous dependencies on the task
            task.getDependencies().stream()
                    .filter(not(Task::isResolved))
                    .forEach(depTask -> this.processTaskRec(depTask, predecessors));
            // all task dependencies are resolved, so the current task can also be resolved
            task.setResolved(true);
            this.result.add(task.getName());
        }
    }

}
