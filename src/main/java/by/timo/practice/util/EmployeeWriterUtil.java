package by.timo.practice.util;

import by.timo.practice.exception.IOProcessingException;
import by.timo.practice.model.DepartmentStat;
import by.timo.practice.model.Employee;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.Manager;
import by.timo.practice.type.OrderType;
import by.timo.practice.type.SortType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeeWriterUtil {
    public static List<Manager> getManagers(List<EmployeeBase> employees) {
        return employees.stream()
                .filter(Manager.class::isInstance)
                .map(Manager.class::cast)
                .toList();
    }

    public static List<Employee> getEmployees(List<EmployeeBase> employees) {
        return employees.stream()
                .filter(Employee.class::isInstance)
                .map(Employee.class::cast)
                .toList();
    }

    public static Map<Long, List<Employee>> getEmployeeByDepartment(List<Employee> employees) {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getManagerId, Collectors.toList()));
    }

    public static List<String> getEmployeesWithoutManagers(
            List<Employee> employees,
            List<Manager> managers
    ) {
        Set<Long> managerIds = managers.stream()
                .map(Manager::getId)
                .collect(Collectors.toSet());

        return employees.stream()
                .filter(e -> !managerIds.contains(e.getManagerId()))
                .map(Object::toString)
                .toList();
    }

    public static SortedSet<DepartmentStat> collectsStat(
            Map<Long, List<Employee>> employeesByManager,
            List<Manager> managers
    ) {
        return managers.stream()
                .map(manager -> buildStat(
                        manager, employeesByManager.getOrDefault(manager.getId(), List.of())))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(DepartmentStat::getDepartment))));

    }

    public static DepartmentStat buildStat(Manager manager, List<Employee> employees) {
        if (employees.isEmpty()) {
            return new DepartmentStat(
                    manager.getDepartment(),
                    SbRecordConstants.ZERO,
                    SbRecordConstants.ZERO,
                    SbRecordConstants.ZERO);
        }

        DoubleSummaryStatistics stats = employees.stream()
                .mapToDouble(employee -> Double.parseDouble(employee.getSalary().toString()))
                .summaryStatistics();

        return new DepartmentStat(
                manager.getDepartment(),
                roundUp2(stats.getMin()),
                roundUp2(stats.getMax()),
                roundUp2(stats.getAverage())
        );
    }

    public static BigDecimal roundUp2(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    public static Comparator<Employee> employeeComparator(SortType sortType, OrderType orderType) {
        Comparator<Employee> comparator = switch (sortType) {
            case NAME -> Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
            case SALARY -> Comparator.comparing(Employee::getSalary);
        };
        return orderType.equals(OrderType.ASC) ? comparator : comparator.reversed();
    }

    public static Path getTargetFile(Path path, String defaultFileName, Path defaultDir) {
        if (path == null) {
            return defaultDir.resolve(defaultFileName);
        }

        String last = (path.getFileName() == null) ? "" : path.getFileName().toString();

        return last.endsWith(".txt") ? path : path.resolve(defaultFileName);
    }

    public static Path getBaseDir(Path path, Path defaultDir) {
        if (path == null) {
            return defaultDir;
        }

        String last = (path.getFileName() == null) ? "" : path.getFileName().toString();

        if (last.endsWith(".txt")) {
            Path parent = path.toAbsolutePath().getParent();
            return (parent != null) ? parent : defaultDir;
        }
        return path;
    }

    public static void ensureParentDirs(Path file) {
        Path parent = file.toAbsolutePath().getParent();

        if (parent != null) {
            try {
                Files.createDirectories(parent);
            } catch (IOException e) {
                throw new IOProcessingException("Failed to create directories for path: " + parent, e);
            }
        }
    }
}
