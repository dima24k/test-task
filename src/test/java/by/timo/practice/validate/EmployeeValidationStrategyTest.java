package by.timo.practice.validate;

import by.timo.practice.validate.strategy.impl.EmployeeValidationStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeValidationStrategyTest {
    private final EmployeeValidationStrategy strategy = new EmployeeValidationStrategy();

    @Test
    void validateShouldReturnTrueForValidEmployee() {
        String[] fields = {"Employee", "1", "John Doe", "1000.50", "10"};
        assertTrue(strategy.validate(fields));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmployeeFields")
    void validateShouldReturnFalseForInvalidEmployee(String[] fields) {
        assertFalse(strategy.validate(fields));
    }

    private static Stream<Arguments> provideInvalidEmployeeFields() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Employee", "", "John Doe", "1000.50", "10"}),
                Arguments.of((Object) new String[]{"Employee", "invalid", "John Doe", "1000.50", "10"}),
                Arguments.of((Object) new String[]{"Employee", "1", "", "1000.50", "10"}),
                Arguments.of((Object) new String[]{"Employee", "1", "John Doe123", "1000.50", "10"}),
                Arguments.of((Object) new String[]{"Employee", "1", "John Doe", "", "10"}),
                Arguments.of((Object) new String[]{"Employee", "1", "John Doe", "invalid", "10"}),
                Arguments.of((Object) new String[]{"Employee", "1", "John Doe", "-1000.50", "10"}),
                Arguments.of((Object) new String[]{"Employee", "1", "John Doe", "1000.50", ""}),
                Arguments.of((Object) new String[]{"Employee", "1", "John Doe", "1000.50", "abc123"})
        );
    }
}
