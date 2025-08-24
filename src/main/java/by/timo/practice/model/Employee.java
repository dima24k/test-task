package by.timo.practice.model;

import by.timo.practice.type.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Employee extends EmployeeBase {
    private Long managerId;

    public Employee(Long id, String name, BigDecimal salary, Long managerId) {
        super(id, name, salary);
        super.setPost(PostType.EMPLOYEE.getPosition());
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return getPost() + ", " + getId() + ", " + getName() + ", " + getSalary() + ", " + getManagerId();
    }
}