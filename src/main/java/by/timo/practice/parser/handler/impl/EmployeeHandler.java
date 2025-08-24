package by.timo.practice.parser.handler.impl;

import by.timo.practice.model.Employee;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.parser.ParseRegistry;
import by.timo.practice.parser.handler.RecordHandler;
import by.timo.practice.util.SbRecordConstants;
import by.timo.practice.validate.SbRecordValidator;
import by.timo.practice.validate.strategy.ValidationStrategy;

import java.math.BigDecimal;
import java.util.List;

import static by.timo.practice.type.PostType.EMPLOYEE;

public class EmployeeHandler implements RecordHandler {
    @Override
    public void handle(String[] fields, List<EmployeeBase> employees, List<String> err, String cleaned) {
        ValidationStrategy validationStrategy = SbRecordValidator.strategies.get(EMPLOYEE);

        if (!validationStrategy.validate(fields)) {
            err.add(cleaned);
            return;
        }

        Employee employee = Employee.builder()
                .post(fields[SbRecordConstants.POST_INDEX])
                .id(Long.parseLong(fields[SbRecordConstants.ID_INDEX]))
                .name(fields[SbRecordConstants.NAME_INDEX])
                .salary(new BigDecimal(fields[SbRecordConstants.SALARY_INDEX]))
                .managerId(Long.parseLong(fields[SbRecordConstants.UNIQUE_FIELD_INDEX]))
                .build();

        ParseRegistry.registerEmployee(employee);
        employees.add(employee);
    }
}
