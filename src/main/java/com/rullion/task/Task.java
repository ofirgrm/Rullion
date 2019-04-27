package com.rullion.task;

import java.util.ArrayList;
import java.util.List;

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

    public List<Task> getDependants() {
        return dependants;
    }

    public List<Task> getDependencies() {
        return dependencies;
    }

    public boolean hasDependencies() {
        return !dependencies.isEmpty();
    }

    public boolean isResolved() {
        return resolved;
    }
}
