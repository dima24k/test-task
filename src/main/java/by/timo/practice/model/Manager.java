package by.timo.practice.model;

import by.timo.practice.type.PostType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Manager extends EmployeeBase {
    private String department;

    public Manager(Long id, String name, BigDecimal salary, String department) {
        super(id, name, salary);
        super.setPost(PostType.MANAGER.getPosition());
        this.department = department;
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

    @Override
    public String toString() {
        return getPost() + ", " + getId() + ", " + getName() + ", " + getSalary() + ", " + department;
    }
}
