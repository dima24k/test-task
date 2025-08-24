package by.timo.practice.parser;

import by.timo.practice.model.Employee;
import by.timo.practice.model.EmployeeBase;
import by.timo.practice.model.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SbRecordParserTest {

    @BeforeEach
    void clearErrorLog() {
        SbRecordParser.getErrorLogLines().clear();

        ParseRegistry.getEMPLOYEES().clear();
        ParseRegistry.getMANAGER_NAME_BY_ID().clear();
        ParseRegistry.getDEPARTMENT_TO_MANAGER_ID().clear();
    }

    @Test
    void parseSbRecordsShouldReturnEmptyListWhenInputIsEmpty() {
        List<EmployeeBase> result = SbRecordParser.parseSbRecords(List.of());

        assertTrue(result.isEmpty());
        assertTrue(SbRecordParser.getErrorLogLines().isEmpty());
    }

    @Test
    void parseSbRecordsShouldHandleValidManagerRecord() {
        String sbRecord = "Manager,1,John Doe,5000,IT";

        List<EmployeeBase> result = SbRecordParser.parseSbRecords(List.of(sbRecord));

        assertEquals(1, result.size());
        assertInstanceOf(Manager.class, result.get(0));
        Manager manager = (Manager) result.get(0);
        assertEquals(1L, manager.getId());
        assertEquals("John Doe", manager.getName());
        assertEquals(new BigDecimal("5000"), manager.getSalary());
        assertEquals("IT", manager.getDepartment());
        assertTrue(SbRecordParser.getErrorLogLines().isEmpty());
    }

    @Test
    void parseSbRecordsShouldHandleValidEmployeeRecord() {
        String sbRecord = "Employee,2,Jane Doe,3000,1";

        List<EmployeeBase> result = SbRecordParser.parseSbRecords(List.of(sbRecord));

        assertEquals(1, result.size());
        assertInstanceOf(Employee.class, result.get(0));
        Employee employee = (Employee) result.get(0);
        assertEquals(2L, employee.getId());
        assertEquals("Jane Doe", employee.getName());
        assertEquals(new BigDecimal("3000"), employee.getSalary());
        assertEquals(1L, employee.getManagerId());
        assertTrue(SbRecordParser.getErrorLogLines().isEmpty());
    }

    @Test
    void parseSbRecordsShouldAddToErrorLogWhenRecordHasWrongFieldCount() {
        String sbRecord = "Manager,1,John Doe";

        List<EmployeeBase> result = SbRecordParser.parseSbRecords(List.of(sbRecord));

        assertTrue(result.isEmpty());
        assertEquals(1, SbRecordParser.getErrorLogLines().size());
        assertEquals(sbRecord, SbRecordParser.getErrorLogLines().get(0));
    }

    @Test
    void parseSbRecordsShouldAddToErrorLogWhenUnknownPostType() {
        String sbRecord = "Director,3,Bob Smith,7000,Finance";

        List<EmployeeBase> result = SbRecordParser.parseSbRecords(List.of(sbRecord));

        assertTrue(result.isEmpty());
        assertEquals(1, SbRecordParser.getErrorLogLines().size());
        assertEquals(sbRecord, SbRecordParser.getErrorLogLines().get(0));
    }

    @Test
    void parseSbRecordsShouldHandleMixedValidAndInvalidRecords() {
        String validRecord = "Manager,1,John Doe,5000,IT";
        String invalidRecord = "Employee,2,Jane Doe";

        List<EmployeeBase> result = SbRecordParser.parseSbRecords(List.of(validRecord, invalidRecord));

        assertEquals(1, result.size());
        assertInstanceOf(Manager.class, result.get(0));
        assertEquals(1, SbRecordParser.getErrorLogLines().size());
        assertEquals(invalidRecord, SbRecordParser.getErrorLogLines().get(0));
    }
}
