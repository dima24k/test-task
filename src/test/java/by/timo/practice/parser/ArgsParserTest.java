package by.timo.practice.parser;

import by.timo.practice.model.InputArgs;
import by.timo.practice.type.OrderType;
import by.timo.practice.type.OutputType;
import by.timo.practice.type.SortType;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgsParserTest {

    @Test
    void parseArgsShouldParseAllArgumentsCorrectly() {
        String[] args = {
                "--sort=name",
                "--order=asc",
                "--output=file",
                "--path=output.txt",
                "--stat"
        };

        InputArgs result = ArgsParser.parseArgs(args);

        assertNotNull(result);
        assertEquals(SortType.NAME, result.getSortType());
        assertEquals(OrderType.ASC, result.getOrderType());
        assertEquals(OutputType.FILE, result.getOutputType());
        assertEquals(Path.of("output.txt"), result.getOutputFilePath());
        assertTrue(result.isStat());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidArguments")
    void parseArgsShouldThrowExceptionForInvalidArguments(String[] args) {
        assertThrows(IllegalArgumentException.class, () -> ArgsParser.parseArgs(args));
    }

    private static Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of((Object) new String[]{}),
                Arguments.of((Object) new String[]{"--invalid-arg"}),
                Arguments.of((Object) new String[]{"--sort=invalid"}),
                Arguments.of((Object) new String[]{"--order=invalid"}),
                Arguments.of((Object) new String[]{"--output=invalid"}),
                Arguments.of((Object) new String[]{"--sort=name", "--sort=salary"})
        );
    }
}
