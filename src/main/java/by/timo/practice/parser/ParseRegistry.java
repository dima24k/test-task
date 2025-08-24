package by.timo.practice.parser;

import by.timo.practice.exception.EmployeeDuplicateException;
import by.timo.practice.model.Employee;
import by.timo.practice.model.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ParseRegistry {
    @Getter
    private static final Map<Long, Employee> EMPLOYEES = new LinkedHashMap<>();

    @Getter
    private static final Map<Long, String> MANAGER_NAME_BY_ID = new LinkedHashMap<>();

    @Getter
    private static final Map<String, Long> DEPARTMENT_TO_MANAGER_ID = new LinkedHashMap<>();

    public static void registerEmployee(Employee e) {
        Long id = e.getId();

        if (EMPLOYEES.containsKey(id)) {
            throw new EmployeeDuplicateException("Duplicate employee id: " + id);
        }

        if (MANAGER_NAME_BY_ID.containsKey(id)) {
            throw new EmployeeDuplicateException("Employee id conflicts with existing manager id: " + id);
        }
        EMPLOYEES.put(id, e);
    }

    public static void registerManager(Manager manager) {
        Long id = manager.getId();
        String department = manager.getDepartment();
        String name = manager.getName();

        Long existing = DEPARTMENT_TO_MANAGER_ID.get(department);
        String existingName = MANAGER_NAME_BY_ID.get(id);

        if (existing != null) {
            throw new EmployeeDuplicateException(
                    "Department already has a manager: department=" + department + ", existingManagerId=" + existing + ", newManagerId=" + id
            );
        }

        if (EMPLOYEES.containsKey(id)) {
            throw new EmployeeDuplicateException(
                    "Manager id conflicts with existing employee id: " + id);
        }

        if (existingName != null && !existingName.equals(name)) {
            throw new EmployeeDuplicateException(
                    "Manager id " + id + " already bound to name '" + existingName + "', got '" + name + "'");
        }
        MANAGER_NAME_BY_ID.put(id, name);
        DEPARTMENT_TO_MANAGER_ID.put(department, id);
    }
}
