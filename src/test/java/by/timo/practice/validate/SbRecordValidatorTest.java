package by.timo.practice.validate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SbRecordValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"123", "1", "999999999"})
    void isValidIdShouldReturnTrueForValidIds(String id) {
        assertTrue(SbRecordValidator.isValidId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "abc", ""})
    void isValidIdShouldReturnFalseForInvalidIds(String id) {
        assertFalse(SbRecordValidator.isValidId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Doe", "Alice", "A"})
    void isStringValidShouldReturnTrueForValidNames(String name) {
        assertTrue(SbRecordValidator.isStringValid(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "123", "John123"})
    void isStringValidShouldReturnFalseForInvalidNames(String name) {
        assertFalse(SbRecordValidator.isStringValid(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1000", "999.99", "0.01"})
    void isSalaryValidShouldReturnTrueForValidSalaries(String salary) {
        assertTrue(SbRecordValidator.isSalaryValid(salary));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1000", "0", "abc", ""})
    void isSalaryValidShouldReturnFalseForInvalidSalaries(String salary) {
        assertFalse(SbRecordValidator.isSalaryValid(salary));
    }
}
