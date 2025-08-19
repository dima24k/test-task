package by.timo.practice.model;

import by.timo.practice.model.enums.PostType;

import java.math.BigDecimal;
import java.util.Objects;

public class Manager extends EmployeeBase {
    private String department;

    public Manager() {
    }

    public Manager(long id, String name, BigDecimal salary, String department) {
        super(id, name, salary);
        super.setPost(PostType.MANAGER.getPosition());
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return getPost() + ", " + getId() + ", " + getName() + ", " + getSalary() + ", " + department;
    }

    @Override
    public final boolean equals(Object o) {
        if (!super.equals(o)) return false;
        if (this == o) return true;
        if (!(o instanceof Manager manager)) return false;

        return Objects.equals(department, manager.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), department);
    }
}
