package by.timo.practice.parser.handler;

import by.timo.practice.model.EmployeeBase;

import java.util.List;

public interface RecordHandler {
    void handle(String[] fields, List<EmployeeBase> employees, List<String> errors, String cleaned);
}
