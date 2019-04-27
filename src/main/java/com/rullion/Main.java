package com.rullion;

import com.rullion.director.ScheduleDirector;
import com.rullion.director.ScheduleDirectorImpl;
import com.rullion.exception.SchedulingTaskCyclicDependencyException;
import com.rullion.exception.SchedulingTaskInputException;
import com.rullion.parser.commandLineParser;

import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        main.commandLineRun();
    }

    private void commandLineRun() {
        commandLineParser parser = new commandLineParser();
        parser.read();
        ScheduleDirector director = new ScheduleDirectorImpl();
        director.setTasks(parser.getTasks());
        parser.getDependencies().stream()
                .forEach(dependency -> {
                    String[] splitDep = dependency.split(">");
                    director.addDependency(splitDep[0], splitDep[1]);
                });

        try {
            director.execute();
        } catch (SchedulingTaskInputException | SchedulingTaskCyclicDependencyException ex) {
            parser.writeLine(ex.getMessage());
        }


        String result = director.getResult().stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")).toString();
        parser.writeLine("Result: " + result);
    }

}
