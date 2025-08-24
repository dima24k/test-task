package by.timo.practice.parser;

import by.timo.practice.exception.EmployeeDuplicateException;
import by.timo.practice.model.Employee;
import by.timo.practice.model.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParseRegistryTest {

    @BeforeEach
    void setUp() {
        ParseRegistry.getEMPLOYEES().clear();
        ParseRegistry.getMANAGER_NAME_BY_ID().clear();
        ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().clear();
    }

    @Test
    void registerEmployeeShouldAddEmployee() {
        Employee employee = new Employee(1L, "John", new BigDecimal("1000"), 10L);

        assertDoesNotThrow(() -> ParseRegistry.registerEmployee(employee));
        assertTrue(ParseRegistry.getEMPLOYEES().containsKey(1L));
        assertEquals(employee, ParseRegistry.getEMPLOYEES().get(1L));
    }

    @Test
    void registerManagerShouldAddManager() {
        Manager manager = new Manager(10L, "Manager", new BigDecimal("5000"), "IT");

        assertDoesNotThrow(() -> ParseRegistry.registerManager(manager));
        assertTrue(ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().containsKey("IT"));
        assertEquals(10L, ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().get("IT").longValue());
        assertEquals("Manager", ParseRegistry.getMANAGER_NAME_BY_ID().get(10L));
    }

    @Test
    void registerEmployeeShouldThrowExceptionForDuplicateId() {
        Employee emp1 = new Employee(1L, "John", new BigDecimal("1000"), 10L);
        Employee emp2 = new Employee(1L, "Jane", new BigDecimal("2000"), 10L);

        ParseRegistry.registerEmployee(emp1);
        assertThrows(EmployeeDuplicateException.class, () -> ParseRegistry.registerEmployee(emp2));
    }

    @Test
    void registerEmployeeShouldThrowExceptionWhenIdConflictsWithManager() {
        Manager manager = new Manager(10L, "Manager", new BigDecimal("5000"), "IT");
        Employee employee = new Employee(10L, "John", new BigDecimal("1000"), 10L);

        ParseRegistry.registerManager(manager);
        assertThrows(EmployeeDuplicateException.class, () -> ParseRegistry.registerEmployee(employee));
    }

    @Test
    void registerManagerShouldThrowExceptionForDuplicateDepartment() {
        Manager manager1 = new Manager(10L, "Manager1", new BigDecimal("5000"), "IT");
        Manager manager2 = new Manager(11L, "Manager2", new BigDecimal("6000"), "IT");

        ParseRegistry.registerManager(manager1);
        assertThrows(EmployeeDuplicateException.class, () -> ParseRegistry.registerManager(manager2));
    }

    @Test
    void registerManagerShouldThrowExceptionWhenIdConflictsWithEmployee() {
        Employee employee = new Employee(10L, "John", new BigDecimal("1000"), 10L);
        Manager manager = new Manager(10L, "Manager", new BigDecimal("5000"), "IT");

        ParseRegistry.registerEmployee(employee);
        assertThrows(EmployeeDuplicateException.class, () -> ParseRegistry.registerManager(manager));
    }

    @Test
    void registerManagerShouldThrowExceptionWhenSameIdDifferentName() {
        Manager manager1 = new Manager(10L, "Manager1", new BigDecimal("5000"), "IT");
        Manager manager2 = new Manager(10L, "Manager2", new BigDecimal("6000"), "HR");

        ParseRegistry.registerManager(manager1);

        assertThrows(EmployeeDuplicateException.class, () -> ParseRegistry.registerManager(manager2));
    }

    @Test
    void registerMultipleManagersWithSameIdAndNameDifferentDepartments() {
        Manager m1 = new Manager(1L, "Alex", new BigDecimal("5000"), "Sales");
        Manager m2 = new Manager(1L, "Alex", new BigDecimal("5500"), "Marketing");

        assertDoesNotThrow(() -> {
            ParseRegistry.registerManager(m1);
            ParseRegistry.registerManager(m2);
        });

        assertEquals(2, ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().size());
        assertTrue(ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().containsKey("Sales"));
        assertEquals(1L, ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().get("Sales").longValue());
        assertEquals("Alex", ParseRegistry.getMANAGER_NAME_BY_ID().get(1L));

        assertTrue(ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().containsKey("Marketing"));
        assertEquals(1L, ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().get("Marketing").longValue());
        assertEquals("Alex", ParseRegistry.getMANAGER_NAME_BY_ID().get(1L));
    }

    @Test
    void registerMultipleEmployeesWithDifferentIdsShouldSucceed() {
        Employee e1 = new Employee(1L, "John", new BigDecimal("1000"), 10L);
        Employee e2 = new Employee(2L, "Jane", new BigDecimal("1200"), 10L);

        assertDoesNotThrow(() -> {
            ParseRegistry.registerEmployee(e1);
            ParseRegistry.registerEmployee(e2);
        });

        assertEquals(2, ParseRegistry.getEMPLOYEES().size());
        assertTrue(ParseRegistry.getEMPLOYEES().containsKey(1L));
        assertTrue(ParseRegistry.getEMPLOYEES().containsKey(2L));
    }
}
