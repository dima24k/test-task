package by.timo.practice.model.enums;

import java.util.Arrays;
import java.util.Objects;

public enum SortType {
    NAME("name"),
    SALARY("salary");

    private final String sort;

    SortType(String sort) { this.sort = sort; }
    public String getSort() { return sort; }

    public static SortType toSortField(String value) {
        return Arrays.stream(values())
                .filter(f -> (Objects.equals(f.sort, value)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown sort: " + value));
    }
}

