package com.rullion.director;

import java.util.*;

public abstract class ScheduleDirector {

    protected List<String> tasks;
    protected Map<String, Set<String>> dependencies;
    protected List<String> result;

    public ScheduleDirector() {
        this.dependencies = new HashMap<>();
    }

    public void execute() {
        this.result = new ArrayList<>();
        if (this.tasks != null && this.dependencies != null) {
            prepareInput();
            schedule();
        }
    }

    protected abstract void prepareInput();

    protected abstract void schedule();

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public ScheduleDirector addDependency(String slave, String master) {
        Set<String> masters = this.dependencies.get(slave);
        if (masters == null) {
            masters = new HashSet<>();
            this.dependencies.put(slave, masters);
        }
        masters.add(master);

        return this;
    }

    public List<String> getResult() {
        return result;
    }

}
