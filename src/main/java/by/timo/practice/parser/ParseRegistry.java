package by.timo.practice.parser;

import by.timo.practice.exception.EmployeeDuplicateException;
import by.timo.practice.model.Employee;
import by.timo.practice.model.Manager;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ParseRegistry {
    private static final Map<Long, Employee> employees = new LinkedHashMap<>();
    private static final Map<Long, String> managerNameById = new LinkedHashMap<>();
    private static final Map<String, Long> departmentToManagerId = new LinkedHashMap<>();

    private ParseRegistry () {}

    public static void registerEmployee(Employee e) {
        long id = e.getId();
        if (employees.containsKey(id)) {
            throw new EmployeeDuplicateException("Duplicate employee id: " + id);
        }
        if (managerNameById.containsKey(id)) {
            throw new EmployeeDuplicateException("Employee id conflicts with existing manager id: " + id);
        }
        employees.put(id, e);
    }

    // проверка manager на корректность id, department, name с другими работниками
    public static void registerManager(Manager manager) {
        long id = manager.getId();
        String department = manager.getDepartment();
        String name = manager.getName();

        Long existing = departmentToManagerId.get(department);

        if (existing != null) {
            throw new EmployeeDuplicateException(
                    "Department already has a manager: department=" + department + ", existingManagerId=" + existing + ", newManagerId=" + id
            );
        }

        if (employees.containsKey(id)) {
            throw new EmployeeDuplicateException(
                    "Manager id conflicts with existing employee id: " + id);
        }

        String existingName = managerNameById.get(id);

        if (existingName != null && !existingName.equals(name)) {
                throw new EmployeeDuplicateException(
                        "Manager id " + id + " already bound to name '" + existingName + "', got '" + name + "'");
        }

        managerNameById.put(id, name);
        departmentToManagerId.put(department, id);
    }
}
