package by.timo.practice.model;

import by.timo.practice.model.enums.PostType;

import java.math.BigDecimal;

public class Employee extends EmployeeBase {

    private long managerId;

    public Employee() {
    }

    public Employee(long id, String name, BigDecimal salary, long managerId) {
        super(id, name, salary);
        super.setPost(PostType.EMPLOYEE.getPosition());
        this.managerId = managerId;
    }

    public long getManagerId() {
        return managerId;
    }

    public void setManagerId(long managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return getPost() + ", " + getId() + ", " + getName() + ", " + getSalary() + ", " + getManagerId();
    }
}