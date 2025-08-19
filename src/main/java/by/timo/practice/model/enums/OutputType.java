package by.timo.practice.model.enums;

import java.util.Arrays;
import java.util.Objects;

public enum OutputType {
    CONSOLE("console"),
    FILE("file");

    private final String output;

    OutputType(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }

    public static OutputType toOutputType(String value) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.output, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown output: " + value));
    }
}
