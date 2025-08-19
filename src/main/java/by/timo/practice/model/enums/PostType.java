package by.timo.practice.model.enums;

import java.util.Arrays;

public enum PostType {
    EMPLOYEE("Employee"),
    MANAGER("Manager");

    private final String position;

    PostType(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public static PostType toPosition(String position) {
        return Arrays.stream(values())
                .filter(f -> f.position.equals(position))
                .findFirst()
                .orElseThrow( () ->  new IllegalArgumentException("Unknown position " + position));
    }
}
