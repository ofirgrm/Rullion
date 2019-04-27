package com.rullion.task;

import java.util.ArrayList;
import java.util.List;

/**
 * A task entity implemented as a tree node using a double linked references.
 * Resolved flag is used to indicate that the task has being flagged as scheduled.
 *
 * @author Ofir Germansky
 */
public class Task {

    private String name;
    private List<Task> dependants;
    private List<Task> dependencies;
    private boolean resolved;

    public Task(String name) {
        this.name = name;
        this.dependants = new ArrayList<>();
        this.dependencies = new ArrayList<>();
    }

    public void addDependant(Task dependant) {
        this.dependants.add(dependant);
    }

    public void addDependency(Task dependency) {
        this.dependencies.add(dependency);
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public String getName() {
        return name;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public boolean isResolved() {
        return resolved;
    }
}
