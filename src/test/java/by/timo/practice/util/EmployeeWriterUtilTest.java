package by.timo.practice.util;

import by.timo.practice.model.DepartmentStat;
import by.timo.practice.model.Employee;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.Manager;
import by.timo.practice.type.OrderType;
import by.timo.practice.type.SortType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeWriterUtilTest {

    @Test
    void getManagersShouldReturnOnlyManagers() {
        Employee employee = new Employee(1L, "John", new BigDecimal("1000"), 10L);
        Manager manager = new Manager(10L, "Manager", new BigDecimal("5000"), "IT");
        List<EmployeeBase> employees = List.of(employee, manager);

        List<Manager> managers = EmployeeWriterUtil.getManagers(employees);

        assertEquals(1, managers.size());
        assertEquals(manager, managers.get(0));
    }

    @Test
    void getEmployeesShouldReturnOnlyEmployees() {
        Employee employee = new Employee(1L, "John", new BigDecimal("1000"), 10L);
        Manager manager = new Manager(10L, "Manager", new BigDecimal("5000"), "IT");
        List<EmployeeBase> employees = List.of(employee, manager);

        List<Employee> result = EmployeeWriterUtil.getEmployees(employees);

        assertEquals(1, result.size());
        assertEquals(employee, result.get(0));
    }

    @Test
    void getEmployeeByDepartmentShouldGroupByManagerId() {
        Employee employee1 = new Employee(1L, "John", new BigDecimal("1000"), 10L);
        Employee employee2 = new Employee(2L, "Jane", new BigDecimal("2000"), 10L);
        Employee employee3 = new Employee(3L, "Bob", new BigDecimal("3000"), 20L);
        List<Employee> employees = List.of(employee1, employee2, employee3);

        Map<Long, List<Employee>> result = EmployeeWriterUtil.getEmployeeByDepartment(employees);

        assertEquals(2, result.size());
        assertEquals(2, result.get(10L).size());
        assertEquals(1, result.get(20L).size());
    }

    @Test
    void getEmployeesWithoutManagersShouldReturnEmployeesWithoutMatchingManager() {
        List<Employee> employees = List.of(
                new Employee(1L, "John",    new BigDecimal(1000), 10L),
                new Employee(2L, "Jane",    new BigDecimal(2000), 99L)
        );

        List<Manager> managers = List.of(
                new Manager(10L, "Manager", new BigDecimal(5000), "IT")
        );

        List<String> result = EmployeeWriterUtil.getEmployeesWithoutManagers(employees, managers);

        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("Jane"));
    }

    @Test
    void collectsStatShouldReturnDepartmentStats() {
        Map<Long, List<Employee>> employeesByManager = Map.of(10L, List.of(
                new Employee(1L, "John", new BigDecimal("1000"), 10L),
                new Employee(2L, "Jane", new BigDecimal("2000"), 10L)
        ));

        List<Manager> managers = List.of(
                new Manager(10L, "Manager", new BigDecimal("5000"), "IT")
        );

        var stats = EmployeeWriterUtil.collectsStat(employeesByManager, managers);

        assertEquals(1, stats.size());
        DepartmentStat stat = stats.iterator().next();
        assertEquals("IT", stat.getDepartment());
        assertEquals(new BigDecimal("1000.00"), stat.getMin());
        assertEquals(new BigDecimal("2000.00"), stat.getMax());
        assertEquals(new BigDecimal("1500.00"), stat.getAvg());
    }

    @Test
    void buildStatShouldHandleEmptyEmployees() {
        Manager manager = new Manager(10L, "Manager", new BigDecimal("5000"), "IT");

        DepartmentStat stat = EmployeeWriterUtil.buildStat(manager, List.of());

        assertEquals("IT", stat.getDepartment());
        assertEquals(SbRecordConstants.ZERO, stat.getMin());
        assertEquals(SbRecordConstants.ZERO, stat.getMax());
        assertEquals(SbRecordConstants.ZERO, stat.getAvg());
    }

    @Test
    void employeeComparatorShouldSortByNameAscending() {
        Employee employee1 = new Employee(1L, "Bob", new BigDecimal("1000"), 10L);
        Employee employee2 = new Employee(2L, "Alice", new BigDecimal("2000"), 10L);

        var comparator = EmployeeWriterUtil.employeeComparator(SortType.NAME, OrderType.ASC);

        assertTrue(comparator.compare(employee1, employee2) > 0);
    }

    @Test
    void employeeComparatorShouldSortBySalaryDescending() {
        Employee employee1 = new Employee(1L, "Alice", new BigDecimal("1000"), 10L);
        Employee employee2 = new Employee(2L, "Bob", new BigDecimal("2000"), 10L);

        var comparator = EmployeeWriterUtil.employeeComparator(SortType.SALARY, OrderType.DESC);

        assertTrue(comparator.compare(employee1, employee2) > 0);
    }

    @Test
    void roundUp2ShouldRoundToTwoDecimalPlaces() {
        BigDecimal result = EmployeeWriterUtil.roundUp2(123.4567);
        assertEquals(new BigDecimal("123.46"), result);
    }

    @Test
    void getTargetFileShouldReturnDefaultWhenPathIsNull() {
        Path defaultDir = Path.of("default");
        Path result = EmployeeWriterUtil.getTargetFile(null, "file.txt", defaultDir);

        assertEquals(defaultDir.resolve("file.txt"), result);
    }

    @Test
    void getTargetFileShouldReturnPathWhenItIsAFile() {
        Path path = Path.of("output.txt");
        Path defaultDir = Path.of("default");
        Path result = EmployeeWriterUtil.getTargetFile(path, "file.txt", defaultDir);

        assertEquals(path, result);
    }

    @Test
    void getTargetFileShouldResolveFileNameWhenPathIsDirectory() {
        Path path = Path.of("output");
        Path defaultDir = Path.of("default");
        Path result = EmployeeWriterUtil.getTargetFile(path, "file.txt", defaultDir);

        assertEquals(path.resolve("file.txt"), result);
    }

    @Test
    void getBaseDirShouldReturnDefaultWhenPathIsNull() {
        Path defaultDir = Path.of("default");
        Path result = EmployeeWriterUtil.getBaseDir(null, defaultDir);

        assertEquals(defaultDir, result);
    }

    @Test
    void getBaseDirShouldReturnParentWhenPathIsFile() {
        Path path = Path.of("output/file.txt");
        Path defaultDir = Path.of("default");
        Path result = EmployeeWriterUtil.getBaseDir(path, defaultDir);

        assertEquals(path.toAbsolutePath().getParent(), result);
    }

    @Test
    void getBaseDirShouldReturnPathWhenItIsDirectory() {
        Path path = Path.of("output");
        Path defaultDir = Path.of("default");
        Path result = EmployeeWriterUtil.getBaseDir(path, defaultDir);

        assertEquals(path, result);
    }
}
