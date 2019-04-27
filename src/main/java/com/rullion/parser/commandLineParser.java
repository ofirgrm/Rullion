package com.rullion.parser;

import com.rullion.exception.SchedulingTaskInputException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class commandLineParser {

    private List<String> tasks;
    private List<String> dependencies;

    public commandLineParser() {
        this.tasks = new ArrayList<>();
        this.dependencies = new ArrayList<>();
    }

    public void read() {
        this.writeLine(" ------------------------------------------------------------------ ");
        this.writeLine("| Command line interface for the screening task.                    |");
        this.writeLine("| Please notice there are no validations on the command line input. |");
        this.writeLine(" ------------------------------------------------------------------ ");
        this.writeLine("Add a comma separated list of tasks:");
        this.parseTasks(this.readLine());
        this.writeLine("Add a list of comma separated list of dependencies using > character (e.g. a>b,b>c):");
        this.parseDependencies(this.readLine());
    }

    private void parseTasks(String line) {
        this.tasks = List.of(line.split(","));
    }

    private void parseDependencies(String line) {
        this.dependencies = List.of(line.split(","));
    }

    private String readLine() {
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException ex) {
            throw new SchedulingTaskInputException(ex);
        }
    }

    public void writeLine(String str) {
        try {
            BufferedWriter writer =
                    new BufferedWriter((new OutputStreamWriter(System.out)));
            writer.write(str);
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            throw new SchedulingTaskInputException(ex);
        }
    }

    public List<String> getTasks() {
        return tasks;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

}