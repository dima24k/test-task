package by.timo.practice.validate;

import by.timo.practice.validate.strategy.impl.ManagerValidationStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagerValidationStrategyTest {
    private final ManagerValidationStrategy strategy = new ManagerValidationStrategy();

    @Test
    void validateShouldReturnTrueForValidManager() {
        String[] fields = {"Manager", "1", "John Krasinsky", "11000", "IT"};
        assertTrue(strategy.validate(fields));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidManagerFields")
    void validateShouldReturnFalseForInvalidManager(String[] fields) {
        assertFalse(strategy.validate(fields));
    }

    private static Stream<Arguments> provideInvalidManagerFields() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Manager", "", "John Krasinsky", "1000.50", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "invalid", "John Krasinsky", "1000.50", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "1", "", "1000.50", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "1", "John Krasinsky123", "1000.50", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "1", "John Krasinsky", "", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "1", "John Krasinsky", "abc", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "1", "John Krasinsky", "-10000", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "invalid", "John Krasinsky", "1000.50", "IT"}),
                Arguments.of((Object) new String[]{"Manager", "1", "John Krasinsky", "1000.50", ""}),
                Arguments.of((Object) new String[]{"Manager", "1", "John Krasinsky", "1000.50", "123abc"})
        );
    }
}
