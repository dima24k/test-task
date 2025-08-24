package by.timo.practice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum OrderType {
    ASC("asc", true),
    DESC("desc", false);

    private final String order;
    private final boolean ascending;

    public static OrderType toOrder(String value) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.order, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order: " + value));
    }
}

