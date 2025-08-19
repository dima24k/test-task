package by.timo.practice.parser.handler.impl;

import by.timo.practice.builder.EmployeeBuilder;
import by.timo.practice.model.Employee;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.parser.ParseRegistry;
import by.timo.practice.parser.handler.RecordHandler;
import by.timo.practice.validate.SbRecordValidator;

import java.util.List;

import static by.timo.practice.model.enums.PostType.EMPLOYEE;

public class EmployeeHandler implements RecordHandler {
    @Override
    public void handle(String[] fields, List<EmployeeBase> out, List<String> err, String cleaned) {
        if (!SbRecordValidator.strategies.get(EMPLOYEE).validate(fields)) {
            err.add(cleaned);
            return;
        }

        Employee employee = new EmployeeBuilder().build(fields);
        ParseRegistry.registerEmployee(employee);
        out.add(employee);
    }
}
