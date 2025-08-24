package by.timo.practice.validate;

import by.timo.practice.type.OrderType;
import by.timo.practice.type.OutputType;
import by.timo.practice.type.SortType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgsValidatorTest {

    @Test
    void validateArgsShouldNotThrowWhenValidArguments() {
        assertDoesNotThrow(() -> ArgsValidator.validateArgs(
                true,
                OutputType.FILE,
                Paths.get("output.txt"),
                SortType.NAME,
                OrderType.ASC
        ));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void validateArgsShouldThrowWhenInvalidArguments(
            boolean stat, OutputType output, Path path, SortType sort, OrderType order) {

        assertThrows(IllegalArgumentException.class, () ->
                ArgsValidator.validateArgs(stat, output, path, sort, order)
        );
    }

    private static Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(false, OutputType.FILE, Paths.get("output.txt"), SortType.NAME, OrderType.ASC),
                Arguments.of(true, OutputType.FILE, null, SortType.NAME, OrderType.ASC),
                Arguments.of(true, OutputType.CONSOLE, Paths.get("output.txt"), SortType.NAME, OrderType.ASC),
                Arguments.of(true, null, null, null, OrderType.ASC)
        );
    }
}
