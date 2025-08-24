package by.timo.practice.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum PostType {
    EMPLOYEE("Employee"),
    MANAGER("Manager");

    private final String position;

    public static PostType toPosition(String position) {
        return Arrays.stream(values())
                .filter(f -> Objects.equals(f.position, position))
                .findFirst()
                .orElse(null);
    }
}
