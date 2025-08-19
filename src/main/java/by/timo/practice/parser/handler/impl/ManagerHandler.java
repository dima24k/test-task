package by.timo.practice.parser.handler.impl;

import by.timo.practice.builder.ManagerBuilder;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.Manager;
import by.timo.practice.parser.ParseRegistry;
import by.timo.practice.parser.handler.RecordHandler;
import by.timo.practice.validate.SbRecordValidator;

import java.util.List;

import static by.timo.practice.model.enums.PostType.MANAGER;

public class ManagerHandler implements RecordHandler {
    @Override
    public void handle(String[] fields, List<EmployeeBase> out, List<String> err, String cleaned) {
        if (!SbRecordValidator.strategies.get(MANAGER).validate(fields)) {
            err.add(cleaned);
            return;
        }

        Manager manager = new ManagerBuilder().build(fields);
        ParseRegistry.registerManager(manager);
        out.add(manager);
    }
}
