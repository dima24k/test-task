package by.timo.practice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum ArgKey {
    SORT_LONG("--sort"),
    SORT_SHORT("-s"),
    ORDER("--order"),
    OUTPUT_LONG("--output"),
    OUTPUT_SHORT("-o"),
    PATH("--path"),
    STAT("--stat");

    private final String key;

    public static ArgKey from(String value) {
        return Arrays.stream(values())
                .filter(argKey -> Objects.equals(argKey.key, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown key: " + value));
    }
}
