package by.timo.practice.model.enums;

import java.util.Arrays;
import java.util.Objects;

public enum OrderType {
    ASC("asc", true),
    DESC("desc", false);

    private final String order;
    private final boolean ascending;

    OrderType(String order, boolean ascending) {
        this.order = order;
        this.ascending = ascending;
    }

    public String getOrder() {
        return order;
    }

    public boolean isAscending() {
        return ascending;
    }

    public static OrderType toOrder(String value) {
        return Arrays.stream(values())
                .filter(o -> Objects.equals(o.order, value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown order: " + value));
    }
}

