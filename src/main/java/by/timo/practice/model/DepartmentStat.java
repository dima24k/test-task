package by.timo.practice.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class DepartmentStat {
    private final String department;
    private final BigDecimal min;
    private final BigDecimal max;
    private final BigDecimal avg;

    @Override
    public String toString() {
        return department + ", " + min + ", " + max + ", " + avg;
    }
}