package by.timo.practice.writer;

import by.timo.practice.exception.IOProcessingException;
import by.timo.practice.model.DepartmentStat;
import by.timo.practice.model.Employee;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.InputArgs;
import by.timo.practice.model.Manager;
import by.timo.practice.type.OrderType;
import by.timo.practice.type.OutputType;
import by.timo.practice.type.SortType;
import by.timo.practice.util.EmployeeWriterUtil;
import by.timo.practice.util.SbRecordConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static by.timo.practice.util.EmployeeWriterUtil.collectsStat;
import static by.timo.practice.util.EmployeeWriterUtil.employeeComparator;
import static by.timo.practice.util.EmployeeWriterUtil.getEmployeeByDepartment;
import static by.timo.practice.util.EmployeeWriterUtil.getEmployees;
import static by.timo.practice.util.EmployeeWriterUtil.getEmployeesWithoutManagers;
import static by.timo.practice.util.EmployeeWriterUtil.getManagers;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EmployeesWriter {
    public static void writeEmployees(InputArgs inputArgs, List<EmployeeBase> allEmployees, List<String> errorLogLines) {
        List<Manager> managers = getManagers(allEmployees);
        List<Employee> employees = getEmployees(allEmployees);
        Map<Long, List<Employee>> employeesByManager = getEmployeeByDepartment(employees);
        List<String> employeesWithoutManagers = getEmployeesWithoutManagers(employees, managers);

        if (!employeesWithoutManagers.isEmpty()) {
            errorLogLines.addAll(employeesWithoutManagers);
        }

        if (inputArgs.getSortType() != null) {
            writeDepartmentToFile(
                    managers,
                    employeesByManager,
                    inputArgs.getOutputFilePath(),
                    inputArgs.getOrderType(),
                    inputArgs.getSortType());
        }

        if (!inputArgs.isStat()) {
            return;
        }

        Set<DepartmentStat> stats = collectsStat(employeesByManager, managers);

        if ((inputArgs.isStat() && inputArgs.getOutputType() == null) ||
                (inputArgs.isStat() && inputArgs.getOutputType().equals(OutputType.CONSOLE))) {
            printStat(stats);
            return;
        }

        if (inputArgs.isStat() && inputArgs.getOutputType().equals(OutputType.FILE)) {
            writeStatsToFile(stats, inputArgs.getOutputFilePath());
        }
    }

    private static void printStat(Set<DepartmentStat> stats) {
        System.out.println("department, min, max, avg\n");

        stats.forEach(stat -> System.out.printf(
                "%s, %.2f, %.2f, %.2f%n",
                stat.getDepartment(), stat.getMin(), stat.getMax(), stat.getAvg()
        ));
    }

    private static void writeStatsToFile(Set<DepartmentStat> stats, Path path) {
        Path target = EmployeeWriterUtil.getTargetFile(
                path, SbRecordConstants.STATISTICS, SbRecordConstants.PROJECT_DIRECTORY);
        EmployeeWriterUtil.ensureParentDirs(target);

        String parameters = "department, min, max, avg";

        List<String> lines = new ArrayList<>();
        lines.add(parameters);
        lines.add("");
        lines.addAll(stats.stream()
                .map(DepartmentStat::toString)
                .toList());

        try {
            Files.write(target, lines, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IOProcessingException("Failed to write statistics file: " + target, e);
        }
    }

    private static void writeDepartmentToFile(
            List<Manager> managers,
            Map<Long, List<Employee>> employeesByManager,
            Path path,
            OrderType orderType,
            SortType sortType
    ) {
        Path baseDir = EmployeeWriterUtil.getBaseDir(path, SbRecordConstants.PROJECT_DIRECTORY);

        Comparator<Employee> employeeComparator = (sortType != null && orderType != null)
                ? employeeComparator(sortType, orderType)
                : null;

        managers.forEach(manager -> {
            Path file = baseDir.resolve(manager.getDepartment() + ".sb");
            EmployeeWriterUtil.ensureParentDirs(file);

            List<Employee> employees = new ArrayList<>(employeesByManager.getOrDefault(manager.getId(), List.of()));

            if (employeeComparator != null) {
                employees.sort(employeeComparator);
            }

            List<String> lines = new ArrayList<>();
            lines.add(manager.toString());
            employees.stream()
                    .map(Object::toString)
                    .forEach(lines::add);

            try {
                Files.write(
                        file,
                        lines,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING
                );
            } catch (IOException e) {
                throw new IOProcessingException("Failed to write department file: " + file, e);
            }
        });
    }
}
