package by.timo.practice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum OutputType {
    CONSOLE("console"),
    FILE("file");

    private final String output;

    public static OutputType toOutputType(String value) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.output, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown output: " + value));
    }
}
