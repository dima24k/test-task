package by.timo.practice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum SortType {
    NAME("name"),
    SALARY("salary");

    private final String sort;

    public static SortType toSortField(String value) {
        return Arrays.stream(values())
                .filter(f -> (Objects.equals(f.sort, value)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort: " + value));
    }
}

