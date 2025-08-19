package by.timo.practice.builder;

import by.timo.practice.model.Employee;
import by.timo.practice.util.SbRecordConstants;

import java.math.BigDecimal;

//todo добавить @Builder lombok для models
public class EmployeeBuilder implements Builder<Employee> {
    public Employee build(String[] fields) {
        Employee employee = new Employee();

        employee.setId(Integer.parseInt(fields[SbRecordConstants.ID_INDEX]));
        employee.setPost(fields[SbRecordConstants.POST_INDEX]);
        employee.setName(fields[SbRecordConstants.NAME_INDEX]);
        employee.setSalary(new BigDecimal(fields[SbRecordConstants.SALARY_INDEX]));
        employee.setManagerId(Integer.parseInt(fields[SbRecordConstants.UNIQUE_FIELD_INDEX]));

        return employee;
    }
}
