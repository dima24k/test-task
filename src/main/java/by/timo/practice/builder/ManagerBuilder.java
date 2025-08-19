package by.timo.practice.builder;

import by.timo.practice.model.Manager;
import by.timo.practice.util.SbRecordConstants;

import java.math.BigDecimal;

public class ManagerBuilder implements Builder<Manager> {

    public Manager build(String[] fields) {
        Manager manager = new Manager();

        manager.setPost(fields[SbRecordConstants.POST_INDEX]);
        manager.setId(Integer.parseInt(fields[SbRecordConstants.ID_INDEX]));
        manager.setName(fields[SbRecordConstants.NAME_INDEX]);
        manager.setSalary(new BigDecimal(fields[SbRecordConstants.SALARY_INDEX]));
        manager.setDepartment(fields[SbRecordConstants.UNIQUE_FIELD_INDEX]);

        return manager;
    }
}
